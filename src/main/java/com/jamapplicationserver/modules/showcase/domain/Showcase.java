/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.domain;

import java.util.*;
import java.time.*;
import java.nio.file.Path;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class Showcase extends Entity {
    
    private static final int MAX_INDEX = 5;
    private static final int MIN_INDEX = 1;
    private static final int MIN_TITLE_LENGTH = 1;
    private static final int MAX_TITLE_LENGTH = 40;
    private static final int MIN_DESCRIPTION_LENGTH = 1;
    private static final int MAX_DESCRIPTION_LENGTH = 200;
    
    private final int index;
    private final String title;
    private final String description;
    private final String route;
    private long interactionCount;
    private final String imagePath;
    private final UniqueEntityId creatorId;
    
    // creation constructor
    private Showcase(
            int index,
            String title,
            String description,
            String route,
            String imagePath,
            UniqueEntityId creatorId
    ) {
        super();
        this.index = index;
        this.title = title;
        this.description = description;
        this.route = route;
        this.interactionCount = 0;
        this.imagePath = imagePath;
        this.creatorId = creatorId;
    }
    
    // reconstitution constructor
    private Showcase(
            UniqueEntityId id,
            int index,
            String title,
            String description,
            String route,
            String imagePath,
            long interactionCount,
            DateTime createdAt,
            DateTime lastModifiedAt,
            UniqueEntityId creatorId
    ) {
        super(id, createdAt, lastModifiedAt);
        this.index = index;
        this.title = title;
        this.description = description;
        this.route = route;
        this.interactionCount = interactionCount;
        this.imagePath = imagePath;
        this.creatorId = creatorId;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getImagePath() {
        return this.imagePath;
    }
    
    public String getRoute() {
        return this.route;
    }
    
    public long getInteractionCount() {
        return this.interactionCount;
    }
    
    public void interactedWith() {
        this.interactionCount++;
    }
    
    public UniqueEntityId getCreatorId() {
        return this.creatorId;
    }
    
    public static final Result<Showcase> create(
            int index,
            String title,
            String description,
            String route,
            Path imagePath,
            UniqueEntityId creatorId
    ) {
        
        if(index < MIN_INDEX || index > MAX_INDEX)
            return Result.fail(new ValidationError("index cannot be greater than 5"));
        
        if(
                title != null &&
                title.length() > MAX_TITLE_LENGTH &&
                title.length() < MIN_TITLE_LENGTH
        )
            return Result.fail(new ValidationError("Title length error"));
        
        if(
                description != null &&
                description.length() > MAX_DESCRIPTION_LENGTH &&
                description.length() < MIN_DESCRIPTION_LENGTH
        ) return Result.fail(new ValidationError("Description length error"));
        
        if(creatorId == null)
            return Result.fail(new ValidationError("Creator is reqiured"));
        
        return Result.ok(new Showcase(index, title, description, route, imagePath.toString(), creatorId));
    }
    
    public static final Result<Showcase> reconstitute(
            UUID id,
            int index,
            String title,
            String description,
            String route,
            String imagePath,
            long interactionCount,
            LocalDateTime createdAt,
            UUID creatorId
    ) {
        
        final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromUUID(id);
        if(idOrError.isFailure) return Result.fail(idOrError.getError());
        
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        if(createdAtOrError.isFailure) return Result.fail(createdAtOrError.getError());

        final Result<UniqueEntityId> creatorIdOrError = UniqueEntityId.createFromUUID(creatorId);
        if(creatorIdOrError.isFailure) return Result.fail(creatorIdOrError.getError());
        
        return Result.ok(
                new Showcase(
                        idOrError.getValue(),
                        index,
                        title,
                        description,
                        route,
                        imagePath,
                        interactionCount,
                        createdAtOrError.getValue(),
                        DateTime.createNow(),
                        creatorIdOrError.getValue()
                )
        );
    }
    
}
