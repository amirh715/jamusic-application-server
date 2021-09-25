/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

import java.util.*;
import java.time.Duration;
import com.jamapplicationserver.core.domain.IDTO;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import java.util.stream.Collectors;

/**
 *
 * @author dada
 */
public abstract class LibraryEntityDetails implements IDTO {
    
    public final String id;
    public final String title;
    public final String description;
    public final boolean published;
    public final Set<String> tags;
    public final Set<GenreIdAndTitle> genres;
    public final String monthlyPlayedCount;
    public final String rate;
    public final String flagNote;
    public final String totalPlayedCount;
    public final String duration;
    
    public LibraryEntityDetails(
            String id,
            String title,
            String description,
            boolean published,
            Set<String> tags,
            Set<GenreIdAndTitle> genres,
            long monthlyPlayedCount,
            double rate,
            String flagNote,
            long totalPlayedCount,
            long duration
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.published = published;
        this.tags = tags;
        this.genres = genres;
        this.monthlyPlayedCount = Long.toString(totalPlayedCount);
        this.rate = Double.toString(rate);
        this.flagNote = flagNote;
        this.totalPlayedCount = Long.toString(totalPlayedCount);
        this.duration = 0;
    }
    
    public static LibraryEntityDetails create(LibraryEntityModel entity) {
        
        LibraryEntityDetails dto = null;
        
        Set<GenreIdAndTitle> genres = null;
        if(entity.getGenres() != null && !entity.getGenres().isEmpty()) {
            entity.getGenres()
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
                                .collect(Collectors.toSet())
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
                            ((SingerModel) entity).getInstagramId()
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
                            ((AlbumModel) entity).getTracks()
                                .stream()
                                .map(track -> LibraryEntitySummary.create(track))
                                .collect(Collectors.toSet())
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
                            ((TrackModel) entity).getArtist() != null ?
                            LibraryEntityIdAndTitle.create(
                                    ((TrackModel) entity).getArtist()
                            ) : null
                    );
        }
        
        return dto;
    }
    
}
