/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

import java.util.*;
import com.jamapplicationserver.core.domain.UserRole;

/**
 *
 * @author dada
 */
public class BandDetails extends ArtistDetails {
    
    public Set<LibraryEntityIdAndTitle> members;
    
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
            long duration,
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
    
    @Override
    public BandDetails filter(UserRole role) {
        switch(role) {
            case ADMIN: break;
            case LIBRARY_MANAGER: break;
            case SUBSCRIBER:
                this.flagNote = null;
                this.tags = null;
                break;
            default:
                return null;
        }
        return this;
    }
    
}
