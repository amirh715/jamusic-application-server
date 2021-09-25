/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.modules.library.domain.Band.*;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;
import javax.persistence.criteria.*;
import com.jamapplicationserver.core.infra.QueryScope;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.repositories.mappers.*;
import com.jamapplicationserver.modules.library.domain.Band.Band;
import com.jamapplicationserver.modules.library.domain.Singer.Singer;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class LibraryEntityRepository implements ILibraryEntityRepository {
    
    private final EntityManagerFactory emf;
    
    private LibraryEntityRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    @Override
    public LibraryEntityQueryScope<LibraryEntity> fetchById(UniqueEntityId id) {
        
        final EntityManager em = emf.createEntityManager();
        
        try {
            
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<LibraryEntityModel> cq = cb.createQuery(LibraryEntityModel.class);
            final Root root = cq.from(LibraryEntityModel.class);
            
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("id"), id.toValue()));
            
            return new LibraryEntityQueryScope(
                    em,
                    cb,
                    cq,
                    root,
                    LibraryEntityModel.class,
                    predicates
            );

        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Artist> fetchArtistById(UniqueEntityId id) {
        
        final EntityManager em = emf.createEntityManager();
        
        try {
            
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<ArtistModel> cq = cb.createQuery(ArtistModel.class);
            final Root<ArtistModel> root = cq.from(ArtistModel.class);
            
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("id"), id.toValue()));
            
            return new LibraryEntityQueryScope(
                    em,
                    cb,
                    cq,
                    root,
                    ArtistModel.class,
                    predicates
            );
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Artwork> fetchArtworkById(UniqueEntityId id) {
        
        final EntityManager em = emf.createEntityManager();
        
        try {
            
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<ArtworkModel> cq = cb.createQuery(ArtworkModel.class);
            final Root<ArtworkModel> root = cq.from(ArtworkModel.class);
            
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("id"), id.toValue()));
            
            return new LibraryEntityQueryScope(
                    em,
                    cb,
                    cq,
                    root,
                    ArtworkModel.class,
                    predicates
            );
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Band> fetchBandById(UniqueEntityId id) {
        
        final EntityManager em = emf.createEntityManager();
        
        try {
            
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<BandModel> cq = cb.createQuery(BandModel.class);
            final Root<BandModel> root = cb.createQuery().from(BandModel.class);
            
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("id"), id.toValue()));

            return new LibraryEntityQueryScope(
                    em,
                    cb,
                    cq,
                    root,
                    BandModel.class,
                    predicates
            );
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Singer> fetchSingerById(UniqueEntityId id) {
        
        final EntityManager em = emf.createEntityManager();
        
        try {
            
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<SingerModel> cq = cb.createQuery(SingerModel.class);
            final Root<SingerModel> root = cq.from(SingerModel.class);
            
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("id"), id.toValue()));
            
            return new LibraryEntityQueryScope(
                    em,
                    cb,
                    cq,
                    root,
                    SingerModel.class,
                    predicates
            );
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Album> fetchAlbumById(UniqueEntityId id) {
        
        final EntityManager em = emf.createEntityManager();
        
        try {
            
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<AlbumModel> cq = cb.createQuery(AlbumModel.class);
            final Root<AlbumModel> root = cq.from(AlbumModel.class);
            
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("id"), id.toValue()));
            
            return new LibraryEntityQueryScope(
                    em,
                    cb,
                    cq,
                    root,
                    AlbumModel.class,
                    predicates
            );
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<Track> fetchTrackById(UniqueEntityId id) {
        
        final EntityManager em = emf.createEntityManager();
        
        try {
            
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<TrackModel> cq = cb.createQuery(TrackModel.class);
            final Root<TrackModel> root = cq.from(TrackModel.class);
            
            final List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("id"), id.toValue()));
            
            return new LibraryEntityQueryScope(
                    em,
                    cb,
                    cq,
                    root,
                    TrackModel.class,
                    predicates
            );
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public LibraryEntityQueryScope<LibraryEntity> fetchByFilters(LibraryEntityFilters filters) {
        
        final EntityManager em = emf.createEntityManager();
        
        try {
            
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<LibraryEntityModel> cq = cb.createQuery(LibraryEntityModel.class);
            final Root<LibraryEntityModel> root = cq.from(LibraryEntityModel.class);
            
            ArrayList<Predicate> predicates =
                    buildCriteriaPredicates(
                            cb,
                            cq,
                            root,
                            filters
                    );
            
            return new LibraryEntityQueryScope(
                    em,
                    cb,
                    cq,
                    root,
                    LibraryEntityModel.class,
                    predicates
            );
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public void save(LibraryEntity entity) throws EntityNotFoundException, Exception {
        
        final EntityManager em = emf.createEntityManager();
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
            e.printStackTrace();
            tnx.rollback();
            throw e;
        } catch(RollbackException e) {
            e.printStackTrace();
            tnx.rollback();
            throw e;
        } catch(Exception e) {
            e.printStackTrace();
            tnx.rollback();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public void remove(LibraryEntity entity) {
        
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            tnx.begin();
            
            final LibraryEntityModel model =
                    em.find(LibraryEntityModel.class, entity.id.toValue());
            
            if(entity instanceof Band) {
                
                ((BandModel) model).getTracks()
                        .forEach(track -> em.remove(track));
                
                ((BandModel) model).getAlbums()
                        .forEach(album -> em.remove(album));
                
            } else if(entity instanceof Singer) {
                
                ((SingerModel) model).getTracks()
                        .forEach(track -> em.remove(track));
                
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
            e.printStackTrace();
        }
        
    }
    
    @Override
    public boolean exists(UniqueEntityId id) {
        
        final EntityManager em = emf.createEntityManager();
        
        try {
            
            final LibraryEntityModel model = em.find(LibraryEntityModel.class, id.toValue());
            
            return model != null;
            
        } catch(Exception e) {
            throw e;
        }

    }
    
    private static String toSearchPattern(String term) {
        return "%" + term + "%";
    }
    
    private Set<TrackModel> getSingleTracks() {
        final EntityManager em = emf.createEntityManager();
        return
                em.createNamedQuery("Artist_FetchSingleTracks", TrackModel.class)
                .getResultList()
                .stream()
                .collect(Collectors.toSet());
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
//                                cb.like(root.get("lyrics"), toSearchPattern(filters.searchTerm))
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
    
//    @Override
    public static LibraryEntity toDomain(LibraryEntityModel model, EntityManager em) {
        
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
            final Set<UUID> tracksIds =
                    ((ArtistModel) model).getTracks()
                            .stream()
                            .map(track -> track.getId())
                            .collect(Collectors.toSet());
            
            if(model instanceof BandModel) { // Band
                
                final Set<Singer> members =
                        ((BandModel) model).getMembers()
                        .stream()
                        .map(member -> (Singer) toDomain(member, em))
                        .collect(Collectors.toSet());
                
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
                        tracksIds,
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
                        tracksIds
                ).getValue();
                
            }
            
        } else {
            
            Artist artist;
            if(model instanceof AlbumModel)
                artist = (Artist) toDomain(((AlbumModel) model).getArtist(), em);
            else
                artist = (Artist) toDomain(((TrackModel) model).getArtist(), em);
            
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
    
//    @Override
    public static LibraryEntityModel toPersistence(LibraryEntity entity, EntityManager em) {
        
//        final EntityManager em = emf.createEntityManager();
     
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
            genres.forEach(genre -> model.addGenre(genre));

        if(entity instanceof Artist) {
            ((ArtistModel) model).setInstagramId(
                    ((Artist) entity).getInstagramId() != null ?
                    ((Artist) entity).getInstagramId().getValue()
                            : null
            );
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
                    ((Artwork) entity).getReleaseYear() != null ?
                            ((Artwork) entity).getReleaseYear().getValue()
                            : null
            );
        }
        
        if(entity instanceof Band) {
            ((Band) entity).getMembers()
                .forEach(member ->
                        ((BandModel) model).addMember(
                                (SingerModel) toPersistence(member, em)
                        )
                );
            ((Band) entity).getAlbumsIds()
                    .forEach(albumId -> {
                        final AlbumModel album = em.getReference(AlbumModel.class, albumId.toValue());
                        ((BandModel) model).addAlbum(album);
                        album.setArtist((BandModel) model);
                    });
            ((Band) entity).getTracksIds()
                    .forEach(trackId -> {
                        final TrackModel track = em.getReference(TrackModel.class, trackId.toValue());
                        ((BandModel) model).addTrack(track);
                        track.setArtist((BandModel) model);
                    });
        }
        
        if(entity instanceof Singer) {
            if(((Singer) entity).getInstagramId() != null)
                ((SingerModel) model).setInstagramId(((Singer) entity).getInstagramId().getValue());
            ((Singer) entity).getAlbumsIds()
                    .forEach(albumId -> {
                        final AlbumModel album = em.getReference(AlbumModel.class, albumId.toValue());
                        ((SingerModel) model).addAlbum(album);
                        album.setArtist((SingerModel) model);
                    });
            ((Singer) entity).getTracksIds()
                    .forEach(trackId -> {
                        final TrackModel track = em.getReference(TrackModel.class, trackId.toValue());
                        ((SingerModel) model).addTrack(track);
                        track.setArtist((SingerModel) model);
                    });
        }
        
        if(entity instanceof Album) {
            
            ((Album) entity).getTracks()
                    .forEach(track -> {
                        ((AlbumModel) model).addTrack((TrackModel) toPersistence(track, em));
                    });
            ((AlbumModel) model).setArtist(
                    em.getReference(
                            ArtistModel.class,
                            ((Album) entity).getArtist().getId().toValue()
                    )
            );
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
                final Album albumEntity = ((Track) entity).getAlbum();
                final AlbumModel albumModel = new AlbumModel();
                albumModel.setId(albumEntity.getId().toValue());
                albumModel.setPublished(albumEntity.isPublished());
                albumModel.setImagePath(
                        albumEntity.hasImage() ?
                        albumEntity.getImagePath().toString() : null
                );
                if(albumEntity.getGenres() != null) {
                    albumEntity.getGenres()
                        .getValue()
                        .stream()
                        .map(genre -> GenreMapper.toPersistence(genre))
                        .forEach(genre -> albumModel.addGenre(genre));
                }
                ((TrackModel) model).setAlbum(albumModel);
            }
            ((TrackModel) model).setArtist(
                    em.getReference(
                            ArtistModel.class,
                            ((Track) entity).getArtist().getId().toValue()
                    )
            );
        }
        
        return model;
    }
    
    public static LibraryEntityRepository getInstance() {
        return LibraryEntityRepositoryHolder.INSTANCE;
    }
    
    public static class LibraryEntityRepositoryHolder {
        
        final static EntityManagerFactory emf =
                EntityManagerFactoryHelper.getInstance()
                .getFactory();
        
        private static final LibraryEntityRepository INSTANCE =
                new LibraryEntityRepository(emf);
        
    }
    
    
    
}
