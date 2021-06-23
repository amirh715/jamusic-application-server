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

import java.util.Collections;
import com.jamapplicationserver.modules.library.domain.Singer.Singer;
import com.jamapplicationserver.core.logic.Result;
import com.jamapplicationserver.modules.library.domain.core.*;

/**
 *
 * @author amirhossein
 */
public class SingerTest {

    private Title title;
    private Description description;
    private Flag flag;
    private TagList tags;
    private GenreList genres;
    private InstagramId instagramId;
    
    @BeforeEach
    public void setup() {
        this.title = LibraryEntityGenerator.createTitle();
        this.description = LibraryEntityGenerator.createDescription();
        this.flag = LibraryEntityGenerator.createFlag();
        this.tags = LibraryEntityGenerator.createTagList();
        this.genres = LibraryEntityGenerator.createGenreList();
    }
    
    @Test
    @DisplayName("Singers must have titles")
    public void testSingerWithoutTitle() {
        
        final Result<Singer> result =
                Singer.create(null, description, flag, tags, genres, instagramId);
        
        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Title is required for singers.");
        
    }
    
    @Test
    @DisplayName("Singers without tracks and albums must not be published")
    public void testSingersMustNotBePublishedWithoutTracksAndAlbums() {
        
        final Singer singer = (Singer) LibraryEntityGenerator.createSingers(1).toArray()[0];
        
        singer.replaceAlbums(Collections.EMPTY_SET);
        singer.replaceTracks(Collections.EMPTY_SET);
        
        singer.publish();
        
        assertFalse(singer.isPublished(), "");
        
    }
    
    @Test
    @DisplayName("Singers can have " + Singer.MAX_ALLOWED_TRACKS_PER_ARTIST + " tracks max")
    public void testSingerWithOverTheLimitTracks() {
        
        final int numberOfTracks = Singer.MAX_ALLOWED_TRACKS_PER_ARTIST + 1;
        
        final Singer singer = (Singer) LibraryEntityGenerator.createSingers(1).toArray()[0];
                
        final Result result =
                singer.replaceTracks(LibraryEntityGenerator.createTracks(numberOfTracks));
        
        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Artists can have " + Singer.MAX_ALLOWED_TRACKS_PER_ARTIST + " max.");
        
    }
    
    @Test
    @DisplayName("Singers can have " + Singer.MAX_ALLOWED_ALBUMS_PER_ARTIST + " albums max")
    public void testSingerWithOverTheLimitAlbums() {
        
        final int numberOfAlbums = Singer.MAX_ALLOWED_ALBUMS_PER_ARTIST + 1;

        final Singer singer = (Singer) LibraryEntityGenerator.createSingers(1).toArray()[0];
        
        final Result result =
                singer.replaceAlbums(LibraryEntityGenerator.createAlbums(numberOfAlbums, 12));
        
        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Artists can have " + Singer.MAX_ALLOWED_ALBUMS_PER_ARTIST + " max.");
        
    }

    @Test
    @DisplayName("Singers published state must be applied to all of his/her tracks and albums")
    public void testSingersPublishedStateMustBeAppliedToHisHerTracksAndAlbums() {
        // implement
    }
    
}
