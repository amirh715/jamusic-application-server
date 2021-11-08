/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.services;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.infra.Persistence.database.Models.LibraryEntityModel;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.GenreDetails;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.LibraryEntitySummary;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.LibraryEntityDetails;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.PlaylistDetails;
import com.jamapplicationserver.modules.library.repositories.LibraryEntityFilters;

/**
 *
 * @author dada
 */
public interface ILibraryQueryService {
    
    Set<LibraryEntityDetails> getLibraryEntitiesByFilters(LibraryEntityFilters filters);
    
//    Set<LibraryEntityDetaisl> getLibraryEntitiesByFilters(LibraryEntityFilters filters, AccessorRole role);
    
    Set<LibraryEntitySummary> getLibraryEntitiesSummaryByFilters(LibraryEntityFilters filters);
    
    LibraryEntityDetails getLibraryEntityById(UniqueEntityId id);
    
    LibraryEntityDetails getLibraryEntityById(UniqueEntityId id, Class<LibraryEntityModel> clazz);
    
    LibraryEntitySummary getLibraryEntitySummaryById(UniqueEntityId id);
    
    Set<GenreDetails> getAllGenres();
    
    GenreDetails getGenreById(UniqueEntityId id);
    
    Set<PlaylistDetails> getAllPlaylistsOfPlayer(UniqueEntityId playerId);
    
    PlaylistDetails getPlaylistById(UniqueEntityId playlistId, UniqueEntityId playerId);
    
    
}
