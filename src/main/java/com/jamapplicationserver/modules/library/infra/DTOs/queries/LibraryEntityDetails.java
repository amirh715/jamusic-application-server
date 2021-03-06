/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

import java.util.*;
import java.util.stream.Collectors;
import java.time.*;
import com.jamapplicationserver.infra.Persistence.database.Models.BandModel;
import com.jamapplicationserver.infra.Persistence.database.Models.AlbumModel;
import com.jamapplicationserver.infra.Persistence.database.Models.SingerModel;
import com.jamapplicationserver.infra.Persistence.database.Models.LibraryEntityModel;
import com.jamapplicationserver.infra.Persistence.database.Models.TrackModel;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;

/**
 *
 * @author dada
 */
public abstract class LibraryEntityDetails implements IQueryResponseDTO {
    
    public String id;
    public String type;
    public String title;
    public String description;
    public Boolean published;
    public Set<String> tags;
    public Set<GenreIdAndTitle> genres;
    public String monthlyPlayedCount;
    public String rate;
    public String flagNote;
    public String totalPlayedCount;
    public String duration;
    public LocalDateTime createdAt;
    public LocalDateTime lastModifiedAt;
    public String creatorId;
    public String creatorName;
    public String updaterId;
    public String updaterName;
    
    public LibraryEntityDetails(
            String id,
            String type,
            String title,
            String description,
            boolean published,
            Set<String> tags,
            Set<GenreIdAndTitle> genres,
            long monthlyPlayedCount,
            double rate,
            String flagNote,
            long totalPlayedCount,
            long duration,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            String creatorId,
            String creatorName,
            String updaterId,
            String updaterName
    ) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.published = published;
        this.tags = tags;
        this.genres = genres;
        this.monthlyPlayedCount = Long.toString(totalPlayedCount);
        this.rate = Double.toString(rate);
        this.flagNote = flagNote;
        this.totalPlayedCount = Long.toString(totalPlayedCount);
        this.duration = Long.toString(duration);
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.updaterId = updaterId;
        this.updaterName = updaterName;
    }
    
    /**
     * Factory method for creating concrete library entity instances
     * @param entity
     * @return LibraryEntityDetails
     */
    public static LibraryEntityDetails create(LibraryEntityModel entity) {
        
        LibraryEntityDetails dto = null;
        
        Set<GenreIdAndTitle> genres = null;
        if(entity.getGenres() != null && !entity.getGenres().isEmpty()) {
            genres = entity.getGenres()
                    .stream()
                    .map(genre -> GenreIdAndTitle.create(genre))
                    .collect(Collectors.toSet());
        }
        
        if(entity instanceof BandModel) {
            dto =
                    new BandDetails(
                            entity.getId().toString(),
                            entity.getTitle(),
                            entity.getDescription(),
                            entity.isPublished(),
                            entity.getTags(),
                            genres,
                            entity.getTotalPlayedCount(),
                            entity.getRate(),
                            entity.getFlagNote(),
                            entity.getTotalPlayedCount(),
                            entity.getDuration(),
                            ((BandModel) entity).getInstagramId(),
                            ((BandModel) entity).getMembers()
                                .stream()
                                .map(member -> LibraryEntityIdAndTitle.create(member))
                                .collect(Collectors.toSet()),
                            entity.getCreatedAt(),
                            entity.getLastModifiedAt(),
                            entity.getCreator().getId().toString(),
                            entity.getCreator().getName(),
                            entity.getUpdater().getId().toString(),
                            entity.getUpdater().getName()
                    );
        }
        
        if(entity instanceof SingerModel) {
            dto =
                    new SingerDetails(
                            entity.getId().toString(),
                            entity.getTitle(),
                            entity.getDescription(),
                            entity.isPublished(),
                            entity.getTags(),
                            genres,
                            entity.getTotalPlayedCount(),
                            entity.getRate(),
                            entity.getFlagNote(),
                            entity.getTotalPlayedCount(),
                            entity.getDuration(),
                            ((SingerModel) entity).getInstagramId(),
                            ((SingerModel) entity).getBands()
                                .stream()
                                .map(band -> LibraryEntityIdAndTitle.create(band))
                                .collect(Collectors.toSet()),
                            entity.getCreatedAt(),
                            entity.getLastModifiedAt(),
                            entity.getCreator().getId().toString(),
                            entity.getCreator().getName(),
                            entity.getUpdater().getId().toString(),
                            entity.getUpdater().getName()
                    );
        }
        
        if(entity instanceof AlbumModel) {
            dto =
                    new AlbumDetails(
                            entity.getId().toString(),
                            entity.getTitle(),
                            entity.getDescription(),
                            entity.isPublished(),
                            entity.getTags(),
                            genres,
                            entity.getTotalPlayedCount(),
                            entity.getRate(),
                            entity.getFlagNote(),
                            entity.getTotalPlayedCount(),
                            entity.getDuration(),
                            ((AlbumModel) entity).getRecordLabel(),
                            ((AlbumModel) entity).getProducer(),
                            ((AlbumModel) entity).getReleaseDate() != null ?
                            ((AlbumModel) entity).getReleaseDate() : null,
                            LibraryEntityIdAndTitle.create(((AlbumModel) entity).getArtist()),
                            ((AlbumModel) entity).getTracks()
                                .stream()
                                .map(track -> LibraryEntitySummary.create(track))
                                .collect(Collectors.toSet()),
                            entity.getCreatedAt(),
                            entity.getLastModifiedAt(),
                            entity.getCreator().getId().toString(),
                            entity.getCreator().getName(),
                            entity.getUpdater().getId().toString(),
                            entity.getUpdater().getName()
                    );
        }
        
        if(entity instanceof TrackModel) {
            dto =
                    new TrackDetails(
                            entity.getId().toString(),
                            entity.getTitle(),
                            entity.getDescription(),
                            entity.isPublished(),
                            entity.getTags(),
                            genres,
                            entity.getTotalPlayedCount(),
                            entity.getRate(),
                            entity.getFlagNote(),
                            entity.getTotalPlayedCount(),
                            entity.getDuration(),
                            ((TrackModel) entity).getAudioPath(),
                            ((TrackModel) entity).getSize(),
                            ((TrackModel) entity).getFormat(),
                            ((TrackModel) entity).getLyrics(),
                            ((TrackModel) entity).getAlbum() != null ?
                            LibraryEntityIdAndTitle.create(
                                    ((TrackModel) entity).getAlbum()
                            ) : null,
                            ((TrackModel) entity).getRecordLabel(),
                            ((TrackModel) entity).getProducer(),
                            ((TrackModel) entity).getReleaseDate() != null ?
                                    ((TrackModel) entity).getReleaseDate() : null,
                            ((TrackModel) entity).getArtist() != null ?
                            LibraryEntityIdAndTitle.create(
                                    ((TrackModel) entity).getArtist()
                            ) : null,
                            entity.getCreatedAt(),
                            entity.getLastModifiedAt(),
                            entity.getCreator().getId().toString(),
                            entity.getCreator().getName(),
                            entity.getUpdater().getId().toString(),
                            entity.getUpdater().getName()
                    );
        }
        
        return dto;
    }
    
}
