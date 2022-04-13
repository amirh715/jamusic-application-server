/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;
import javax.persistence.criteria.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.repositories.mappers.*;
import com.jamapplicationserver.modules.library.domain.Band.Band;
import com.jamapplicationserver.modules.library.domain.Singer.Singer;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author amirhossein
 */
public class LibraryEntityRepository implements ILibraryEntityRepository {
    
    private final EntityManagerFactoryHelper emfh;
    
    private LibraryEntityRepository(EntityManagerFactoryHelper emfh) {
        this.emfh = emfh;
    }
    
    @Override
    public LibraryEntityQueryScope<LibraryEntity> fetchById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final CriteriaBuilder builder = em.getCriteriaBuilder();
            final CriteriaQuery<LibraryEntityModel> query =
                    builder.createQuery(LibraryEntityModel.class);
            final Root root = query.from(LibraryEntityModel.class);
            
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("id"), id.toValue()));
            
            return new LibraryEntityQueryScope(
                    em,
                    query,
                    root,
                    predicates
            );

        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Artist> fetchArtistById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final CriteriaBuilder builder = em.getCriteriaBuilder();
            final CriteriaQuery<ArtistModel> query = builder.createQuery(ArtistModel.class);
            final Root<ArtistModel> root = query.from(ArtistModel.class);
            
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("id"), id.toValue()));

            return new LibraryEntityQueryScope<>(
                    em,
                    query,
                    root,
                    predicates
            );

        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Artwork> fetchArtworkById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final CriteriaBuilder builder = em.getCriteriaBuilder();
            final CriteriaQuery<ArtworkModel> query = builder.createQuery(ArtworkModel.class);
            final Root<ArtworkModel> root = query.from(ArtworkModel.class);
            
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("id"), id.toValue()));
            
            return new LibraryEntityQueryScope(
                    em,
                    query,
                    root,
                    predicates
            );
            
        } catch(NoResultException e) {
            return null;
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Band> fetchBandById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final CriteriaBuilder builder = em.getCriteriaBuilder();
            final CriteriaQuery<BandModel> query = builder.createQuery(BandModel.class);
            final Root<BandModel> root = builder.createQuery().from(BandModel.class);
            
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("id"), id.toValue()));

            return new LibraryEntityQueryScope(
                    em,
                    query,
                    root,
                    predicates
            );
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Singer> fetchSingerById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final CriteriaBuilder builder = em.getCriteriaBuilder();
            final CriteriaQuery<SingerModel> query = builder.createQuery(SingerModel.class);
            final Root<SingerModel> root = query.from(SingerModel.class);
            
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("id"), id.toValue()));
            
            return new LibraryEntityQueryScope(
                    em,
                    query,
                    root,
                    predicates
            );
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Album> fetchAlbumById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final CriteriaBuilder builder = em.getCriteriaBuilder();
            final CriteriaQuery<AlbumModel> query = builder.createQuery(AlbumModel.class);
            final Root<AlbumModel> root = query.from(AlbumModel.class);
            
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("id"), id.toValue()));
            
            return new LibraryEntityQueryScope(
                    em,
                    query,
                    root,
                    predicates
            );
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Track> fetchTrackById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final CriteriaBuilder builder = em.getCriteriaBuilder();
            final CriteriaQuery<TrackModel> query = builder.createQuery(TrackModel.class);
            final Root<TrackModel> root = query.from(TrackModel.class);
            
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("id"), id.toValue()));
            
            return new LibraryEntityQueryScope(
                    em,
                    query,
                    root,
                    predicates
            );
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<LibraryEntity> fetchByFilters(LibraryEntityFilters filters) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            return buildQueryScope(filters, em);
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public void save(LibraryEntity entity) throws EntityNotFoundException, Exception {

        final EntityManager em = emfh.createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {
                                    
            LibraryEntityModel model;
            
            tnx.begin();
            
            model = toPersistence(entity, em);
            
            if(exists(entity.getId())) {
                em.merge(model);
            } else {
                em.persist(model);
            }
            
            tnx.commit();

        } catch(EntityNotFoundException e) {
            LogService.getInstance().error(e);
            tnx.rollback();
            throw e;
        } catch(RollbackException e) {
            LogService.getInstance().error(e);
            tnx.rollback();
            throw e;
        } catch(Exception e) {
            LogService.getInstance().error(e);
            tnx.rollback();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public void remove(LibraryEntity entity) {
        
        final EntityManager em = emfh.createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            tnx.begin();
            
            final LibraryEntityModel model =
                    em.find(LibraryEntityModel.class, entity.id.toValue());
            
            if(!model.getReports().isEmpty()) {
                model.getReports().forEach(report -> em.remove(report));
            }
            
            // remove from playlist tracks
            {
                final String query = "DELETE FROM jamschema.playlist_tracks pt "
                        + "WHERE pt.track_id = ?1";
                em.createNativeQuery(query)
                        .setParameter(1, model.getId())
                        .executeUpdate();
            }
            
            if(entity instanceof Band) {
                
                ((BandModel) model).getTracks()
                        .forEach(track -> em.remove(track));
                
                ((BandModel) model).getAlbums()
                        .forEach(album -> em.remove(album));
                
            } else if(entity instanceof Singer) {
                
                for(TrackModel track : ((SingerModel) model).getTracks()) {
                    em.remove(track);
                }
                
                ((SingerModel) model).getAlbums()
                        .forEach(album -> em.remove(album));
                
            } else if(entity instanceof Album) {
                
                ((AlbumModel) model).getTracks()
                        .forEach(track -> em.remove(track));
                
            } else {
                
                em.remove(((TrackModel) model));
                
            }
            
            em.remove(model);
            
            tnx.commit();
        } catch(Exception e) {
            LogService.getInstance().error(e);
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
            
            final LibraryEntityModel model = em.find(LibraryEntityModel.class, id.toValue());
            
            return model != null;
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }

    }
    
    private static String toSearchPattern(String term) {
        return "%" + term + "%";
    }
    
    private LibraryEntityQueryScope buildQueryScope(
            LibraryEntityFilters filters,
            EntityManager em
    ) {
        
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery query = builder.createQuery(LibraryEntityModel.class);
        
        final ArrayList<Predicate> predicates = new ArrayList<>();
        
        Root<LibraryEntityModel> root;
        Class<? extends LibraryEntityModel> entityType;
        
        if(filters != null) {

            if(filters.type != null) {
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
            } else {
                entityType = LibraryEntityModel.class;
            }

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
                                builder.like(root.get("inheritedTags"), toSearchPattern(searchTerm))
                        );
                predicates.add(predicate);
            }
            
            // artworks with artist id
            if(filters.artistId != null) {
                final Root<ArtworkModel> artworkRoot =
                        builder.treat(root, ArtworkModel.class);
                final Predicate predicate =
                        builder.equal(
                                artworkRoot.get("artist").get("id"),
                                filters.artistId.toValue()
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
            
        } else {
            root = query.from(LibraryEntityModel.class);
        }
        
        root.fetch("genres", JoinType.LEFT);
        
        query.select(root);
        
        return new LibraryEntityQueryScope(em, query, root, predicates);
    }
    
    @Override
    public LibraryEntity toDomain(LibraryEntityModel model, EntityManager em) {
        
        LibraryEntity entity;
        
        Set<Genre> genres = null;
        if(!model.getGenres().isEmpty()) {
            genres =
                    model.getGenres()
                    .stream()
                    .map(genre -> GenreMapper.toDomain(genre))
                    .collect(Collectors.toSet());
        }
        
        Set<Tag> tags = null;
        if(!model.getTags().isEmpty()) {
            tags =
                    model.getTags()
                    .stream()
                    .map(tag -> Tag.create(tag).getValue())
                    .collect(Collectors.toSet());
        }
        
        if(model instanceof ArtistModel) {
            
            final Set<UUID> albumsIds =
                    ((ArtistModel) model).getAlbums()
                            .stream()
                            .map(album -> album.getId())
                            .collect(Collectors.toSet());
            final Set<UUID> allTracksIds =
                    ((ArtistModel) model).getTracks()
                            .stream()
                            .map(track -> track.getId())
                            .collect(Collectors.toSet());
            final Set<UUID> singleTracksIds =
                    ((ArtistModel) model).getSingleTracks()
                            .stream()
                            .map(track -> track.getId())
                            .collect(Collectors.toSet());
            
            if(model instanceof BandModel) { // Band
                
//                final Set<Singer> members =
//                        ((BandModel) model).getMembers()
//                        .stream()
//                        .map(member -> (Singer) toDomain(member, em))
//                        .collect(Collectors.toSet());
                final Set<Singer> members = Set.of();
                
                entity = Band.reconstitute(
                        model.getId(),
                        model.getTitle(),
                        model.getDescription(),
                        model.isPublished(),
                        model.getFlagNote(),
                        tags,
                        genres,
                        model.getImagePath(),
                        model.getTotalPlayedCount(),
                        model.getRate(),
                        model.getDuration(),
                        model.getCreatedAt(),
                        model.getLastModifiedAt(),
                        model.getCreator().getId(),
                        model.getUpdater().getId(),
                        ((BandModel) model).getInstagramId(),
                        albumsIds,
                        allTracksIds,
                        singleTracksIds,
                        members
                ).getValue();
                
            } else { // Singer
                
                entity = Singer.reconstitute(
                        model.getId(),
                        model.getTitle(),
                        model.getDescription(),
                        model.isPublished(),
                        model.getFlagNote(),
                        tags,
                        genres,
                        model.getImagePath(),
                        model.getTotalPlayedCount(),
                        model.getRate(),
                        model.getDuration(),
                        model.getCreatedAt(),
                        model.getLastModifiedAt(),
                        model.getCreator().getId(),
                        model.getUpdater().getId(),
                        ((ArtistModel) model).getInstagramId(),
                        albumsIds,
                        allTracksIds,
                        singleTracksIds
                ).getValue();
                
            }
            
        } else {
            
            Artist artist = null;
            if(((ArtworkModel) model).getArtist() != null) {
                artist = (Artist) toDomain(((ArtworkModel) model).getArtist(), em);
            }
            
            if(((ArtworkModel) model).getArtworksInheritedTags() != null) {
                
            }
            
            if(model instanceof AlbumModel) { // Album
                
                final Set<Track> tracks =
                        ((AlbumModel) model).getTracks()
                        .stream()
                        .map(track -> (Track) toDomain(track, em))
                        .collect(Collectors.toSet());
            
                entity = Album.reconstitute(
                        model.getId(),
                        model.getTitle(),
                        model.getDescription(),
                        model.isPublished(),
                        model.getFlagNote(),
                        tags,
                        genres,
                        model.getImagePath(),
                        model.getTotalPlayedCount(),
                        model.getRate(),
                        model.getDuration(),
                        model.getCreatedAt(),
                        model.getLastModifiedAt(),
                        model.getCreator().getId(),
                        model.getUpdater().getId(),
                        ((AlbumModel) model).getRecordLabel(),
                        ((AlbumModel) model).getProducer(),
                        ((AlbumModel) model).getReleaseDate(),
                        artist,
                        tracks
                ).getValue();
                
            } else { // Track

                Album album = null;
                if(((TrackModel) model).getAlbum() != null) {
                    album = Album.reconstitute(
                            ((TrackModel) model).getAlbum().getId(),
                            ((TrackModel) model).getAlbum().getTitle(),
                            ((TrackModel) model).getAlbum().getDescription(),
                            ((TrackModel) model).getAlbum().isPublished(),
                            ((TrackModel) model).getAlbum().getFlagNote(),
                            ((TrackModel) model).getAlbum().getTags()
                                .stream()
                                .map(tag -> Tag.create(tag).getValue())
                                .collect(Collectors.toSet()),
                            ((TrackModel) model).getAlbum().getGenres() != null &&
                                    !((TrackModel) model).getAlbum().getGenres().isEmpty() ?
                            ((TrackModel) model).getAlbum().getGenres()
                                .stream()
                                .map(genre -> GenreMapper.toDomain(genre))
                                .collect(Collectors.toSet()) : null,
                            ((TrackModel) model).getAlbum().getImagePath(),
                            ((TrackModel) model).getAlbum().getTotalPlayedCount(),
                            ((TrackModel) model).getAlbum().getRate(),
                            ((TrackModel) model).getAlbum().getDuration(),
                            ((TrackModel) model).getAlbum().getCreatedAt(),
                            ((TrackModel) model).getAlbum().getLastModifiedAt(),
                            ((TrackModel) model).getAlbum().getCreator().getId(),
                            ((TrackModel) model).getAlbum().getUpdater().getId(),
                            ((TrackModel) model).getAlbum().getRecordLabel(),
                            ((TrackModel) model).getAlbum().getProducer(),
                            ((TrackModel) model).getAlbum().getReleaseDate(),
                            (Artist) toDomain(((TrackModel) model).getAlbum().getArtist(), em),
                            null
                    ).getValue();

                }
                
                entity = Track.reconstitute(
                        model.getId(),
                        model.getTitle(),
                        model.getDescription(),
                        model.isPublished(),
                        model.getFlagNote(),
                        tags,
                        genres,
                        model.getImagePath(),
                        model.getTotalPlayedCount(),
                        model.getRate(),
                        model.getDuration(),
                        model.getCreatedAt(),
                        model.getLastModifiedAt(),
                        model.getCreator().getId(),
                        ((TrackModel) model).getRecordLabel(),
                        ((TrackModel) model).getProducer(),
                        ((TrackModel) model).getReleaseDate(),
                        model.getUpdater().getId(),
                        ((TrackModel) model).getAudioPath(),
                        ((TrackModel) model).getFormat(),
                        ((TrackModel) model).getSize(),
                        ((TrackModel) model).getLyrics(),
                        artist,
                        album
                ).getValue();

            }
            
        }
        
        return entity;
    }
    
    @Override
    public LibraryEntityModel toPersistence(LibraryEntity entity, EntityManager em) {
     
        LibraryEntityModel model;
        if(entity instanceof Band)
            model = new BandModel();
        else if(entity instanceof Singer)
            model = new SingerModel();
        else if(entity instanceof Album)
            model = new AlbumModel();
        else
            model = new TrackModel();
        
        Set<GenreModel> genres = null;
        if(entity.getGenres() != null) {
            genres = entity.getGenres()
                    .getValue()
                    .stream()
                    .map(genre -> GenreMapper.toPersistence(genre))
                    .collect(Collectors.toSet());
        }
        
        Set<String> tags = null;
        if(entity.getTags() != null) {
            tags = entity.getTags()
                    .getValue()
                    .stream()
                    .map(tag -> tag.getValue())
                    .collect(Collectors.toSet());
        }
            
        model.setId(entity.getId().toValue());
        model.setTitle(entity.getTitle().getValue());
        model.setDescription(
                entity.getDescription() != null ?
                entity.getDescription().getValue()
                : null
        );
        model.setPublished(entity.isPublished());
        model.setTotalPlayedCount(entity.getTotalPlayedCount());
        model.setRate(entity.getRate().getValue());
        model.setCreatedAt(entity.getCreatedAt().getValue());
        model.setLastModifiedAt(entity.getLastModifiedAt().getValue());
        model.setDuration(entity.getDuration().toSeconds());
        model.setFlagNote(entity.getFlag() != null ? entity.getFlag().getValue() : null);
        model.setImagePath(entity.hasImage() ? entity.getImagePath().toString() : null);
        model.setCreator(em.getReference(UserModel.class, entity.getCreatorId().toValue()));
        model.setUpdater(em.getReference(UserModel.class, entity.getUpdaterId().toValue()));
        if(tags != null && !tags.isEmpty())
            model.setTags(tags);
        if(genres != null && !genres.isEmpty())
            model.replaceGenres(genres);

        if(entity instanceof Artist) {
            ((ArtistModel) model).setInstagramId(
                    ((Artist) entity).getInstagramId() != null ?
                    ((Artist) entity).getInstagramId().getValue()
                            : null
            );
            ((Artist) entity).getAllTracksIds()
                    .forEach(trackId -> {
                        final TrackModel track = em.getReference(TrackModel.class, trackId.toValue());
                        ((ArtistModel) model).addTrack(track);
                        track.setArtist((ArtistModel) model);
                    });
            if(((Artist) entity).getAlbumsIds() != null) {
                ((Artist) entity).getAlbumsIds()
                    .forEach(albumId -> {
                        final AlbumModel album = em.getReference(AlbumModel.class, albumId.toValue());
                        ((ArtistModel) model).addAlbum(album);
                        album.setArtist((ArtistModel) model);
                    });
            }
        }
        
        if(entity instanceof Artwork) {
            ((ArtworkModel) model)
                    .setRecordLabel(
                            ((Artwork) entity).getRecordLabel() != null ?
                                    ((Artwork) entity).getRecordLabel().getValue()
                                    : null
                    );
            ((ArtworkModel) model)
                    .setProducer(
                            ((Artwork) entity).getProducer() != null ?
                                    ((Artwork) entity).getProducer().getValue()
                                    : null
                    );
            ((ArtworkModel) model).setReleaseDate(
                    ((Artwork) entity).getReleaseDate() != null ?
                            ((Artwork) entity).getReleaseDate().getValue()
                            : null
            );
            if(((Artwork) entity).getArtist() != null) {
                ((ArtworkModel) model).setArtist(
                        em.find(
                                ArtistModel.class,
                                ((Artwork) entity).getArtist().getId().toValue()
                        )
                );
            }
        }
        
//        if(entity instanceof Band) {
//            ((Band) entity).getMembers()
//                .forEach(member ->
//                        ((BandModel) model).addMember(
//                                (SingerModel) toPersistence(member, em)
//                        )
//                );
//        }
        
        if(entity instanceof Album) {
            ((Album) entity).getTracks()
                    .forEach(track -> {
                        ((AlbumModel) model).addTrack((TrackModel) toPersistence(track, em));
                    });
        }
        
        if(entity instanceof Track) {
            ((TrackModel) model).setAudioPath(((Track) entity).getAudioPath().toString());
            ((TrackModel) model).setFormat(((Track) entity).getType().getValue());
            ((TrackModel) model).setSize(((Track) entity).getSize());
            ((TrackModel) model).setLyrics(
                    ((Track) entity).getLyrics() != null ?
                    ((Track) entity).getLyrics().getValue() : null
            );
            if(((Track) entity).getAlbum() != null) {
                final AlbumModel album = em.getReference(AlbumModel.class, ((Track) entity).getAlbum().getId().toValue());
                ((TrackModel) model).setAlbum(album);
            }
        }
        
        return model;
    }
    
    public static LibraryEntityRepository getInstance() {
        return LibraryEntityRepositoryHolder.INSTANCE;
    }
    
    public static class LibraryEntityRepositoryHolder {
        
        final static EntityManagerFactoryHelper emfh =
                EntityManagerFactoryHelper.getInstance();
        
        private static final LibraryEntityRepository INSTANCE =
                new LibraryEntityRepository(emfh);
        
    }
    
    
    
}
