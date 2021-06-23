/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories.mappers;

import com.jamapplicationserver.infra.Persistence.database.Models.GenreModel;
import com.jamapplicationserver.infra.Persistence.database.Models.TrackModel;
import com.jamapplicationserver.infra.Persistence.database.Models.AlbumModel;
import com.jamapplicationserver.infra.Persistence.database.Models.SingerModel;
import com.jamapplicationserver.infra.Persistence.database.Models.LibraryEntityModel;
import com.jamapplicationserver.infra.Persistence.database.Models.BandModel;
import com.jamapplicationserver.core.infra.Mapper;
import com.jamapplicationserver.modules.library.domain.core.LibraryEntity;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.domain.Band.Band;
import com.jamapplicationserver.modules.library.domain.Singer.Singer;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.modules.library.domain.core.Artist;
import com.jamapplicationserver.modules.library.domain.core.Genre;
import com.jamapplicationserver.modules.library.domain.core.GenreTitle;
import com.jamapplicationserver.modules.library.domain.core.Tag;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author dada
 */
public class LibraryEntityMapper {

    public static final LibraryEntity toDomain(LibraryEntityModel model) {
        
        LibraryEntity entity;
        
        final Set<Genre> genres =
                model
                .getGenres()
                .stream()
                .map(genreModel -> GenreMapper.toDomain(genreModel))
                .collect(Collectors.toSet());
        
        final Set<Tag> tags =
                model
                .getTags()
                .stream()
                .map(tag -> Tag.create(tag).getValue())
                .collect(Collectors.toSet());
        
        if(model instanceof SingerModel) {
            
            final Set<UUID> tracksIds =
                    ((SingerModel) model).getTracks()
                    .stream()
                    .map(track -> track.getId())
                    .collect(Collectors.toSet());
            
            final Set<UUID> albumsIds =
                    ((SingerModel) model).getAlbums()
                    .stream()
                    .map(album -> album.getId())
                    .collect(Collectors.toSet());
            
            entity = Singer.reconstitute(
                    model.getId(),
                    model.getTitle(),
                    model.getDescription(),
                    model.isPublished(),
                    model.getFlagNote(),
                    tags,
                    genres,
                    ((SingerModel) model).getInstagramId(),
                    model.getImagePath(),
                    model.getCreatedAt(),
                    model.getLastModifiedAt(),
                    model.getTotalPlayedCount(),
                    model.getRate(),
                    model.getDuration(),
                    albumsIds,
                    tracksIds
            ).getValue();
            
        } else if(model instanceof BandModel) {
            
            final Set<UUID> albumsIds =
                    ((BandModel) model).getAlbums()
                    .stream()
                    .map(album -> album.getId())
                    .collect(Collectors.toSet());
            
            final Set<UUID> tracksIds =
                    ((BandModel) model).getTracks()
                    .stream()
                    .map(track -> track.getId())
                    .collect(Collectors.toSet());
            
            final Set<Singer> members =
                    ((BandModel) model).getMembers()
                    .stream()
                    .map(member -> (Singer) toDomain(member))
                    .collect(Collectors.toSet());
            
            entity = Band.reconstitute(
                    model.getId(),
                    model.getTitle(),
                    model.getDescription(),
                    model.isPublished(),
                    model.getFlagNote(),
                    tags,
                    genres,
                    ((BandModel) model).getInstagramId(),
                    model.getImagePath(),
                    model.getTotalPlayedCount(),
                    model.getRate(),
                    model.getDuration(),
                    model.getCreatedAt(),
                    model.getLastModifiedAt(),
                    albumsIds,
                    tracksIds,
                    members
            ).getValue();
            
        } else if(model instanceof TrackModel) {
            
            final Artist artist =
                    ((AlbumModel) model).getSinger() != null ?
                    (Artist) toDomain(((AlbumModel) model).getSinger()) :
                    (Artist) toDomain(((AlbumModel) model).getBand());
            
            entity = Track.reconstitute(
                    model.getId(),
                    model.getTitle(),
                    model.getDescription(),
                    model.isPublished(),
                    model.getFlagNote(),
                    tags,
                    genres,
                    model.getImagePath(),
                    model.getCreatedAt(),
                    model.getLastModifiedAt(),
                    model.getDuration(),
                    ((TrackModel) model).getSize(),
                    ((TrackModel) model).getFormat(),
                    ((TrackModel) model).getAudioPath(),
                    model.getTotalPlayedCount(),
                    model.getRate(),
                    ((TrackModel) model).getLyrics(),
                    artist
            ).getValue();
            
        } else if(model instanceof AlbumModel) {
            
            final Set<Track> tracks =
                    ((AlbumModel) model).getTracks()
                    .stream()
                    .map(track -> (Track) toDomain(track))
                    .collect(Collectors.toSet());
            
            final Artist artist =
                    ((AlbumModel) model).getSinger() != null ?
                    (Artist) toDomain(((AlbumModel) model).getSinger()) :
                    (Artist) toDomain(((AlbumModel) model).getBand());
                    
            entity = Album.reconstitute(
                    model.getId(),
                    model.getTitle(),
                    model.getDescription(),
                    model.isPublished(),
                    model.getFlagNote(),
                    tags,
                    genres,
                    model.getImagePath(),
                    model.getCreatedAt(),
                    model.getLastModifiedAt(),
                    model.getTotalPlayedCount(),
                    model.getRate(),
                    model.getDuration(),
                    tracks,
                    artist
            ).getValue();
            
        } else
            entity = null;
        
        return entity;
    }
    
    public static final LibraryEntityModel toPersistence(LibraryEntity entity) {
        
        LibraryEntityModel model;
        
        final Set<GenreModel> genres =
                entity
                .getGenres()
                .getValue()
                .stream()
                .map(genre -> GenreMapper.toPersistence(genre))
                .collect(Collectors.toSet());
        
        final Set<String> tags =
                entity
                .getTags()
                .getValue()
                .stream()
                .map(tag -> tag.getValue())
                .collect(Collectors.toSet());
        
        if(entity instanceof Singer) {
            
            model = new SingerModel();
            
            final Set<AlbumModel> albumsIds =
                    ((Band) entity).getAlbumsIds()
                    .stream()
                    .map(albumId -> {
                        final AlbumModel album = new AlbumModel();
                        model.setId(albumId.toValue());
                        return album;
                    })
                    .collect(Collectors.toSet());
            
            final Set<TrackModel> tracksIds =
                    ((Band) entity).getTracksIds()
                    .stream()
                    .map(trackId -> {
                        final TrackModel track = new TrackModel();
                        track.setId(trackId.toValue());
                        return track;
                    })
                    .collect(Collectors.toSet());
            
            model.setId(((Singer) entity).id.toValue());
            model.setTitle(entity.getTitle().getValue());
            model.setDescription(entity.getDescription().getValue());
            model.setPublished(entity.isPublished());
            model.setTags(tags);
            model.setGenres(genres);
            model.setFlagNote(entity.getFlag().getValue());
            model.setImagePath(entity.getImagePath().toAbsolutePath().toString());
            model.setDuration(entity.getDuration().toSeconds());
            model.setTotalPlayedCount(entity.getTotalPlayedCount());
            model.setCreatedAt(entity.getCreatedAt().getValue());
            model.setLastModifiedAt(entity.getLastModifiedAt().getValue());
            ((SingerModel) model).setAlbums(albumsIds);
            ((SingerModel) model).setTracks(tracksIds);
            
        } else if(entity instanceof Band) {
            
            model = new BandModel();
            
            final Set<SingerModel> members =
                    ((Band) entity).getMembers()
                    .stream()
                    .map(member -> (SingerModel) toPersistence(member))
                    .collect(Collectors.toSet());
            
            final Set<AlbumModel> albumsIds =
                    ((Band) entity).getAlbumsIds()
                    .stream()
                    .map(albumId -> {
                        final AlbumModel album = new AlbumModel();
                        model.setId(albumId.toValue());
                        return album;
                    })
                    .collect(Collectors.toSet());
            
            final Set<TrackModel> tracksIds =
                    ((Band) entity).getTracksIds()
                    .stream()
                    .map(trackId -> {
                        final TrackModel track = new TrackModel();
                        track.setId(trackId.toValue());
                        return track;
                    })
                    .collect(Collectors.toSet());
            
            model.setId(((Singer) entity).id.toValue());
            model.setTitle(entity.getTitle().getValue());
            model.setDescription(entity.getDescription().getValue());
            model.setPublished(entity.isPublished());
            model.setTags(tags);
            model.setGenres(genres);
            model.setFlagNote(entity.getFlag().getValue());
            model.setImagePath(entity.getImagePath().toAbsolutePath().toString());
            model.setDuration(entity.getDuration().toSeconds());
            model.setTotalPlayedCount(entity.getTotalPlayedCount());
            model.setCreatedAt(entity.getCreatedAt().getValue());
            model.setLastModifiedAt(entity.getLastModifiedAt().getValue());
            ((BandModel) model).setMembers(members);
            ((BandModel) model).setAlbums(albumsIds);
            ((BandModel) model).setTracks(tracksIds);
            
            
        } else if(entity instanceof Track) {
            
            model = new TrackModel();
            
            model.setId(((Singer) entity).id.toValue());
            model.setTitle(entity.getTitle().getValue());
            model.setDescription(entity.getDescription().getValue());
            model.setPublished(entity.isPublished());
            model.setTags(tags);
            model.setGenres(genres);
            model.setFlagNote(entity.getFlag().getValue());
            model.setImagePath(entity.getImagePath().toAbsolutePath().toString());
            model.setDuration(entity.getDuration().toSeconds());
            model.setTotalPlayedCount(entity.getTotalPlayedCount());
            model.setCreatedAt(entity.getCreatedAt().getValue());
            model.setLastModifiedAt(entity.getLastModifiedAt().getValue());

        } else if(entity instanceof Album) {
            
            model = new AlbumModel();
            
            final Set<TrackModel> tracks =
                    ((Album) entity).getTracks()
                    .stream()
                    .map(track -> (TrackModel) toPersistence(track))
                    .collect(Collectors.toSet());
            
            model.setId(((Singer) entity).id.toValue());
            model.setTitle(entity.getTitle().getValue());
            model.setDescription(entity.getDescription().getValue());
            model.setPublished(entity.isPublished());
            model.setTags(tags);
            model.setGenres(genres);
            model.setFlagNote(entity.getFlag().getValue());
            model.setImagePath(entity.getImagePath().toAbsolutePath().toString());
            model.setDuration(entity.getDuration().toSeconds());
            model.setTotalPlayedCount(entity.getTotalPlayedCount());
            model.setCreatedAt(entity.getCreatedAt().getValue());
            model.setLastModifiedAt(entity.getLastModifiedAt().getValue());
            ((AlbumModel) model).setTracks(tracks);

        } else
            model = null;
        
        return model;
    }
    
    public static final LibraryEntityModel mergeForPersistence(
            LibraryEntity entity,
            LibraryEntityModel model
    ) {
        
        model.setId(entity.id.toValue());
        model.setTitle(entity.getTitle().getValue());
        model.setDescription(entity.getDescription().getValue());
        model.setPublished(entity.isPublished());
        
        return model;
    }
    
}
