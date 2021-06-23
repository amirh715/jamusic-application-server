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

import java.nio.file.Path;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.core.logic.Result;
import com.jamapplicationserver.modules.library.domain.core.*;
import java.time.Duration;

/**
 *
 * @author amirhossein
 */
public class TrackTest {
    
    private Title title;
    private Description description;
    private Flag flag;
    private AudioFormat format;
    private Path audioPath;
    private TagList tagList;
    private GenreList genreList;
    private Lyrics lyrics;
    
    @BeforeEach
    public void setup() {
        
        this.title = Title.create("Track Title").getValue();
        this.description = Description.create("Track description.").getValue();
        this.flag = Flag.create("Flag note.").getValue();
        this.format = AudioFormat.create().getValue();
        this.audioPath = Path.of("");
        this.tagList = LibraryEntityGenerator.createTagList();
        this.genreList = LibraryEntityGenerator.createGenreList();
        this.lyrics = LibraryEntityGenerator.createLyrics();
        
    }
    
    @Test
    @DisplayName("Track must have title")
    public void testTrackMustHaveTitle() {
        
        final Result<Track> result =
                Track.create(null, description, flag, 0, Duration.ZERO, format, audioPath, tagList, genreList, lyrics);
        
        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Title is required for tracks.");
        
    }
    
    @Test
    @DisplayName("Track must have audioPath")
    public void testTrackMustHaveAudioPath() {
        
        final Result<Track> result =
                Track.create(title, description, flag, 0, Duration.ofSeconds(20), format, null, tagList, genreList, lyrics);
        
        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Track must have an audioPath.");
        
    }
    
    @Test
    @DisplayName("Track must have (valid) duration")
    public void testTrackMustHaveValidDuration() {
        
        final Result<Track> result =
                Track.create(
                        title,
                        description,
                        flag,
                        0,
                        Track.MAX_ALLOWED_DURATION.plusSeconds(1),
                        format,
                        audioPath,
                        tagList,
                        genreList,
                        lyrics
                );

        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Max allowed audio duration is superceeded.");
        
    }
    
    @Test
    @DisplayName("Track must have (valid) audio size")
    public void testTrackMustHaveValidAudioSize() {
        
        final Result<Track> result =
                Track.create(
                        title,
                        description,
                        flag,
                        Track.MAX_ALLOWED_AUDIO_SIZE + 1,
                        Duration.ZERO,
                        format,
                        audioPath,
                        tagList,
                        genreList,
                        lyrics
                );
        
        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Max audio size is " + Track.MAX_ALLOWED_AUDIO_SIZE);
        
    }
    
    @Test
    @DisplayName("Track must have (valid) audio format")
    public void testTrackMustHaveValidAudioFormat() {
        
        final Result<Track> result =
                Track.create(
                        title,
                        description,
                        flag,
                        0,
                        Duration.ZERO,
                        null,
                        audioPath,
                        tagList,
                        genreList,
                        lyrics
                );
        
        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Audio format is required.");
        
    }

}
