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
public class GenreTitleTest {
    
    @Test
    @DisplayName("Genre title length must be between ")
    public void testGenreTitleLengthLimits() {
        
        final String underTheLimit = String.valueOf("T").repeat(GenreTitle.MIN_LENGTH - 1);
        final String overTheLimit = String.valueOf("T").repeat(GenreTitle.MAX_LENGTH + 1);
        
        final Result resultWithUnderTheLimit = GenreTitle.create(underTheLimit);
        final Result resultWithOverTheLimit = GenreTitle.create(overTheLimit);
        
        assertTrue(resultWithUnderTheLimit.isFailure, "");
        assertEquals(resultWithUnderTheLimit.getError(), "");
        
        assertTrue(resultWithOverTheLimit.isFailure, "");
        assertEquals(resultWithOverTheLimit.getError(), "");
        
    }
    
}
