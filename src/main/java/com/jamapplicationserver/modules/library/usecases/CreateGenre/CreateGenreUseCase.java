/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.CreateGenre;

import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.UniqueEntityID;
import com.jamapplicationserver.modules.library.infra.DTOs.entities.GenreDTO;
import com.jamapplicationserver.modules.library.infra.DTOs.usecases.CreateGenreRequestDTO;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author dada
 */
public class CreateGenreUseCase implements IUseCase<CreateGenreRequestDTO, Genre> {
    
    private final IGenreRepository repository;
    
    private CreateGenreUseCase(IGenreRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(CreateGenreRequestDTO request) throws GenericAppException {
        
        try {
            
            final GenreDTO genreDTO = request.genre;
            final String parentGenreId = genreDTO.parentGenreId;
            
            // create genre title objects
            final Result<GenreTitle> titleOrError = GenreTitle.create(genreDTO.title);
            final Result<GenreTitle> titleInPersianOrError = GenreTitle.create(genreDTO.titleInPersian);
            if(titleOrError.isFailure) return titleOrError;
            if(titleInPersianOrError.isFailure) return titleInPersianOrError;
            final GenreTitle title = titleOrError.getValue();
            final GenreTitle titleInPersian = titleInPersianOrError.getValue();
            
            Result<Genre> genreOrError;
            Genre genre;
            
            if(parentGenreId == null) { // create root genre

                genreOrError = Genre.create(title, titleInPersian, null);
                
            } else { // create sub-genre
                
                // create parent genre object
                final Result<UniqueEntityID> idOrError = UniqueEntityID.createFromString(parentGenreId);
                if(idOrError.isFailure) return idOrError;
                final UniqueEntityID id = idOrError.getValue();
                
                // fetch parent genre
                final Genre parent = this.repository.fetchById(id);

                if(parent == null) // parent does not exist
                    return Result.fail(new ParentGenreDoesNotExistError());
                else // parent exists
                    genreOrError = Genre.create(title, titleInPersian, parent);

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
