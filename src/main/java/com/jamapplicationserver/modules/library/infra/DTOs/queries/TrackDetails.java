/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

import java.util.Set;
import com.jamapplicationserver.core.domain.UserRole;

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
            String releaseDate,
            LibraryEntityIdAndTitle artist
    ) {
        super(
                id,
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
                releaseDate,
                producer,
                artist
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
                this.flagNote = null;
                this.tags = null;
                this.audioSize = null;
                break;
            default:
                return null;
        }
        return this;
    }
    
    
}
