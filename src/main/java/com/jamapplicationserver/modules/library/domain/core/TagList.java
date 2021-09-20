/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import java.util.*;
import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;
import java.util.stream.Collectors;

/**
 *
 * @author amirhossein
 */
public class TagList extends ValueObject<Set<Tag>> {
    
    public static final int MAX_ALLOWED_TAGS = 5;
    
    private final Set<Tag> tags;
    
    @Override
    public Set<Tag> getValue() {
        return this.tags;
    }
    
    private TagList(Set tags) {
        this.tags = tags;
    }
    
    public static final Result<TagList> create(Set<Tag> tags) {
        
        if(tags == null)
            return Result.fail(new ValidationError("Tags are required"));
        
        if(tags.size() > MAX_ALLOWED_TAGS)
            return Result.fail(new ValidationError("Tag lists can have " + MAX_ALLOWED_TAGS + " max."));
        
        return Result.ok(new TagList(tags));
        
    }
    
    public static final Result<TagList> createFromString(Set<String> tags) {
        
        if(tags == null)
            return Result.fail(new ValidationError("Tags are required"));
        
        if(tags.size() > MAX_ALLOWED_TAGS)
            return Result.fail(new ValidationError("Tag lists can have " + MAX_ALLOWED_TAGS + " max."));
        
        final Set<Tag> tagsSet = new HashSet<>();
        for(String tag : tags) {
            final Result<Tag> tagOrError = Tag.create(tag);
            if(tagOrError.isFailure) return Result.fail(tagOrError.getError());
            tagsSet.add(tagOrError.getValue());
        }
        
        return Result.ok(new TagList(tagsSet));
    }
    
}
