/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.services;

import java.util.*;
import java.util.stream.*;
import javax.persistence.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.infra.DTOs.entities.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.library.repositories.LibraryEntityFilters;
import java.time.LocalDateTime;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author dada
 */
public class LibraryQueryService implements ILibraryQueryService {
    
    private final EntityManager em;
    
    @Override
    public Set<LibraryEntityDetails> getLibraryEntitiesByFilters(LibraryEntityFilters filters) {
        
        try {
            
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<LibraryEntityModel> cq = cb.createQuery(LibraryEntityModel.class);
            final Root<LibraryEntityModel> root = cq.from(LibraryEntityModel.class);
            
            final ArrayList<Predicate> predicates =
                    buildCriteriaPredicates(
                            cb,
                            cq,
                            root,
                            filters
                    );
            
            cq.where(
                    cb.and(
                            predicates.toArray(
                                    new Predicate[predicates.size()]
                            )
                    )
            );
            
            return em.createQuery(cq)
                    .getResultList()
                    .stream()
                    .map(entity -> LibraryEntityDetails.create((LibraryEntityModel) entity))
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public Set<LibraryEntitySummary> getLibraryEntitiesSummaryByFilters(LibraryEntityFilters filters) {
        
        try {
            
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<LibraryEntityModel> cq = cb.createQuery(LibraryEntityModel.class);
            final Root<LibraryEntityModel> root = cq.from(LibraryEntityModel.class);
            
            final ArrayList<Predicate> predicates =
                    buildCriteriaPredicates(
                            cb,
                            cq,
                            root,
                            filters
                    );
            
            cq.where(
                    cb.and(
                            predicates.toArray(
                                    new Predicate[predicates.size()]
                            )
                    )
            );
            
            return em.createQuery(cq)
                    .getResultList()
                    .stream()
                    .map(entity -> LibraryEntitySummary.create(entity))
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public LibraryEntityDetails getLibraryEntityById(UniqueEntityId id, Class<LibraryEntityModel> clazz) {
        
        try {
            
            final LibraryEntityModel entity = em.find(clazz, id.toValue());
            if(entity == null) return null;
            
            return LibraryEntityDetails.create(entity);
            
        } catch(Exception e) {
            return null;
        }
        
    }
    
    @Override
    public LibraryEntityDetails getLibraryEntityById(UniqueEntityId id) {
        
        try {
            
            final LibraryEntityModel entity = em.find(LibraryEntityModel.class, id.toValue());
            if(entity == null) return null;
            
            return LibraryEntityDetails.create(entity);
            
        } catch(Exception e) {
            return null;
        }
        
    }
    
    @Override
    public LibraryEntitySummary getLibraryEntitySummaryById(UniqueEntityId id) {
        
        try {
            
            final LibraryEntityModel entity =
                    em.createQuery(
                            "SELECT entity FROM LibraryEntityModel entity WHERE entity.id = ?1",
                            LibraryEntityModel.class
                    )
                    .setParameter(1, id.toValue())
                    .getSingleResult();
            if(entity == null) return null;
            
            return LibraryEntitySummary.create(entity);
            
        } catch(Exception e) {
            return null;
        }
        
    }
    
    @Override
    public Set<GenreDetails> getAllGenres() {
        
        try {
            
            final List<GenreModel> genres =
                    em.createQuery("SELECT genre FROM GenreModel genre")
                    .getResultList();
            
            return genres
                    .stream()
                    .map(genre -> GenreDetails.create(genre))
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            e.printStackTrace();
            return Set.of();
        }
        
    }
    
    @Override
    public GenreDetails getGenreById(UniqueEntityId id) {
        
        try {
            
            final GenreModel genre = em.find(GenreModel.class, id.toValue());
            if(genre == null) return null;
            
            return GenreDetails.create(genre);
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public Set<PlaylistDetails> getPlaylistsByPlayerId(UniqueEntityId playerId) {
        try {
            
            final List<PlaylistModel> playlists =
                    em.createQuery("SELECT playlist FROM PlaylistModel playlist WHERE playlist.player.id = ?1")
                    .setParameter(1, playerId.toValue())
                    .getResultList();
            
            return playlists
                    .stream()
                    .map(playlist -> PlaylistDetails.create(playlist))
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public PlaylistDetails getPlaylistById(UniqueEntityId playlistId) {
        try {
            
            final PlaylistModel playlist = em.find(PlaylistModel.class, playlistId.toValue());
            if(playlist == null) return null;
            
            return PlaylistDetails.create(playlist);
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private ArrayList<Predicate> buildCriteriaPredicates(
            CriteriaBuilder cb,
            CriteriaQuery<LibraryEntityModel> cq,
            Root<LibraryEntityModel> root,
            LibraryEntityFilters filters
    ) {
        
        cq.select(root);
        
        cq.orderBy();
        
        List<Predicate> predicates = new ArrayList<>();
        
        if(filters != null) {

            // searchTerm
            if(filters.searchTerm != null) {
                predicates.add(
                        cb.or(
                            cb.like(root.get("title"), toSearchPattern(filters.searchTerm)),
                            cb.like(root.get("description"), toSearchPattern(filters.searchTerm)),
                            cb.like(root.get("tags"), toSearchPattern(filters.searchTerm))
                        )
                );
            }
            
            // entity type
            if(filters.type != null) {
                predicates.add(
                        cb.equal(root.get("entityType"), filters.type)
                );
            }
            
            // published
            if(filters.published != null) {
                predicates.add(
                        cb.equal(root.get("published"), filters.published)
                );
            }
            
            // is flagged
            if(filters.isFlagged != null) {
                
                if(filters.isFlagged)
                    predicates.add(cb.isNotNull(root.get("flagNote")));
                else
                    predicates.add(cb.isNull(root.get("flagNote")));
                
            }
            
            // has image
            if(filters.hasImage != null) {
                
                if(filters.hasImage)
                    predicates.add(cb.isNotNull(root.get("imagePath")));
                else
                    predicates.add(cb.isNull(root.get("imagePath")));
                
            }
            
            // createdAt from till
            if(
                    filters.createdAtFrom != null ||
                    filters.createdAtTill != null
            ) {
                final LocalDateTime from =
                        filters.createdAtFrom != null ?
                        filters.createdAtFrom.getValue() :
                        LocalDateTime.of(2020, 1, 1, 0, 0);
                final LocalDateTime till =
                        filters.lastModifiedAtFrom != null ?
                        filters.lastModifiedAtFrom.getValue() :
                        DateTime.createNow().getValue();
                predicates.add(
                        cb.between(
                                root.get("createdAt"),
                                from,
                                till
                        )
                );
            }
            
            // lastModifiedAt from till
            if(
                    filters.lastModifiedAtFrom != null ||
                    filters.lastModifiedAtTill != null
            ) {
                final LocalDateTime from =
                        filters.lastModifiedAtFrom != null ?
                        filters.lastModifiedAtFrom.getValue() :
                        LocalDateTime.of(2020, 1, 1, 0, 0);
                final LocalDateTime till =
                        filters.lastModifiedAtFrom != null ?
                        filters.lastModifiedAtFrom.getValue() :
                        DateTime.createNow().getValue();
                predicates.add(
                        cb.between(
                                root.get("lastModifiedAt"),
                                from,
                                till
                        )
                );
            }
            
            // genres
            if(filters.genreIds != null) {
                Set<UUID> genreIds =
                        filters
                                .genreIds
                                .stream()
                                .map(genreId -> genreId.toValue())
                                .collect(Collectors.toSet());
                predicates.add(
                        root.get("genres").in(genreIds)
                );
            }
            
            // creator Id
            if(filters.creatorId != null)
                predicates.add(cb.equal(root.get("creator").get("id"), filters.creatorId));
            
            // updater Id
            if(filters.updaterId != null)
                predicates.add(cb.equal(root.get("updater").get("id"), filters.updaterId));
            
        } // if filters != null ENDS
        
        return new ArrayList(predicates);
    }
        
    private String toSearchPattern(String term) {
        return "%" + term + "%";
    }
    
    private LibraryQueryService(EntityManager em) {
        this.em = em;
    }
    
    public static LibraryQueryService getInstance() {
        return LibraryQueryServiceHolder.INSTANCE;
    }
    
    private static class LibraryQueryServiceHolder {

        private static final LibraryQueryService INSTANCE =
                new LibraryQueryService(EntityManagerFactoryHelper.getInstance().getEntityManager());
    }
}
