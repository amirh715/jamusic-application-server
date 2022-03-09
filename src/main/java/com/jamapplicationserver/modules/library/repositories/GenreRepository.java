/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import com.jamapplicationserver.infra.Persistence.database.Models.GenreModel;
import com.jamapplicationserver.infra.Persistence.database.Models.UserModel;
import com.jamapplicationserver.infra.Persistence.database.AccessControlException;
import com.jamapplicationserver.infra.Persistence.database.UpdaterOrCreatorDoesNotExistException;
import com.jamapplicationserver.infra.Persistence.database.Models.LibraryEntityModel;
import com.jamapplicationserver.infra.Persistence.database.Models.UserRoleEnum;
import javax.persistence.*;
import com.jamapplicationserver.modules.library.repositories.exceptions.*;
import org.hibernate.exception.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.library.repositories.mappers.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author dada
 */
public class GenreRepository implements IGenreRepository {
    
    private final EntityManagerFactoryHelper emfh;
    
    private static final int MAX_ALLOWED_GENRES = 30;
    
    private GenreRepository(EntityManagerFactoryHelper emfh) {
        this.emfh = emfh;
    }
    
    @Override
    public Map<UniqueEntityId, Genre> fetchAll() {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final String query = "SELECT g FROM GenreModel g";
            
            return (Map<UniqueEntityId, Genre>) em.createQuery(query)
                    .getResultStream()
                    .map(model -> GenreMapper.toDomain((GenreModel)model))
                    .collect(Collectors.toMap(Genre::getId, g -> g));

        } catch(Exception e) {
            LogService.getInstance().error(e);
            throw e;
        } finally {
            em.close();
        }

    }

    @Override
    public Genre fetchById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final GenreModel model = em.find(GenreModel.class, id.toValue());
            if(model == null) return null;
            
            return GenreMapper.toDomain(model);
            
        } catch(NoResultException e) {
            LogService.getInstance().error(e);
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public Set<Genre> fetchByIds(Set<UniqueEntityId> ids) {
        
        try {
            
            return Set.of();
        } catch(Exception e) {
            LogService.getInstance().error(e);
            throw e;
        }
        
    }
    
    @Override
    public Genre fetchByTitle(GenreTitle title) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final String query = "FROM GenreModel g WHERE g.title = :title";

            final GenreModel model = (GenreModel) em.createQuery(query)
                    .setParameter("title", title.getValue())
                    .getSingleResult();
            
            return GenreMapper.toDomain(model);
            
        } catch(Exception e) {
            LogService.getInstance().error(e);
            throw e;
        } finally {
            em.close();
        }

    }

    @Override
    public void save(Genre genre)
            throws ConstraintViolationException,
            MaxAllowedGenresExceededException,
            AccessControlException,
            UpdaterOrCreatorDoesNotExistException,
            Exception
    {
        
        GenreModel model;
        
        final EntityManager em = emfh.createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            tnx.begin();
        
            if(exists(genre.id)) { // updater existing genre

                model = em.find(GenreModel.class, genre.getId().toValue());

                GenreMapper.mergeForPersistence(genre, model);
                
                final UserModel updater = getAuthorizedUser(genre.getUpdaterId().toValue());
                if(updater == null) throw new UpdaterOrCreatorDoesNotExistException();
                
                model.setUpdater(updater);

                em.merge(model);

            } else { // insert new genre
                
                // check if maximum total number of genres has exceeded the limit
                final String query = "SELECT COUNT(genre) FROM GenreModel genre";
                final long totalGenreCount =
                        (long) em.createQuery(query)
                                .getSingleResult();
                if(totalGenreCount >= MAX_ALLOWED_GENRES)
                    throw new MaxAllowedGenresExceededException();

                final UserModel creator = getAuthorizedUser(genre.getCreatorId().toValue());
                if(creator == null) throw new UpdaterOrCreatorDoesNotExistException();
                
                model = GenreMapper.toPersistence(genre);

                model.setCreator(creator);
                model.setUpdater(creator);
                
                em.persist(model);

            }
            
            tnx.commit();
            
        } catch(RollbackException e) {
            
            tnx.rollback();
            
            // constraint violation exception
            if(
                    e.getCause() != null &&
                    e.getCause().getCause() != null &&
                    e.getCause().getCause() instanceof ConstraintViolationException
            ) {
                final ConstraintViolationException exception =
                        (ConstraintViolationException) e.getCause().getCause();
                final String constraintName = exception.getConstraintName();
                String message = "Unknown constraint violation";
                if(constraintName.equals("title_unique_key"))
                    message = "عنوان سبک تکراری است";
                if(constraintName.equals("title_in_persian_unique_key"))
                    message = "عنوان فارسی سبک تکراری است";
                throw new ConstraintViolationException(message, exception.getSQLException(), constraintName);
            }
            
            throw e;
        } catch(Exception e) {
            LogService.getInstance().error(e); // LOG
            tnx.rollback();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public boolean exists(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final GenreModel model = em.find(GenreModel.class, id.toValue());
            return model != null;
            
        } catch(Exception e) {
            LogService.getInstance().error(e);
            return false;
        } finally {
            em.close();
        }

    }

    @Override
    public void remove(Genre genre)
            throws UpdaterOrCreatorDoesNotExistException,
            Exception
    {

        if(getAuthorizedUser(genre.getCreatorId().toValue()) == null)
            throw new UpdaterOrCreatorDoesNotExistException();
        
        final EntityManager em = emfh.createEntityManager();
        final EntityTransaction tnx = em.getTransaction();

        final Set<Genre> subGenres = genre.getSubGenres();
        if(!subGenres.isEmpty()) {
            for(Genre subGenre : subGenres) {
                remove(subGenre);
            }
        }
        
        try {
            
            tnx.begin();
            
            final GenreModel model = em.find(GenreModel.class, genre.id.toValue());
            
            final List<LibraryEntityModel> entities =
                    em.createQuery(
                            "SELECT entities FROM LibraryEntityModel entities JOIN entities.genres genres WHERE genres.id = ?1",
                            LibraryEntityModel.class
                    )
                    .setParameter(1, model.getId())
                    .getResultList();

            entities.forEach(entity -> {
                entity.removeGenre(model);
                em.merge(entity);
            });
            
            em.remove(model);
            
            tnx.commit();
            
        } catch(Exception e) {
            tnx.rollback();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    private UserModel getAuthorizedUser(UUID id) {
        final EntityManager em = emfh.createEntityManager();
        try {
            final String query = "FROM UserModel u WHERE u.role IN (?1) AND u.id = ?2";
            return (UserModel) em.createQuery(query)
                .setParameter(1, Set.of(UserRoleEnum.ADMIN, UserRoleEnum.LIBRARY_MANAGER))
                .setParameter(2, id)
                .getSingleResult();
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }
    
    @Override
    public Genre toDomain(GenreModel model) {
        
        return Genre.reconstitute(
                model.getId(),
                model.getTitle(),
                model.getTitleInPersian(),
                model.getParentGenre() != null ?
                        toDomain(model.getParentGenre()) : null,
                model.getSubGenres() != null && !model.getSubGenres().isEmpty() ?
                        model.getSubGenres()
                            .stream()
                            .map(g -> toDomain(g))
                            .collect(Collectors.toSet())
                        :
                        null,
                model.getCreatedAt(),
                model.getLastModifiedAt(),
                model.getCreator().getId(),
                model.getUpdater().getId()
        ).getValue();
    }
    
    @Override
    public GenreModel toPersistence(Genre entity) {
        
        if(entity == null) return null;
        
        final GenreModel model = new GenreModel();
        
        model.setId(entity.id.toValue());
        model.setTitle(entity.getTitle() != null ? entity.getTitle().getValue() : null);
        model.setTitleInPersian(entity.getTitle() != null ? entity.getTitleInPersian().getValue() : null);
        model.setParentGenre(GenreMapper.toPersistence(entity.getParent()));
        model.setCreatedAt(entity.getCreatedAt().getValue());
        model.setLastModifiedAt(entity.getLastModifiedAt().getValue());
        
        return model;
        
    }
    
    public static GenreRepository getInstance() {
        return GenreRepositoryHolder.INSTANCE;
    }
    
    private static class GenreRepositoryHolder {
        
        final static EntityManagerFactoryHelper emfh =
                EntityManagerFactoryHelper.getInstance();

        private static final GenreRepository INSTANCE =
                new GenreRepository(emfh);
    }
}
