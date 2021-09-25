/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import java.util.*;
import java.time.LocalDateTime;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class Genre extends Entity {
    
    public static final int MAX_ALLOWED_LEVEL = 3;

    private GenreTitle title;
    
    private GenreTitle titleInPersian;
    
    private final Genre parentGenre;
    
    private Set<Genre> subGenres = new HashSet<>();
    
    private final UniqueEntityId creatorId;
    
    private UniqueEntityId updaterId;
    
    // creation constructor
    private Genre(
            GenreTitle title,
            GenreTitle titleInPersian,
            Genre parentGenre,
            UniqueEntityId creatorId,
            UniqueEntityId updaterId
    ) {
        super();
        this.title = title;
        this.titleInPersian = titleInPersian;
        this.parentGenre = parentGenre;
        this.creatorId = creatorId;
        this.updaterId = updaterId;
    }
    
    // reconstitution constructor
    private Genre(
            UniqueEntityId id,
            GenreTitle title,
            GenreTitle titleInPersian,
            Genre parentGenre,
            Set<Genre> subGenres,
            DateTime createdAt,
            DateTime lastModifiedAt,
            UniqueEntityId creatorId,
            UniqueEntityId updaterId
    ) {
        super(id, createdAt, lastModifiedAt);
        this.title = title;
        this.titleInPersian = titleInPersian;
        this.parentGenre = parentGenre;
        this.subGenres = subGenres;
        this.creatorId = creatorId;
        this.updaterId = updaterId;
    }
    
    public void edit(GenreTitle title, GenreTitle titleInPersian, UniqueEntityId updaterId) {
        if(
                this.title.equals(title) &&
                this.titleInPersian.equals(titleInPersian)
        ) return;
        if(updaterId == null) return;
        
        this.title = title;
        this.titleInPersian = titleInPersian;
        this.updaterId = updaterId;
        
        modified();
    }

    public static Result<Genre> create(
            GenreTitle title,
            GenreTitle titleInPersian,
            Genre parentGenre,
            UniqueEntityId creatorId
    ) {
        
        if(
                parentGenre != null &&
                parentGenre.getLevel() >= MAX_ALLOWED_LEVEL
        ) return Result.fail(new ValidationError("Genre can have " + MAX_ALLOWED_LEVEL + " sub-genres max."));
        
        if(creatorId == null) return Result.fail(new ValidationError("Creator id is required"));
        
        return Result.ok(
                new Genre(
                        title,
                        titleInPersian,
                        parentGenre,
                        creatorId,
                        creatorId
                )
        );
    }
    
    public static Result<Genre> reconstitute(
            UUID id,
            String title,
            String titleInPersian,
            Genre parent,
            Set<Genre> subGenres,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            UUID creatorId,
            UUID updaterId
    ) {
        
        final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromUUID(id);
        final Result<GenreTitle> titleOrError = GenreTitle.create(title);
        final Result<GenreTitle> titleInPersianOrError = GenreTitle.create(titleInPersian);
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        final Result<DateTime> lastModifiedAtOrError = DateTime.create(lastModifiedAt);
        final Result<UniqueEntityId> creatorIdOrError = UniqueEntityId.createFromUUID(creatorId);
        final Result<UniqueEntityId> updaterIdOrError = UniqueEntityId.createFromUUID(updaterId);
        
        final Result[] combinedProps = {
            idOrError,
            titleOrError,
            titleInPersianOrError,
            createdAtOrError,
            lastModifiedAtOrError,
            creatorIdOrError,
            updaterIdOrError
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
                        subGenres,
                        createdAtOrError.getValue(),
                        lastModifiedAtOrError.getValue(),
                        creatorIdOrError.getValue(),
                        updaterIdOrError.getValue()
                );
        
        
        return Result.ok(instance);
    }
    
    public final int getLevel() {
        int level;
        if(this.parentGenre == null)
            level = 1;
        else
            level = this.parentGenre.getLevel() + 1;
        return level;
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
    
    public final Set<Genre> getSubGenres() {
        return this.subGenres;
    }
    
    public final UniqueEntityId getCreatorId() {
        return this.creatorId;
    }
    
    public final UniqueEntityId getUpdaterId() {
        return this.updaterId;
    }
    
    public final boolean isRoot() {
        return this.parentGenre == null;
    }
    
    public final boolean isSubGenreOf(Genre genre) {
        if(this.isRoot())
            return false;
        if(this.parentGenre.equals(genre))
            return true;
        return this.parentGenre.isSubGenreOf(genre);
    }
    
    public final boolean isParentGenreOf(Genre genre) {
        
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof Genre))
            return false;
        Genre g = (Genre) obj;
        return this.id.equals(g.id);
    }
    
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
    
}
