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
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author amirhossein
 */
public class BandTest {
    
    private Title title;
    private Description description;
    private Flag flag;
    private TagList tags;
    private GenreList genres;
    private InstagramId instagramId;
    private Set<Singer> members;
    
    @BeforeEach
    public void setup() {
        this.title = LibraryEntityGenerator.createTitle();
        this.description = LibraryEntityGenerator.createDescription();
        this.flag = LibraryEntityGenerator.createFlag();
        this.tags = LibraryEntityGenerator.createTagList();
        this.genres = LibraryEntityGenerator.createGenreList();
        this.members = LibraryEntityGenerator.createSingers(4);
    }
    
    @Test
    @DisplayName("Bands must have titles")
    public void testBandWithoutTitle() {
        
        final Result<Band> result =
                Band.create(null, description, flag, tags, genres, instagramId);
        
        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Title is required for bands.");
        
    }
    
    @Test
    @DisplayName("Bands without any tracks and albums must not be published")
    public void testBandsMustNotBePublishedWithoutTracksAndAlbums() {
        
        final Band band = (Band) LibraryEntityGenerator.createBands(1).toArray()[0];
        
        band.replaceAlbums(Collections.EMPTY_SET);
        band.replaceTracks(Collections.EMPTY_SET);
        
        band.publish();
        
        assertFalse(band.isPublished(), "");
        
    }
    
    @Test
    @DisplayName("Bands can have 2 to " + Band.MAX_ALLOWED_MEMBERS_PER_BAND + " members")
    public void testBandsCanHaveMembersWithinLimit() {
        
        final Band band = (Band) LibraryEntityGenerator.createBands(1).toArray()[0];
        members = LibraryEntityGenerator.createSingers(Band.MAX_ALLOWED_MEMBERS_PER_BAND + 1);
        
        final Result resultWithOverTheLimitMembers = band.replaceMembers(members);
        final Result resultWithUnderTheLimitMember = band.replaceMembers(LibraryEntityGenerator.createSingers(1));
        
        assertTrue(resultWithOverTheLimitMembers.isFailure, "");
        assertEquals(resultWithOverTheLimitMembers.getError(), "Bands can have " + Band.MAX_ALLOWED_MEMBERS_PER_BAND + " singers max.");
        assertTrue(resultWithUnderTheLimitMember.isFailure, "");
        assertEquals(resultWithUnderTheLimitMember.getError(), "Bands can have " + Band.MAX_ALLOWED_MEMBERS_PER_BAND + " singers max.");
        
    }
    
    @Test
    @DisplayName("Bands can have " + Band.MAX_ALLOWED_TRACKS_PER_ARTIST + " tracks max")
    public void testBandsWithOverTheLimitTracks() {
        
        final int numberOfTracks = Band.MAX_ALLOWED_TRACKS_PER_ARTIST + 1;
        
        final Band band = (Band) LibraryEntityGenerator.createBands(1).toArray()[0];
        final Set<Track> tracks = LibraryEntityGenerator.createTracks(numberOfTracks);
        
        final Result result = band.replaceTracks(tracks);
        
        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Artists can have " + Band.MAX_ALLOWED_TRACKS_PER_ARTIST + " max.");
        
    }
    
    @Test
    @DisplayName("Bands can have " + Band.MAX_ALLOWED_ALBUMS_PER_ARTIST + " albums max")
    public void testBandWithOverTheLimitAlbums() {
        
        final int numberOfAlbums = Band.MAX_ALLOWED_ALBUMS_PER_ARTIST + 1;
        
        final Band band = (Band) LibraryEntityGenerator.createBands(1).toArray()[0];
        final Set<Album> albums = LibraryEntityGenerator.createAlbums(numberOfAlbums, 2);
        
        final Result result = band.replaceAlbums(albums);
        
        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Artists can have " + Band.MAX_ALLOWED_ALBUMS_PER_ARTIST + " max.");
        
    }
    
    @Test
    @DisplayName("Bands published state must be applied to all of its tracks and albums")
    public void testBandsPublishedStateMustBeAppliedToItsTracksAndAlbums() {
        
    }
    
    @Test
    @DisplayName("Bands can have no members")
    public void testBandsCanHaveNoMembers() {
        
        final Result<Band> result =
                Band.create(title, description, flag, tags, genres, instagramId);
        
        assertTrue(result.isSuccess, "");
        
    }

    
}
