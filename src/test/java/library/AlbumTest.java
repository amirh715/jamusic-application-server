/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jamapplicationserver.modules.library.domain.Singer.Singer;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.core.logic.Result;
import com.jamapplicationserver.modules.library.domain.Band.Band;
import com.jamapplicationserver.modules.library.domain.core.*;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author amirhossein
 */
public class AlbumTest {
    
    private Title title;
    private Description description;
    private Flag flag;
    private TagList tagList;
    private GenreList genreList;
    private Set<Track> tracks;
    
    @BeforeEach
    public void setup() {
        this.title = Title.create("Track Title").getValue();
        this.description = Description.create("Track description.").getValue();
        this.flag = Flag.create("Flag note.").getValue();
        this.tagList = LibraryEntityGenerator.createTagList();
        this.genreList = LibraryEntityGenerator.createGenreList();
        this.tracks = LibraryEntityGenerator.createTracks(12);
    }
    
    @Test
    @DisplayName("Album must have title")
    public void testAlbumWithoutTitle() {
                
        final Result<Album> result = Album.create(null, description, flag, tagList, genreList, tracks);
        
        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Title is required for albums.");
    }
    
    @Test
    @DisplayName("Album must have " + "" + " tracks max")
    public void testAlbumWithOverTheLimitTracks() {
        
        final Set<Track> tracks =
                LibraryEntityGenerator.createTracks(Album.MAX_ALLOWED_TRACKS_PER_ALBUM + 1);
        
        final Result<Album> result =
                Album.create(title, description, flag, tagList, genreList, tracks);
        
        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Albums can have " + Album.MAX_ALLOWED_TRACKS_PER_ALBUM + " tracks max.");

    }
    
    @Test
    @DisplayName("Album must have at least one track")
    public void testAlbumMustHaveAtLeastOneTrack() {

        final Set<Track> tracks =
                LibraryEntityGenerator.createTracks(0);
        
        final Result<Album> result =
                Album.create(title, description, flag, tagList, genreList, tracks);
        
        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Albums must have at least one track.");        

    }
    

    
}
