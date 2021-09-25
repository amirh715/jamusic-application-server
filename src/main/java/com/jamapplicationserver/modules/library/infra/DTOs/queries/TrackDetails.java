/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

import java.util.Set;

/**
 *
 * @author dada
 */
public class TrackDetails extends LibraryEntityDetails {
    
    public final String audioPath;
    public final long audioSize;
    public final String format;
    public final String lyrics;
    public final LibraryEntityIdAndTitle album;
    public final LibraryEntityIdAndTitle artist;
    
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
                duration
        );
        this.audioPath = audioPath;
        this.audioSize = audioSize;
        this.format = format;
        this.lyrics = lyrics;
        this.album = album;
        this.artist = artist;
    }
    
    
}
