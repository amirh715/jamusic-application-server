/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import java.util.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import com.jamapplicationserver.core.domain.UniqueEntityID;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.domain.Band.Band;
import com.jamapplicationserver.modules.library.domain.Singer.Singer;
import com.jamapplicationserver.modules.library.domain.Track.Track;

/**
 *
 * @author amirhossein
 */
public interface ILibraryEntityRepository {
    
    void save(LibraryEntity entity);
    
    boolean exists(UniqueEntityID id);
    
    LibraryEntityQueryScope<LibraryEntity> fetchById(UniqueEntityID id);
    
    LibraryEntityQueryScope<Artist> fetchArtistById(UniqueEntityID id);
    
    LibraryEntityQueryScope<Band> fetchBandById(UniqueEntityID id);
    
    LibraryEntityQueryScope<Singer> fetchSingerById(UniqueEntityID id);
    
    LibraryEntityQueryScope<Album> fetchAlbumById(UniqueEntityID id);
    
    LibraryEntityQueryScope<Track> fetchTrackById(UniqueEntityID id);
    
    void remove(LibraryEntity entity);
    
    LibraryEntityQueryScope<LibraryEntity> fetchByFilters(LibraryEntityFilters filters);

}
