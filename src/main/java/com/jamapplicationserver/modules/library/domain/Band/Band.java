/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Band;

import com.jamapplicationserver.modules.library.domain.Singer.Singer;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.core.logic.*;
import java.util.*;
import java.util.stream.*;
import java.nio.file.Path;
import java.time.*;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author amirhossein
 */
public class Band extends Artist {
    
    public static final int MAX_ALLOWED_MEMBERS_PER_BAND = 20;
    
    private Set<Singer> members = new HashSet<>();
    
    // creation constructor
    private Band(
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
    private Band(
        UniqueEntityId id,
        Title title,
        Description description,
        boolean published,
        Flag flag,
        TagList tags,
        GenreList genres,
        InstagramId instagramId,
        Path imagePath,
        Duration totalDuration,
        long totalPlayedCount,
        Rate rate,
        DateTime createdAt,
        DateTime lastModifiedAt,
        UniqueEntityId creatorId,
        UniqueEntityId updaterId,
        Set<UniqueEntityId> albumsIds,
        Set<UniqueEntityId> tracksIds,
        Set<Singer> members
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
        this.members = members;
    }
    
    public static Result<Band> create(
            Title title,
            Description description,
            Flag flag,
            TagList tags,
            GenreList genres,
            InstagramId instagramId,
            UniqueEntityId creatorId
    ) {
        
        if(title == null)
            return Result.fail(new ValidationError("Title is required for bands."));
        
        Band instance = new Band(
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
    
    public static Result<Band> reconstitute(
            UUID id,
            String title,
            String description,
            boolean published,
            String flag,
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
            Set<UUID> tracksIds,
            Set<Singer> members
    ) {
        
        final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromUUID(id);
        final Result<Title> titleOrError = Title.create(title);
        final Result<Description> descriptionOrError = Description.create(description);
        final Result<Flag> flagOrError = Flag.create(flag);
        final Result<TagList> tagListOrError = TagList.create(tags);
        final Result<GenreList> genreListOrError = GenreList.create(genres);
        final Result<Rate> rateOrError = Rate.create(rate);
        final Result<InstagramId> instagramIdOrError = InstagramId.create(instagramId);
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        final Result<DateTime> lastModifiedAtOrError = DateTime.create(lastModifiedAt);
        final Result<UniqueEntityId> creatorIdOrError = UniqueEntityId.createFromUUID(creatorId);
        final Result<UniqueEntityId> updaterIdOrError = UniqueEntityId.createFromUUID(updaterId);
        
        final Result<Set<Result<UniqueEntityId>>> albumsIdsOrErrors =
                UniqueEntityId.createFromUUIDs(albumsIds);
        
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
        if(flag != null) combinedProps.add(flagOrError);
        if(tags != null && !tags.isEmpty()) combinedProps.add(tagListOrError);
        if(genres != null && !genres.isEmpty()) combinedProps.add(genreListOrError);
        if(instagramId != null) combinedProps.add(instagramIdOrError);
        
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
        
        Band instance = new Band(
                idOrError.getValue(),
                titleOrError.getValue(),
                description != null ? descriptionOrError.getValue() : null,
                published,
                flag != null ? flagOrError.getValue() : null,
                tags != null && !tags.isEmpty() ? tagListOrError.getValue() : null,
                genres != null && !genres.isEmpty() ? genreListOrError.getValue() : null,
                instagramId != null ? instagramIdOrError.getValue() : null,
                imagePath != null ? Path.of(imagePath) : null,
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
                        tracksIdsValues : null,
                members != null && !members.isEmpty() ? members : null
        );
        
        return Result.ok(instance);
        
    }
    
    public final Result addMember(Singer member) {
        
        if(this.members.size() > MAX_ALLOWED_MEMBERS_PER_BAND)
            return Result.fail(new BandMaxAllowedMembersExceededError());
        
        final boolean alreadyExists = this.members.contains(member);
        
        if(alreadyExists) return Result.ok();
        
        this.duration.plus(member.getDuration());
        
        this.members.add(member);
        
        this.modified();
        
        return Result.ok();
    }
    
    public final Result removeMember(Singer member) {
        
        this.duration.minus(member.getDuration());
        
        this.members.remove(member);
        
        this.modified();
        
        return Result.ok();
    }
    
    public final Set<Singer> getMembers() {
        return this.members;
    }
    
    public final boolean hasMembers() {
        return !this.members.isEmpty();
    }
    
}
