/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.http;

import spark.RouteGroup;
import static spark.Spark.*;
import com.jamapplicationserver.modules.library.usecases.CreateLibraryEntity.CreateLibraryEntityController;
import com.jamapplicationserver.modules.library.usecases.GetLibraryEntitiesByFilters.GetLibraryEntitiesByFiltersController;
import com.jamapplicationserver.modules.library.usecases.GetLibraryEntityById.GetLibraryEntityByIdController;
import com.jamapplicationserver.modules.library.usecases.EditLibraryEntity.EditLibraryEntityController;
import com.jamapplicationserver.modules.library.usecases.PublishOrArchiveLibraryEntity.PublishArchiveLibraryEntityController;
import com.jamapplicationserver.modules.library.usecases.PlayTrack.PlayTrackController;
import com.jamapplicationserver.modules.library.usecases.RemoveLibraryEntity.RemoveLibraryEntityController;
import com.jamapplicationserver.modules.library.usecases.GetLibraryEntityImageById.GetLibraryEntityImageByIdController;
import com.jamapplicationserver.modules.library.usecases.GetTrackAudioById.GetTrackAudioByIdController;
import com.jamapplicationserver.modules.library.usecases.CreateGenre.CreateGenreController;
import com.jamapplicationserver.modules.library.usecases.EditGenre.EditGenreController;
import com.jamapplicationserver.modules.library.usecases.GetAllGenres.GetAllGenresController;
import com.jamapplicationserver.modules.library.usecases.GetGenreByTitle.GetGenreByTitleController;
import com.jamapplicationserver.modules.library.usecases.RemoveGenre.RemoveGenreController;
import com.jamapplicationserver.modules.library.usecases.GetGenreById.GetGenreByIdController;

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
        
        // create library entity
        post(
                LibraryPaths.CREATE_NEW_ENTITY,
                CreateLibraryEntityController.getInstance()
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
        
        // edit library entity
        put(
                LibraryPaths.EDIT_ENTITY,
                EditLibraryEntityController.getInstance()
        );
        
        // publish/archive library entity
        put(
                LibraryPaths.PUBLISH_ARCHIVE_ENTITY,
                PublishArchiveLibraryEntityController.getInstance()
        );
        
        // play track
        put(
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
        
        // get genre by title
        get(
                LibraryPaths.GET_GENRE_BY_TITLE,
                GetGenreByTitleController.getInstance()
        );
        
        // get genre by id
        get(
                LibraryPaths.GET_GENRE_BY_ID,
                GetGenreByIdController.getInstance()
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
