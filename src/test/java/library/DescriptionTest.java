/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import com.jamapplicationserver.modules.library.domain.core.Description;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jamapplicationserver.core.logic.Result;

/**
 *
 * @author amirhossein
 */
public class DescriptionTest {
    
    @Test
    @DisplayName("Description must be between to ")
    public void testDescriptionLengthLimits() {
        
        final String underTheLimit =
                String
                .valueOf("D")
                .repeat(Description.MIN_LENGTH - 1);
        
        final String overTheLimit =
                String
                .valueOf("D")
                .repeat(Description.MAX_LENGTH + 1);
        
        final Result resultWithUnderTheLimit =
                Description.create(underTheLimit);
        
        final Result resultWithOverTheLimit =
                Description.create(overTheLimit);
        
        assertTrue(resultWithUnderTheLimit.isFailure, "");
        assertEquals(resultWithUnderTheLimit.getError(), "Description must be " + Description.MIN_LENGTH + " to " + Description.MAX_LENGTH + " characters long.");
        
        assertTrue(resultWithOverTheLimit.isFailure, "");
        assertEquals(resultWithOverTheLimit.getError(), "Description must be " + Description.MIN_LENGTH + " to " + Description.MAX_LENGTH + " characters long.");
        
    }
    
}
