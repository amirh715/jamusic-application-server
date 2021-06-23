/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import java.util.Set;
import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;
import java.util.Collections;
import java.util.stream.Stream;

/**
 *
 * @author amirhossein
 */
public class TagList extends ValueObject {
    
    public static final int MAX_ALLOWED_TAGS = 5;
    
    private final Set<Tag> tags;
    
    @Override
    public Set<Tag> getValue() {
        return this.tags;
    }
    
    private TagList(Set tags) {
        this.tags = tags;
    }
    
    public static final Result<TagList> create(Set tags) {
        
        if(tags.size() > MAX_ALLOWED_TAGS)
            return Result.fail(new ValidationError("Tag lists can have " + MAX_ALLOWED_TAGS + " max."));
        
        return Result.ok(new TagList(tags));
        
    }
    
}
