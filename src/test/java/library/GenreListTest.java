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
public class GenreListTest {
    
    @Test
    @DisplayName("Genre list can have " + GenreList.MAX_ALLOWED_GENRES + " genres max")
    public void testGenreListLimits() {
        
//        final Result result =
//                GenreList.create(null);
//        
//        assertTrue(result.isFailure, "");
//        assertEquals(result.getError(), "");
        
    }
    
}
