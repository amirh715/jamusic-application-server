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
import java.nio.file.Path;
import java.time.*;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author amirhossein
 */
public class Band extends Artist {
    
    public static final int MAX_ALLOWED_MEMBERS_PER_BAND = 15;
    
    private Set<Singer> members = new HashSet<>();
    
    // creation constructor
    private Band(
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
    private Band(
        UniqueEntityID id,
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
        Set<UniqueEntityID> albumsIds,
        Set<UniqueEntityID> tracksIds,
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
            InstagramId instagramId
    ) {
        
        if(title == null) return Result.fail(new ValidationError("Title is required for bands."));
        
        Band instance = new Band(
                title,
                description,
                flag,
                tags,
                genres,
                instagramId
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
            String instagramId,
            String imagePath,
            long totalPlayedCount,
            double rate,
            long totalDuration,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            Set<UUID> albumsIDs,
            Set<UUID> tracksIDs,
            Set<Singer> members
    ) {
        
        final Result<UniqueEntityID> idOrError = UniqueEntityID.createFromUUID(id);
        final Result<Title> titleOrError = Title.create(title);
        final Result<Description> descriptionOrError = Description.create(description);
        final Result<Flag> flagOrError = Flag.create(flag);
        final Result<TagList> tagListOrErrors = TagList.create(tags);
        final Result<GenreList> genreListOrErrors = GenreList.create(genres);
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
            tagListOrErrors,
            genreListOrErrors,
            rateOrError,
            instagramIdOrError,
            createdAtOrError,
            lastModifiedAtOrError,
            albumsIDsOrErrors,
            tracksIDsOrErrors
        };
        
        final Result combinedPropsResult = Result.combine(combinedProps);
        
        if(combinedPropsResult.isFailure) return combinedPropsResult;
        
        Band instance = new Band(
                idOrError.getValue(),
                titleOrError.getValue(),
                descriptionOrError.getValue(),
                published,
                flagOrError.getValue(),
                tagListOrErrors.getValue(),
                genreListOrErrors.getValue(),
                instagramIdOrError.getValue(),
                Path.of(imagePath),
                Duration.ofSeconds(totalDuration),
                totalPlayedCount,
                rateOrError.getValue(),
                createdAtOrError.getValue(),
                lastModifiedAtOrError.getValue(),
                albumsIDsOrErrors.getValue(),
                tracksIDsOrErrors.getValue(),
                members
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
        
        return Result.ok();
    }
    
    public final Result removeMember(Singer member) {
        
        this.duration.minus(member.getDuration());
        
        this.members.remove(member);
        
        return Result.ok();
    }
    
    public final Set<Singer> getMembers() {
        return this.members;
    }
    
}
