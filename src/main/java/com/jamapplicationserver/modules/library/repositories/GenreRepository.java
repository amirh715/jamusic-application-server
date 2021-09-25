/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import javax.persistence.*;
import com.jamapplicationserver.modules.library.repositories.exceptions.*;
import org.hibernate.exception.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.library.repositories.mappers.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.infra.Persistence.database.*;

/**
 *
 * @author dada
 */
public class GenreRepository implements IGenreRepository {
    
    private final EntityManagerFactory emf;
    
    private static final int MAX_ALLOWED_GENRES = 30;
    
    private GenreRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    @Override
    public Map<UniqueEntityId, Genre> fetchAll() {
        
        final EntityManager em = emf.createEntityManager();
        
        try {
            
            final String query = "SELECT g FROM GenreModel g";
            
            return (Map<UniqueEntityId, Genre>) em.createQuery(query)
                    .getResultStream()
                    .map(model -> GenreMapper.toDomain((GenreModel)model))
                    .collect(Collectors.toMap(Genre::getId, g -> g));

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Genre fetchById(UniqueEntityId id) {
        
        final EntityManager em = emf.createEntityManager();
        
        try {
            
            final GenreModel model = em.find(GenreModel.class, id.toValue());
            if(model == null) return null;
            
            return GenreMapper.toDomain(model);
            
        } catch(NoResultException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public Set<Genre> fetchByIds(Set<UniqueEntityId> ids) {
        
        try {
            
            return Set.of();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
    }
    
    @Override
    public Genre fetchByTitle(GenreTitle title) {
        
        final EntityManager em = emf.createEntityManager();
        
        try {
            
            final String query = "FROM GenreModel g WHERE g.title = :title";

            final GenreModel model = (GenreModel) em.createQuery(query)
                    .setParameter("title", title.getValue())
                    .getSingleResult();
            
            return GenreMapper.toDomain(model);
            
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
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
        
        final EntityManager em = emf.createEntityManager();
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
            e.printStackTrace(); // LOG
            tnx.rollback();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public boolean exists(UniqueEntityId id) {
        
        final EntityManager em = emf.createEntityManager();
        
        try {
            
            final GenreModel model = em.find(GenreModel.class, id.toValue());
            return model != null;
            
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    
    // 1. remove entity_genres corresponding to the genre being removed.
    // 2. remove all of genre's sub-genres.
    // 3. remove the genre itself.
    @Override
    public void remove(Genre genre)
            throws UpdaterOrCreatorDoesNotExistException,
            Exception
    {

        if(getAuthorizedUser(genre.getCreatorId().toValue()) == null)
            throw new UpdaterOrCreatorDoesNotExistException();
        
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tnx = em.getTransaction();

        try {
            
            tnx.begin();
            
            final GenreModel model = em.find(GenreModel.class, genre.id.toValue());
            
            final List<LibraryEntityModel> entities =
                    em.createQuery(
                            "SELECT entity FROM LibraryEntityModel entity JOIN entity.genres genres WHERE genres.id = ?1",
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
        final EntityManager em = emf.createEntityManager();
        try {
            final String query = "FROM UserModel u WHERE u.role IN (?1) AND u.id = ?2";
            return (UserModel) em.createQuery(query)
                .setParameter(1, Set.of(UserRoleEnum.ADMIN, UserRoleEnum.LIBRARY_MANAGER))
                .setParameter(2, id)
                .getSingleResult();
        } catch(Exception e) {
            return null;
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
        
        final static EntityManagerFactory emf =
                EntityManagerFactoryHelper.getInstance().getFactory();

        private static final GenreRepository INSTANCE =
                new GenreRepository(emf);
    }
}
