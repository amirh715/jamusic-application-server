/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import com.jamapplicationserver.infra.Persistence.database.Models.LibraryEntityModel;
import com.jamapplicationserver.infra.Persistence.database.Models.GenreModel;
import com.jamapplicationserver.modules.library.domain.Band.*;
import java.util.*;
import java.util.ArrayList;
import javax.persistence.*;
import javax.persistence.criteria.*;
import com.jamapplicationserver.core.infra.QueryScope;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.library.domain.core.*;
import java.util.stream.Collectors;
import com.jamapplicationserver.modules.library.repositories.mappers.*;
import com.jamapplicationserver.modules.library.domain.Band.Band;
import com.jamapplicationserver.modules.library.domain.Singer.Singer;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.domain.Track.Track;

/**
 *
 * @author amirhossein
 */
public class LibraryEntityRepository implements ILibraryEntityRepository {
    
    private final EntityManager em;
    
    private LibraryEntityRepository(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public LibraryEntityQueryScope<LibraryEntity> fetchById(UniqueEntityID id) {
        
        try {
            
            final LibraryEntityModel model =
                    this.em.find(LibraryEntityModel.class, id);
            
            return new LibraryEntityQueryScope();

        } catch(Exception e) {
            return null;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Artist> fetchArtistById(UniqueEntityID id) {
        
        try {
            
            final CriteriaBuilder cb = this.em.getCriteriaBuilder();
            final Root root = cb.createQuery().from(LibraryEntityModel.class);
            
            final Predicate predicate =
                    cb.or(
                            cb.equal(root.get("entity_type"), "S"),
                            cb.equal(root.get("entity_type"), "B")
                    );
            
            return new LibraryEntityQueryScope(List.of(predicate));
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
                
    }
    
    @Override
    public LibraryEntityQueryScope<Band> fetchBandById(UniqueEntityID id) {
        
        try {
            
            final CriteriaBuilder cb = this.em.getCriteriaBuilder();
            final Root root = cb.createQuery().from(LibraryEntityModel.class);
            
            final Predicate predicate =
                    cb.equal(root.get("type"), "B");
            
            return new LibraryEntityQueryScope(List.of(predicate));
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Singer> fetchSingerById(UniqueEntityID id) {
        
        try {
            
            final CriteriaBuilder cb = this.em.getCriteriaBuilder();
            final Root root = cb.createQuery().from(LibraryEntityModel.class);
            
            final Predicate predicate =
                    cb.equal(root.get("type"), "S");
            
            return new LibraryEntityQueryScope(List.of(predicate));
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Album> fetchAlbumById(UniqueEntityID id) {
        
        try {
            
            final CriteriaBuilder cb = this.em.getCriteriaBuilder();
            final Root root = cb.createQuery().from(LibraryEntityModel.class);
            
            final Predicate predicate =
                    cb.equal(root.get("type"), "A");
            
            return new LibraryEntityQueryScope(List.of(predicate));
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Track> fetchTrackById(UniqueEntityID id) {
        
        try {
            
            final CriteriaBuilder cb = this.em.getCriteriaBuilder();
            final Root root = cb.createQuery().from(LibraryEntityModel.class);
            
            final Predicate predicate =
                    cb.equal(root.get("type"), "T");
            
            return new LibraryEntityQueryScope(List.of(predicate));
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<LibraryEntity> fetchByFilters(LibraryEntityFilters filters) {
        
        try {
            
            ArrayList<Predicate> predicates = buildCriteriaPredicates(filters);
            
            return new LibraryEntityQueryScope(predicates);
            
        } catch(Exception e) {
            return null;
        }
        
    }
    
    @Override
    public void save(LibraryEntity entity) {
        
        final EntityTransaction tnx = this.em.getTransaction();
        
        try {
            
            final boolean exists = this.exists(entity.id);
            
            tnx.begin();
            
            final LibraryEntityModel model = LibraryEntityMapper.toPersistence(entity);
            
            if(exists)
                this.em.merge(model);
            else
                this.em.persist(model);
            
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            tnx.commit();
        }
        
    }
    
    @Override
    public void remove(LibraryEntity entity) {
        
        try {
            
            this.em.getTransaction().begin();
            
            this.em.remove(entity);
            
            this.em.getTransaction().commit();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public boolean exists(UniqueEntityID id) {
        
        try {
            
            final LibraryEntityModel model = this.em.find(LibraryEntityModel.class, id.toValue());
            
            return model != null;
            
        } catch(Exception e) {
            throw e;
        }

    }
    
    private static String toSearchPattern(String term) {
        return "%" + term + "%";
    }
    
    private ArrayList<Predicate> buildCriteriaPredicates(LibraryEntityFilters filters) {
        
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery cq = builder.createQuery();
        
        Root<LibraryEntityModel> root = cq.from(LibraryEntityModel.class);
        
        cq.select(root);
        
        cq.orderBy();
        
        List<Predicate> predicates = new ArrayList<>();
        
        if(filters != null) {

            // searchTerm
            if(filters.searchTerm != null) {
                predicates.add(
                        builder.or(
                                builder.like(root.get("title"), toSearchPattern(filters.searchTerm)),
                                builder.like(root.get("description"), toSearchPattern(filters.searchTerm)),
                                builder.like(root.get("tags"), toSearchPattern(filters.searchTerm)),
                                builder.like(root.get("lyrics"), toSearchPattern(filters.searchTerm))
                        )
                );
            }
            
            // entity type
            if(filters.type != null) {
                predicates.add(
                        builder.equal(root.get("entityType"), filters.type)
                );
            }
            
            // published
            if(filters.published != null)
                predicates.add(
                        builder.equal(root.get("published"), filters.published)
                );
            
            // is flagged
            if(filters.flagged != null) {
                
                if(filters.flagged)
                    predicates.add(builder.isNotNull(root.get("flagNote")));
                else
                    predicates.add(builder.isNull(root.get("flagNote")));
                
            }
            
            // has image
            if(filters.hasImage != null) {
                
                if(filters.hasImage)
                    predicates.add(builder.isNotNull(root.get("imagePath")));
                else
                    predicates.add(builder.isNull(root.get("imagePath")));
                
            }
            
            // createdAt from till
            if(
                    filters.createdAtFrom != null ||
                    filters.createdAtTill != null
            ) {
                predicates.add(
                        builder.between(
                                root.get("createdAt"),
                                filters.createdAtFrom.getValue(),
                                filters.createdAtTill.getValue()
                        )
                );
            }
            
            // lastModifiedAt from till
            if(
                    filters.lastModifiedAtFrom != null ||
                    filters.lastModifiedAtTill != null
            ) {
                predicates.add(
                        builder.between(
                                root.get("lastModifiedAt"),
                                filters.lastModifiedAtFrom.getValue(),
                                filters.lastModifiedAtTill.getValue()
                        )
                );
            }
            
            // genres
            if(filters.genres != null) {
                Set<GenreModel> gm;
                predicates.add(
                        root.get("genres").in()
                );
            }
            
            // creator Id
            if(filters.creatorId != null)
                predicates.add(builder.equal(root.get("creator").get("id"), filters.creatorId));
            
            // updater Id
            if(filters.updaterId != null)
                predicates.add(builder.equal(root.get("updater").get("id"), filters.updaterId));
            
        } // if filters != null ENDS
        
        return new ArrayList(predicates);
    }

    
    public static LibraryEntityRepository getInstance() {
        return LibraryEntityRepositoryHolder.INSTANCE;
    }
    
    public static class LibraryEntityRepositoryHolder {
        
        final static EntityManager em =
                EntityManagerFactoryHelper.getInstance()
                .getFactory()
                .createEntityManager();
        
        private static final LibraryEntityRepository INSTANCE =
                new LibraryEntityRepository(em);
        
    }
    
    
    
}
