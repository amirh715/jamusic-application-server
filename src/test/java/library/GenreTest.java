/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import com.jamapplicationserver.modules.library.domain.core.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jamapplicationserver.core.logic.Result;

/**
 *
 * @author amirhossein
 */
public class GenreTest {
    
    @Test
    @DisplayName("Genres can have " + Genre.MAX_ALLOWED_PARENT_GENRE_SIZE + " sub-genres max")
    public void testGenreParentGenreSizeLimit() {
        

        Result<Genre> genre = createGenre(null);
        for(int i=0; i<Genre.MAX_ALLOWED_PARENT_GENRE_SIZE + 1; i++)
            genre = createGenre(genre.getValue());
        
        assertTrue(genre.isFailure, "");
        assertEquals(genre.getError(), "Genre can have " + Genre.MAX_ALLOWED_PARENT_GENRE_SIZE + " max.");
        
    }
    
    private Result<Genre> createGenre(Genre parentGenre) {
        final GenreTitle title = GenreTitle.create("Genre Title").getValue();
        final GenreTitle titleInPersian = GenreTitle.create("Genre Title").getValue();
        return Genre.create(title, titleInPersian, parentGenre);
    }
    
    
}
