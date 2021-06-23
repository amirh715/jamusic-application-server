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
public class TagListTest {
    
    @Test
    @DisplayName("TagList must have tags within the limit")
    public void testTagListTagCountLimit() {
        
        final Set<Tag> tags = new HashSet<>();
        
        for(int i=0; i<TagList.MAX_ALLOWED_TAGS + 1; i++)
            tags.add(Tag.create(String.valueOf("T").repeat(Tag.MAX_LENGTH)).getValue());
        
        final Result result = TagList.create(tags);
        
        assertTrue(result.isFailure, "");
        assertEquals(result.getError(), "Tag lists can have " + TagList.MAX_ALLOWED_TAGS + " max.");
        
    }
    
}
