/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Genres.CreateGenre;

import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.library.infra.DTOs.usecases.CreateGenreRequestDTO;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.repositories.exceptions.*;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author dada
 */
public class CreateGenreUseCase implements IUsecase<CreateGenreRequestDTO, Genre> {
    
    private final IGenreRepository repository;
    
    private CreateGenreUseCase(IGenreRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(CreateGenreRequestDTO request) throws GenericAppException {
        
        try {
            
            final String parentGenreId = request.parentGenreId;
            
            // create genre title objects
            final Result<GenreTitle> titleOrError = GenreTitle.create(request.title);
            final Result<GenreTitle> titleInPersianOrError = GenreTitle.create(request.titleInPersian);
            final Result<UniqueEntityId> creatorIdOrError = UniqueEntityId.createFromString(request.creatorId);
            
            final Result[] combinedProps = {
                titleOrError,
                titleInPersianOrError,
                creatorIdOrError
            };
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final GenreTitle title = titleOrError.getValue();
            final GenreTitle titleInPersian = titleInPersianOrError.getValue();
            final UniqueEntityId creatorId = creatorIdOrError.getValue();
            
            Result<Genre> genreOrError;
            Genre genre;
            
            if(parentGenreId == null) { // create root genre

                genreOrError = Genre.create(title, titleInPersian, null, creatorId);
                
            } else { // create sub-genre
                
                // create parent genre object
                final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(parentGenreId);
                if(idOrError.isFailure) return idOrError;
                final UniqueEntityId id = idOrError.getValue();
                
                // fetch parent genre
                final Genre parent = this.repository.fetchById(id);

                if(parent == null) // parent does not exist
                    return Result.fail(new ParentGenreDoesNotExistError());
                else // parent exists
                    genreOrError = Genre.create(title, titleInPersian, parent, creatorId);

            }
            
            if(genreOrError.isFailure) return genreOrError;
            
            genre = genreOrError.getValue();
            
            // save genre
            this.repository.save(genre);
            
            return Result.ok(genre);
            
        } catch(ConstraintViolationException e) {
            
            final String constraintName = e.getConstraintName();
            
            if(constraintName.equals("title_unique_key"))
                return Result.fail(new GenreAlreadyExistsError());
            
            if(constraintName.equals("title_in_persian_unique_key"))
                return Result.fail(new GenreAlreadyExistsError());
            
            throw new GenericAppException(e);
        } catch(MaxAllowedGenresExceededException e) {
            return Result.fail(new MaxAllowedGenresExceededError());
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static CreateGenreUseCase getInstance() {
        return CreateGenreUseCaseHolder.INSTANCE;
    }
    
    private static class CreateGenreUseCaseHolder {

        private static final CreateGenreUseCase INSTANCE =
                new CreateGenreUseCase(GenreRepository.getInstance());
    }
}
