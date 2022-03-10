/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

import java.util.Set;
import com.jamapplicationserver.core.domain.UserRole;
import java.time.*;

/**
 *
 * @author dada
 */
public class TrackDetails extends ArtworkDetails {
    
    public String audioPath;
    public Long audioSize;
    public String format;
    public String lyrics;
    public LibraryEntityIdAndTitle album;
    
    public TrackDetails(
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
            long duration,
            String audioPath,
            long audioSize,
            String format,
            String lyrics,
            LibraryEntityIdAndTitle album,
            String recordLabel,
            String producer,
            YearMonth releaseDate,
            LibraryEntityIdAndTitle artist,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            String creatorId,
            String creatorName,
            String updaterId,
            String updaterName
    ) {
        super(
                id,
                "T",
                title,
                description,
                published,
                tags,
                genres,
                monthlyPlayedCount,
                rate,
                flagNote,
                totalPlayedCount,
                duration,
                recordLabel,
                producer,
                releaseDate,
                artist,
                createdAt,
                lastModifiedAt,
                creatorId,
                creatorName,
                updaterId,
                updaterName
        );
        this.audioPath = audioPath;
        this.audioSize = audioSize;
        this.format = format;
        this.lyrics = lyrics;
        this.album = album;
    }
    
    @Override
    public TrackDetails filter(UserRole role) {
        switch(role) {
            case ADMIN: break;
            case LIBRARY_MANAGER: break;
            case SUBSCRIBER:
                if(!this.published) return null;
                this.flagNote = null;
                this.tags = null;
                this.genres = null;
                this.audioSize = null;
                this.published = null;
                this.creatorId = null;
                this.creatorName = null;
                this.updaterId = null;
                this.updaterName = null;
                this.createdAt = null;
                this.lastModifiedAt = null;
                break;
            default:
                return null;
        }
        return this;
    }
    
    
}
