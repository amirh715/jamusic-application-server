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
import java.util.stream.*;
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
            InstagramId instagramId,
            UniqueEntityId creatorId
    ) {
        super(
            title,
            description,
            flag,
            tags,
            genres,
            instagramId,
            creatorId
        );
    }
    
    // reconstitution constructor
    private Singer(
            UniqueEntityId id,
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
            UniqueEntityId creatorId,
            UniqueEntityId updaterId,
            Set<UniqueEntityId> albumsIds,
            Set<UniqueEntityId> tracksIds
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
                creatorId,
                updaterId,
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
            InstagramId instagramId,
            UniqueEntityId creatorId
    ) {
        
        if(title == null) return Result.fail(new ValidationError("Title is required for singers."));
        if(creatorId == null) return Result.fail(new ValidationError("Singer must have a creator"));
        
        Singer instance = new Singer(
                title,
                description,
                flag,
                tags,
                genres,
                instagramId,
                creatorId
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
            String imagePath,
            long totalPlayedCount,
            double rate,
            long totalDuration,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            UUID creatorId,
            UUID updaterId,
            String instagramId,
            Set<UUID> albumsIds,
            Set<UUID> tracksIds
    ) {
        
        final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromUUID(id);
        final Result<Title> titleOrError = Title.create(title);
        final Result<Description> descriptionOrError = Description.create(description);
        final Result<Flag> flagOrError = Flag.create(flagNote);
        final Result<TagList> tagListOrError = TagList.create(tags);
        final Result<GenreList> genreListOrError = GenreList.create(genres);
        final Result<Rate> rateOrError = Rate.create(rate);
        final Result<InstagramId> instagramIdOrError = InstagramId.create(instagramId);
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        final Result<DateTime> lastModifiedAtOrError = DateTime.create(lastModifiedAt);
        final Result<UniqueEntityId> creatorIdOrError = UniqueEntityId.createFromUUID(creatorId);
        final Result<UniqueEntityId> updaterIdOrError = UniqueEntityId.createFromUUID(updaterId);
        
        final Result<Set<Result<UniqueEntityId>>> albumsIdsOrErrors =
                UniqueEntityId.createFromUUIDs(albumsIds != null ? albumsIds : Set.of());
        
        final Result<Set<Result<UniqueEntityId>>> tracksIdsOrErrors =
                UniqueEntityId.createFromUUIDs(tracksIds);
        
        final ArrayList<Result> combinedProps = new ArrayList<>();
        
        combinedProps.add(idOrError);
        combinedProps.add(titleOrError);
        combinedProps.add(rateOrError);
        combinedProps.add(createdAtOrError);
        combinedProps.add(lastModifiedAtOrError);
        combinedProps.add(creatorIdOrError);
        combinedProps.add(updaterIdOrError);
        
        if(description != null) combinedProps.add(descriptionOrError);
        if(flagNote != null) combinedProps.add(flagOrError);
        if(tags != null) combinedProps.add(tagListOrError);
        if(genres != null) combinedProps.add(genreListOrError);
        if(instagramId != null) combinedProps.add(instagramIdOrError);
        if(albumsIds != null && !albumsIds.isEmpty()) combinedProps.add(albumsIdsOrErrors);
        if(tracksIds != null && !tracksIds.isEmpty()) combinedProps.add(tracksIdsOrErrors);
        
        final Result combinedPropsResult = Result.combine(combinedProps);
        
        if(combinedPropsResult.isFailure) return combinedPropsResult;
        
        final Set<UniqueEntityId> albumsIdsValues =
                albumsIdsOrErrors.getValue()
                .stream()
                .map(result -> result.getValue())
                .collect(Collectors.toSet());
        
        final Set<UniqueEntityId> tracksIdsValues =
                tracksIdsOrErrors.getValue()
                .stream()
                .map(result -> result.getValue())
                .collect(Collectors.toSet());

        Singer instance = new Singer(
                idOrError.getValue(),
                titleOrError.getValue(),
                description != null ? descriptionOrError.getValue() : null,
                published,
                flagNote != null ? flagOrError.getValue() : null,
                tags != null ? tagListOrError.getValue() : null,
                genres != null ? genreListOrError.getValue() : null,
                imagePath != null ? Path.of(imagePath) : null,
                instagramId != null ? instagramIdOrError.getValue() : null,
                Duration.ofSeconds(totalDuration),
                totalPlayedCount,
                rateOrError.getValue(),
                createdAtOrError.getValue(),
                lastModifiedAtOrError.getValue(),
                creatorIdOrError.getValue(),
                updaterIdOrError.getValue(),
                albumsIds != null && !albumsIds.isEmpty() ?
                        albumsIdsValues : null,
                tracksIds != null && !tracksIds.isEmpty() ?
                        tracksIdsValues : null
        );
        
        return Result.ok(instance);
    }
    

}
