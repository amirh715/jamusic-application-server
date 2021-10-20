/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.services;

import com.jamapplicationserver.modules.library.infra.DTOs.queries.PlaylistDetails;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.GenreDetails;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.LibraryEntitySummary;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.LibraryEntityDetails;
import java.util.*;
import java.util.stream.*;
import javax.persistence.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.library.repositories.LibraryEntityFilters;
import java.time.LocalDateTime;
import java.time.*;
import javax.persistence.criteria.*;

/**
 *
 * @author dada
 */
public class LibraryQueryService implements ILibraryQueryService {
    
    private final EntityManagerFactoryHelper emfh;
    
    @Override
    public Set<LibraryEntityDetails> getLibraryEntitiesByFilters(LibraryEntityFilters filters) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            // fetch and cache associations of Band and Album
            // ref: https://thorben-janssen.com/fetch-association-of-subclass/
            // fetch bands' members
            {
                filters.type = LibraryEntityType.B;
                final CriteriaQuery<BandModel> query = buildQuery(filters, em);
                em.createQuery(query).getResultList();
            }
            // fetch albums' tracks
            {
                filters.type = LibraryEntityType.A;
                final CriteriaQuery<AlbumModel> query = buildQuery(filters, em);
                em.createQuery(query).getResultList();
            }
            
            final CriteriaQuery<LibraryEntityModel> query = buildQuery(filters, em);
            
            return em.createQuery(query)
                    .getResultList()
                    .stream()
                    .map(entity -> LibraryEntityDetails.create(entity))
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public Set<LibraryEntitySummary> getLibraryEntitiesSummaryByFilters(LibraryEntityFilters filters) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final CriteriaQuery<LibraryEntityModel> query = buildQuery(filters, em);
            
            return em.createQuery(query)
                    .getResultList()
                    .stream()
                    .map(entity -> LibraryEntitySummary.create(entity))
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public LibraryEntityDetails getLibraryEntityById(UniqueEntityId id, Class<LibraryEntityModel> clazz) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final LibraryEntityModel entity = em.find(clazz, id.toValue());
            if(entity == null) return null;
            
            return LibraryEntityDetails.create(entity);
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public LibraryEntityDetails getLibraryEntityById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final LibraryEntityModel entity = em.find(LibraryEntityModel.class, id.toValue());
            if(entity == null) return null;
            
            return LibraryEntityDetails.create(entity);
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public LibraryEntitySummary getLibraryEntitySummaryById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
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
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public Set<GenreDetails> getAllGenres() {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final List<GenreModel> genres =
                    em.createQuery("SELECT genre FROM GenreModel genre WHERE genre.parentGenre IS NULL", GenreModel.class)
                    .getResultList();
            
            return genres
                    .stream()
                    .map(genre -> GenreDetails.create(genre))
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public GenreDetails getGenreById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final GenreModel genre = em.find(GenreModel.class, id.toValue());
            if(genre == null) return null;
            
            return GenreDetails.create(genre);
            
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public Set<PlaylistDetails> getPlaylistsByPlayerId(UniqueEntityId playerId) {
        
        final EntityManager em = emfh.createEntityManager();
        
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
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public PlaylistDetails getPlaylistById(UniqueEntityId playlistId) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final PlaylistModel playlist = em.find(PlaylistModel.class, playlistId.toValue());
            if(playlist == null) return null;
            
            return PlaylistDetails.create(playlist);
            
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    private CriteriaQuery buildQuery(
            LibraryEntityFilters filters,
            EntityManager em
    ) {
        
        
        
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery query = builder.createQuery(LibraryEntityModel.class);
        
        final ArrayList<Predicate> predicates = new ArrayList<>();
        
        Root root;
        Class<? extends LibraryEntityModel> entityType;
        
        if(filters != null) {

            if(filters.type.isArtwork())
                entityType = ArtworkModel.class;
            else if(filters.type.isAlbum())
                entityType = AlbumModel.class;
            else if(filters.type.isTrack())
                entityType = TrackModel.class;
            else if(filters.type.isArtist())
                entityType = ArtistModel.class;
            else if(filters.type.isBand())
                entityType = BandModel.class;
            else if(filters.type.isSinger())
                entityType = SingerModel.class;
            else
                entityType = LibraryEntityModel.class;

            root = query.from(entityType);

            // entity type
            if(entityType != LibraryEntityModel.class) {
                final Predicate predicate = builder.equal(root.type(), entityType);
                predicates.add(predicate);
            }

            // search term
            if(filters.searchTerm != null) {
                final String searchTerm = filters.searchTerm;
                final Predicate predicate =
                        builder.or(
                                builder.like(root.get("title"), toSearchPattern(searchTerm)),
                                builder.like(root.get("description"), toSearchPattern(searchTerm)),
                                builder.like(root.get("tags"), toSearchPattern(searchTerm)),
                                builder.and(
                                        builder.equal(root.type(), ArtworkModel.class),
                                        builder.like(((Root<ArtworkModel>) (Root<?>) root).get("inheritedTags"), toSearchPattern(searchTerm))
                                )
                        );
                predicates.add(predicate);
            }
            
            // published
            if(filters.published != null) {
                final boolean published = filters.published;
                final Predicate predicate =
                        published ?
                        builder.isTrue(root.get("published")) :
                        builder.isFalse(root.get("published"));
                predicates.add(predicate);
            }
            
            // has image
            if(filters.hasImage != null) {
                final boolean hasImage = filters.hasImage;
                final Predicate predicate =
                        hasImage ?
                        builder.isNotNull(root.get("imagePath")) :
                        builder.isNull(root.get("imagePath"));
                predicates.add(predicate);
            }
            
            // is flagged
            if(filters.isFlagged != null) {
                final boolean isFlagged = filters.isFlagged;
                final Predicate predicate =
                        isFlagged ?
                        builder.isNotNull(root.get("flagNote")) :
                        builder.isNull(root.get("flagNote"));
                predicates.add(predicate);
            }
            
            // rate from to
            if(filters.rateFrom != null || filters.rateTo != null) {
                final double rateFrom =
                        filters.rateFrom != null ?
                        filters.rateFrom.getValue() :
                        0.0;
                final double rateTo =
                        filters.rateTo != null ?
                        filters.rateTo.getValue() :
                        5.0;
                final Predicate predicate =
                        builder.between(root.get("rate"), rateFrom, rateTo);
                predicates.add(predicate);
            }
            
            // total played count from to
            if(filters.totalPlayedCountFrom != null || filters.totalPlayedCountTo != null) {
                final long totalPlayedCountFrom =
                        filters.totalPlayedCountFrom != null ?
                        filters.totalPlayedCountFrom : 0;
                final long totalPlayedCountTo =
                        filters.totalPlayedCountTo != null ?
                        filters.totalPlayedCountTo : Long.MAX_VALUE;
                final Predicate predicate =
                        builder.between(
                                root.get("totalPlayedCount"),
                                totalPlayedCountFrom,
                                totalPlayedCountTo
                        );
                predicates.add(predicate);
            }
            
            // duration from to
            if(filters.durationFrom != null || filters.durationTo != null) {
                final long durationFrom =
                        filters.durationFrom != null ?
                        filters.durationFrom : 0;
                final long durationTo =
                        filters.durationTo != null ?
                        filters.durationTo : Long.MAX_VALUE;
                final Predicate predicate =
                        builder.between(root.get("duration"), durationFrom, durationTo);
                predicates.add(predicate);
            }
            
            // creator id
            if(filters.creatorId != null) {
                final UUID creatorId = filters.creatorId.toValue();
                final Predicate predicate =
                        builder.equal(root.get("creator").get("id"), creatorId);
                predicates.add(predicate);
            }
            
            // updater id
            if(filters.updaterId != null) {
                final UUID updaterId = filters.updaterId.toValue();
                final Predicate predicate =
                        builder.equal(root.get("updater").get("id"), updaterId);
                predicates.add(predicate);
            }
            
            // created at from till
            if(filters.createdAtFrom != null || filters.createdAtTill != null) {
                final LocalDateTime createdAtFrom =
                        filters.createdAtFrom != null ?
                        filters.createdAtFrom.getValue() :
                        LocalDateTime.of(2021, Month.JANUARY, 1, 1, 1);
                final LocalDateTime createdAtTill =
                        filters.createdAtTill != null ?
                        filters.createdAtTill.getValue() :
                        LocalDateTime.now();
                final Predicate predicate =
                        builder.between(root.get("createdAt"), createdAtFrom, createdAtTill);
                predicates.add(predicate);
            }
            
            // last modified at from till
            if(filters.lastModifiedAtFrom != null || filters.lastModifiedAtTill != null) {
                final LocalDateTime lastModifiedAtFrom =
                        filters.lastModifiedAtFrom != null ?
                        filters.lastModifiedAtFrom.getValue() :
                        LocalDateTime.of(2021, Month.JANUARY, 1, 1, 1);
                final LocalDateTime lastModifiedAtTill =
                        filters.lastModifiedAtTill != null ?
                        filters.lastModifiedAtTill.getValue() :
                        LocalDateTime.now();
                final Predicate predicate =
                        builder.between(
                                root.get("lastModifiedAt"),
                                lastModifiedAtFrom,
                                lastModifiedAtTill
                        );
                predicates.add(predicate);
            }
            
            query.where(
                    builder.and(
                            predicates.toArray(new Predicate[predicates.size()])
                    )
            );
            
        } else {
            root = query.from(LibraryEntityModel.class);
        }
        
        root.fetch("genres", JoinType.LEFT);
        
        query.select(root);
        
        return query;
    }
        
    private String toSearchPattern(String term) {
        return "%" + term + "%";
    }
    
    private LibraryQueryService(EntityManagerFactoryHelper emfh) {
        this.emfh = emfh;
    }
    
    public static LibraryQueryService getInstance() {
        return LibraryQueryServiceHolder.INSTANCE;
    }
    
    private static class LibraryQueryServiceHolder {

        private static final LibraryQueryService INSTANCE =
                new LibraryQueryService(EntityManagerFactoryHelper.getInstance());
    }
}
