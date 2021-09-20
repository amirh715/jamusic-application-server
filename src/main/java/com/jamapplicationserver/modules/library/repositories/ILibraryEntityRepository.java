/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.domain.Band.Band;
import com.jamapplicationserver.modules.library.domain.Singer.Singer;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.infra.Persistence.database.Models.*;

/**
 *
 * @author amirhossein
 */
public interface ILibraryEntityRepository {
    
    void save(LibraryEntity entity) throws Exception;
    
    boolean exists(UniqueEntityId id);
    
    LibraryEntityQueryScope<LibraryEntity> fetchById(UniqueEntityId id);
    
    LibraryEntityQueryScope<Artist> fetchArtistById(UniqueEntityId id);
    
    LibraryEntityQueryScope<Artwork> fetchArtworkById(UniqueEntityId id);
    
    LibraryEntityQueryScope<Band> fetchBandById(UniqueEntityId id);
    
    LibraryEntityQueryScope<Singer> fetchSingerById(UniqueEntityId id);
    
    LibraryEntityQueryScope<Album> fetchAlbumById(UniqueEntityId id);
    
    LibraryEntityQueryScope<Track> fetchTrackById(UniqueEntityId id);
    
    void remove(LibraryEntity entity);
    
    LibraryEntityQueryScope<LibraryEntity> fetchByFilters(LibraryEntityFilters filters);
    
    LibraryEntity toDomain(LibraryEntityModel model);
    
    LibraryEntityModel toPersistence(LibraryEntity entity);
    
}
