/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

import java.util.*;
import com.jamapplicationserver.core.domain.UserRole;
import java.time.LocalDateTime;

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
            Set<LibraryEntityIdAndTitle> members,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            String creatorId,
            String creatorName,
            String updaterId,
            String updaterName
    ) {
        super(
                id,
                "B",
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
                instagramId,
                createdAt,
                lastModifiedAt,
                creatorId,
                creatorName,
                updaterId,
                updaterName
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
                this.genres = null;
                this.published = null;
                this.creatorId = null;
                this.creatorName = null;
                this.updaterId = null;
                this.updaterName = null;
                break;
            default:
                return null;
        }
        return this;
    }
    
}
