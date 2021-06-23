/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import com.jamapplicationserver.modules.library.domain.core.Title;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jamapplicationserver.core.logic.Result;

/**
 *
 * @author amirhossein
 */
public class TitleTest {
    
    @Test
    @DisplayName("Title must be between to ")
    public void testTitleLengthLimits() {

        
        final String underTheLimit =
                String
                .valueOf("T")
                .repeat(Title.MIN_LENGTH - 1);
        
        final String overTheLimit =
                String
                .valueOf("T")
                .repeat(Title.MAX_LENGTH + 1);
        
        final Result resultWithUnderTheLimit =
                Title.create(underTheLimit);
        
        final Result resultWithOverTheLimit =
                Title.create(overTheLimit);
        
        assertTrue(resultWithUnderTheLimit.isFailure, "");
        assertEquals(resultWithUnderTheLimit.getError(), "Title must be " + Title.MIN_LENGTH + " to " + Title.MAX_LENGTH + " characters long.");
        
        assertTrue(resultWithOverTheLimit.isFailure, "");
        assertEquals(resultWithOverTheLimit.getError(), "Title must be " + Title.MIN_LENGTH + " to " + Title.MAX_LENGTH + " characters long.");
        
    }
    
}
