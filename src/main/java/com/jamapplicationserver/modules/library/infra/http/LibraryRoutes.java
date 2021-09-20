/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.http;

import spark.RouteGroup;
import static spark.Spark.*;
import com.jamapplicationserver.modules.library.usecases.LibraryEntity.CreateArtist.CreateArtistController;
import com.jamapplicationserver.modules.library.usecases.LibraryEntity.CreateAlbum.CreateAlbumController;
import com.jamapplicationserver.modules.library.usecases.LibraryEntity.CreateTrack.CreateTrackController;
import com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetLibraryEntitiesByFilters.GetLibraryEntitiesByFiltersController;
import com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetLibraryEntityById.GetLibraryEntityByIdController;
import com.jamapplicationserver.modules.library.usecases.LibraryEntity.PublishOrArchiveLibraryEntity.PublishArchiveLibraryEntityController;
import com.jamapplicationserver.modules.library.usecases.LibraryEntity.PlayTrack.PlayTrackController;
import com.jamapplicationserver.modules.library.usecases.LibraryEntity.RemoveLibraryEntity.RemoveLibraryEntityController;
import com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetLibraryEntityImageById.GetLibraryEntityImageByIdController;
import com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetTrackAudioById.GetTrackAudioByIdController;
import com.jamapplicationserver.modules.library.usecases.Genres.CreateGenre.CreateGenreController;
import com.jamapplicationserver.modules.library.usecases.Genres.EditGenre.EditGenreController;
import com.jamapplicationserver.modules.library.usecases.Genres.GetAllGenres.GetAllGenresController;
import com.jamapplicationserver.modules.library.usecases.Genres.GetGenreByTitle.GetGenreByTitleController;
import com.jamapplicationserver.modules.library.usecases.Genres.RemoveGenre.RemoveGenreController;
import com.jamapplicationserver.modules.library.usecases.Genres.GetGenreById.GetGenreByIdController;
import com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetRecommendedCollections.GetRecommendedCollectionsController;

/**
 *
 * @author dada
 */
public class LibraryRoutes implements RouteGroup {

    private LibraryRoutes() {
    }
    
    @Override
    public void addRoutes() {
        
        before("/*", (req, res) -> System.out.println(req.url()));
        
        // create new artist
        post(
                LibraryPaths.CREATE_NEW_ARTIST,
                CreateArtistController.getInstance()
        );
        
        // create new album
        post(
                LibraryPaths.CREATE_NEW_ALBUM,
                CreateAlbumController.getInstance()
        );
        
        // create new track
        post(
                LibraryPaths.CREATE_NEW_TRACK,
                CreateTrackController.getInstance()
        );
        
        // get library entities by filters
        get(
                LibraryPaths.GET_ENTITIES_BY_FILTERS,
                GetLibraryEntitiesByFiltersController.getInstance()
        );
        
        // get library entity by id
        get(
                LibraryPaths.GET_ENTITY_BY_ID,
                GetLibraryEntityByIdController.getInstance()
        );
        
        // get collections
        get(
                LibraryPaths.GET_COLLECTIONS,
                GetRecommendedCollectionsController.getInstance()
        );
        
        // publish/archive library entity
        put(
                LibraryPaths.PUBLISH_ARCHIVE_ENTITY,
                PublishArchiveLibraryEntityController.getInstance()
        );
        
        // played track
        post(
                LibraryPaths.PLAYED_TRACK,
                PlayTrackController.getInstance()
        );
        
        // remove library entity
        delete(
                LibraryPaths.REMOVE_ENTITY,
                RemoveLibraryEntityController.getInstance()
        );
        
        // get library entity image
        get(
                LibraryPaths.GET_ENTITY_IMAGE_BY_ID,
                GetLibraryEntityImageByIdController.getInstance()
        );
        
        // get track audio
        get(
                LibraryPaths.GET_TRACK_AUDIO,
                GetTrackAudioByIdController.getInstance()
        );
        
        // create genre
        post(
                LibraryPaths.CREATE_NEW_GENRE,
                CreateGenreController.getInstance()
        );
        
        // edit genre
        put(
                LibraryPaths.EDIT_GENRE,
                EditGenreController.getInstance()
        );
        
        // get all genres
        get(
                LibraryPaths.GET_ALL_GENRES,
                GetAllGenresController.getInstance()
        );
                
        // get genre by id
        get(
                LibraryPaths.GET_GENRE_BY_ID,
                GetGenreByIdController.getInstance()
        );
        
        // get genre by title
        get(
                LibraryPaths.GET_GENRE_BY_TITLE,
                GetGenreByTitleController.getInstance()
        );
        
        // remove genre
        delete(
                LibraryPaths.REMOVE_GENRE,
                RemoveGenreController.getInstance()
        );
        
    }
    
    public static LibraryRoutes getInstance() {
        return LibraryRoutesHolder.INSTANCE;
    }
    
    private static class LibraryRoutesHolder {

        private static final LibraryRoutes INSTANCE = new LibraryRoutes();
    }
}
