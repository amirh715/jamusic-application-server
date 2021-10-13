/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

import java.util.*;

/**
 *
 * @author dada
 */
public class AlbumDetails extends ArtworkDetails {
    
    public final Set<LibraryEntitySummary> tracks;
    
    public AlbumDetails(
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
            String recordLabel,
            String producer,
            String releaseDate,
            LibraryEntityIdAndTitle artist,
            Set<LibraryEntitySummary> tracks
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
                producer,
                releaseDate,
                artist
        );
        this.tracks = tracks;
    }
    
}
