/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

/**
 *
 * @author dada
 */
public class PlaylistSummary {
    
    public final String id;
    public final int tracksCount;
    public final String createdAt;
    public final String lastModifiedAt;
    
    private PlaylistSummary(
            String id,
            int tracksCount,
            String createdAt,
            String lastModifiedAt
    ) {
        this.id = id;
        this.tracksCount = tracksCount;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }
    
}
