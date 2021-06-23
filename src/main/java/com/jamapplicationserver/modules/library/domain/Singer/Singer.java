/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Singer;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import java.util.*;
import java.nio.file.Path;
import java.time.*;

/**
 *
 * @author amirhossein
 */
public class Singer extends Artist {
    
    // creation constructor
    private Singer(
            Title title,
            Description description,
            Flag flag,
            TagList tags,
            GenreList genres,
            InstagramId instagramId
    ) {
        super(
            title,
            description,
            flag,
            tags,
            genres,
            instagramId
        );
    }
    
    // reconstitution constructor
    private Singer(
            UniqueEntityID id,
            Title title,
            Description description,
            boolean published,
            Flag flag,
            TagList tags,
            GenreList genres,
            Path imagePath,
            InstagramId instagramId,
            Duration totalDuration,
            long totalPlayedCount,
            Rate rate,
            DateTime createdAt,
            DateTime lastModifiedAt,
            Set<UniqueEntityID> albumsIds,
            Set<UniqueEntityID> tracksIds
    ) {
        super(
                id,
                title,
                description,
                published,
                flag,
                tags,
                genres,
                imagePath,
                instagramId,
                totalDuration,
                totalPlayedCount,
                rate,
                createdAt,
                lastModifiedAt,
                albumsIds,
                tracksIds
        );

    }
    
    public static Result<Singer> create(
            Title title,
            Description description,
            Flag flag,
            TagList tags,
            GenreList genres,
            InstagramId instagramId
    ) {
        
        if(title == null) return Result.fail(new ValidationError("Title is required for singers."));
        
        Singer instance = new Singer(
                title,
                description,
                flag,
                tags,
                genres,
                instagramId
        );
        
        return Result.ok(instance);
    }
    
    public static final Result<Singer> reconstitute(
            UUID id,
            String title,
            String description,
            boolean published,
            String flagNote,
            Set<Tag> tags,
            Set<Genre> genres,
            String instagramId,
            String imagePath,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            long totalPlayedCount,
            double rate,
            long totalDuration,
            Set<UUID> albumsIDs,
            Set<UUID> tracksIDs
    ) {
        
        final Result<UniqueEntityID> idOrError = UniqueEntityID.createFromUUID(id);
        final Result<Title> titleOrError = Title.create(title);
        final Result<Description> descriptionOrError = Description.create(description);
        final Result<Flag> flagOrError = Flag.create(flagNote);
        final Result<TagList> tagListOrError = TagList.create(tags);
        final Result<GenreList> genreListOrError = GenreList.create(genres);
        final Result<Rate> rateOrError = Rate.create(rate);
        final Result<InstagramId> instagramIdOrError = InstagramId.create(instagramId);
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        final Result<DateTime> lastModifiedAtOrError = DateTime.create(lastModifiedAt);
        
        final Result<Set<UniqueEntityID>> albumsIDsOrErrors =
                UniqueEntityID.createFromUUIDs(albumsIDs);
        
        final Result<Set<UniqueEntityID>> tracksIDsOrErrors =
                UniqueEntityID.createFromUUIDs(tracksIDs);
        
        final Result[] combinedProps = {
            idOrError,
            titleOrError,
            descriptionOrError,
            flagOrError,
            tagListOrError,
            genreListOrError,
            rateOrError,
            instagramIdOrError,
            createdAtOrError,
            lastModifiedAtOrError,
            albumsIDsOrErrors,
            tracksIDsOrErrors
        };
        
        final Result combinedPropsResult = Result.combine(combinedProps);
        
        if(combinedPropsResult.isFailure) return combinedPropsResult;

        Singer instance = new Singer(
                idOrError.getValue(),
                titleOrError.getValue(),
                descriptionOrError.getValue(),
                published,
                flagOrError.getValue(),
                tagListOrError.getValue(),
                genreListOrError.getValue(),
                Path.of(imagePath),
                instagramIdOrError.getValue(),
                Duration.ofSeconds(totalDuration),
                totalPlayedCount,
                rateOrError.getValue(),
                createdAtOrError.getValue(),
                lastModifiedAtOrError.getValue(),
                albumsIDsOrErrors.getValue(),
                tracksIDsOrErrors.getValue()
        );
        
        return Result.ok(instance);
    }
    

}
