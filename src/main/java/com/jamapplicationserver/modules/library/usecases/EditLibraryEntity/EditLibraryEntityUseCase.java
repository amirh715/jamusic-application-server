/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.EditLibraryEntity;

import com.jamapplicationserver.modules.library.infra.DTOs.usecases.EditLibraryEntityRequestDTO;
import java.util.*;
import java.util.stream.Collectors;
import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.UniqueEntityID;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.infra.DTOs.entities.*;
import com.jamapplicationserver.modules.library.repositories.mappers.*;
import com.jamapplicationserver.modules.library.domain.Band.Band;
import com.jamapplicationserver.modules.library.domain.Singer.Singer;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.domain.Track.Track;

/**
 *
 * @author dada
 */
public class EditLibraryEntityUseCase implements IUseCase<EditLibraryEntityRequestDTO, String> {
    
    private final ILibraryEntityRepository repository;
    
    private EditLibraryEntityUseCase(ILibraryEntityRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(EditLibraryEntityRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<LibraryEntityType> typeOrError = LibraryEntityType.create(request.type);
            if(typeOrError.isFailure) return typeOrError;
            
            final LibraryEntityType type = typeOrError.getValue();
            
            final ArrayList<Result> combinedProps = new ArrayList<>();
            
            final Result<UniqueEntityID> idOrError = UniqueEntityID.createFromString(request.id);
            final Result<Title> titleOrError = Title.create(request.title);
            final Result<Description> descriptionOrError = Description.create(request.description);
            final Result<TagList> tagsOrError = TagList.create(request.tags);
            final Result<Flag> flagOrError = Flag.create(request.flagNote);
            final Result<InstagramId> instagramIdOrError = InstagramId.create(request.instagramId);
            
            final Set<Result<Genre>> genresOrError =
                    request.genres
                    .stream()
                    .map(dto -> createGenre(dto))
                    .collect(Collectors.toSet());
            
            final Set<Genre> genres = new HashSet<>();
            
            for(Result<Genre> genreOrError : genresOrError) {
                if(genreOrError.isFailure) return genreOrError;
                genres.add(genreOrError.getValue());
            }
            
            final Result<GenreList> genreListOrError = GenreList.create(genres);
            
            combinedProps.add(idOrError);
            
            if(request.title != null) combinedProps.add(titleOrError);
            if(request.description != null) combinedProps.add(descriptionOrError);
            if(request.genres != null) combinedProps.add(genreListOrError);
            if(request.tags != null) combinedProps.add(tagsOrError);
            if(request.flagNote != null) combinedProps.add(flagOrError);
            if(request.instagramId != null) combinedProps.add(instagramIdOrError);
            
            if(combinedProps.size() < 2) return Result.fail(new ValidationError("At least one field must be edited"));
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityID id = idOrError.getValue();
            final Title title = titleOrError.getValue();
            final Description description = descriptionOrError.getValue();
            final GenreList genreList = genreListOrError.getValue();
            final TagList tags = tagsOrError.getValue();
            final Flag flag = flagOrError.getValue();
            final InstagramId instagramId = instagramIdOrError.getValue();
            
            final LibraryEntity entity =
                    this.repository.fetchById(id).getSingleResult();
            
            entity.edit(title, description, tags, genreList);
            
            this.repository.save(entity);
            
            return Result.ok();
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    private Result<Genre> createGenre(GenreDTO dto) {
        final Result<GenreTitle> titleOrError = GenreTitle.create(dto.title);
        if(titleOrError.isFailure) return Result.fail(titleOrError.getError());
        final GenreTitle title = titleOrError.getValue();
        return Genre.create(title, null, null);
    }
    
    public static EditLibraryEntityUseCase getInstance() {
        return EditLibraryEntityUseCaseHolder.INSTANCE;
    }
    
    private static class EditLibraryEntityUseCaseHolder {

        private static final EditLibraryEntityUseCase INSTANCE =
                new EditLibraryEntityUseCase(LibraryEntityRepository.getInstance());
    }
}
