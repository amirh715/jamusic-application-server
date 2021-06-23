/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.util.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jamapplicationserver.core.logic.Result;

/**
 *
 * @author amirhossein
 */
public class TagTest {
    
    @Test
    @DisplayName("Test tag length limits")
    public void testTagLengthLimit() {
        
        final String underTheLimit = String.valueOf("T").repeat(Tag.MIN_LENGTH - 1);
        final String overTheLimit = String.valueOf("T").repeat(Tag.MAX_LENGTH + 1);
        
        final Result resultWithUnderTheLimit =
                Tag.create(underTheLimit);
        
        final Result resultWithOverTheLimit =
                Tag.create(overTheLimit);
        
        assertTrue(resultWithUnderTheLimit.isFailure, "");
        assertEquals(resultWithUnderTheLimit.getError(), "Tag length must be between " + Tag.MIN_LENGTH + " to " + Tag.MAX_LENGTH + " characters long.");
        
        assertTrue(resultWithOverTheLimit.isFailure, "");
        assertEquals(resultWithOverTheLimit.getError(), "Tag length must be between " + Tag.MIN_LENGTH + " to " + Tag.MAX_LENGTH + " characters long.");
        
    }
    
    @Test
    @DisplayName("Tags must not contain whitespaces")
    public void testTagMustNotHaveWhitespaces() {
        
        final Result result = Tag.create("Tag with whitespace");
        
        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Tag value should not contain space character.");
        
    }
    
}
