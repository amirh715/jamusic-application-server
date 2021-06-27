/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import java.util.UUID;
import java.time.LocalDateTime;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class Genre extends Entity {
    
    public static final int MAX_ALLOWED_PARENT_GENRE_SIZE = 3;

    private GenreTitle title;
    
    private GenreTitle titleInPersian;
    
    private final Genre parentGenre;
    
    private final DateTime createdAt;
    
    private final DateTime lastModifiedAt;
    
    // creation constructor
    private Genre(
            GenreTitle title,
            GenreTitle titleInPersian,
            Genre parentGenre,
            DateTime createdAt,
            DateTime lastModifiedAt
    ) {
        super();
        this.title = title;
        this.titleInPersian = titleInPersian;
        this.parentGenre = parentGenre;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }
    
    // reconstitution constructor
    private Genre(
            UniqueEntityID id,
            GenreTitle title,
            GenreTitle titleInPersian,
            Genre parentGenre,
            DateTime createdAt,
            DateTime lastModifiedAt
    ) {
        super(id);
        this.title = title;
        this.titleInPersian = titleInPersian;
        this.parentGenre = parentGenre;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }
    
    public void edit(GenreTitle title, GenreTitle titleInPersian) {
        this.title = title;
        this.titleInPersian = titleInPersian;
    }
    
    public static Result<Genre> create(GenreTitle title, GenreTitle titleInPersian, Genre parentGenre) {
        
        if(
                parentGenre != null &&
                parentGenre.getSize() > MAX_ALLOWED_PARENT_GENRE_SIZE
        )
            return Result.fail(new ValidationError("Genre can have " + MAX_ALLOWED_PARENT_GENRE_SIZE + " max."));
        
        return Result.ok(
                new Genre(
                        title,
                        titleInPersian,
                        parentGenre,
                        DateTime.createNow(),
                        DateTime.createNow()
                )
        );
    }
    
    public static Result<Genre> reconstitute(
            UUID id,
            String title,
            String titleInPersian,
            Genre parent,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt
    ) {
        
        final Result<UniqueEntityID> idOrError = UniqueEntityID.createFromUUID(id);
        final Result<GenreTitle> titleOrError = GenreTitle.create(title);
        final Result<GenreTitle> titleInPersianOrError = GenreTitle.create(titleInPersian);
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        final Result<DateTime> lastModifiedAtOrError = DateTime.create(lastModifiedAt);
        
        final Result[] combinedProps = {
            idOrError,
            titleOrError,
            titleInPersianOrError,
            createdAtOrError,
            lastModifiedAtOrError
        };
        
        final Result combinedPropsResult = Result.combine(combinedProps);
        
        if(combinedPropsResult.isFailure)
            return Result.fail(combinedPropsResult.getError());
        
        final Genre instance =
                new Genre(
                        idOrError.getValue(),
                        titleOrError.getValue(),
                        titleInPersianOrError.getValue(),
                        parent,
                        createdAtOrError.getValue(),
                        lastModifiedAtOrError.getValue()
                );
        
        
        return Result.ok(instance);
    }
    
    public final int getSize() {
        int size = 0;
        if(this.parentGenre == null)
            size = 1;
        else
            size = this.parentGenre.getSize() + 1;
        return size;
    }
    
    public final GenreTitle getTitle() {
        return this.title;
    }
    
    public final GenreTitle getTitleInPersian() {
        return this.titleInPersian;
    }
    
    public final Genre getParent() {
        return this.parentGenre;
    }
    
    public final boolean isRoot() {
        return parentGenre == null;
    }
    
    public final DateTime getCreatedAt() {
        return this.createdAt;
    }
    
    public final DateTime getLastModifiedAt() {
        return this.lastModifiedAt;
    }

    
}
