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
public class BandDetails extends ArtistDetails {
    
    public final Set<LibraryEntityIdAndTitle> members;
    
    public BandDetails(
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
            String duration,
            String instagramId,
            Set<LibraryEntityIdAndTitle> members
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
                instagramId
        );
        this.members = members;
    }
    
}
