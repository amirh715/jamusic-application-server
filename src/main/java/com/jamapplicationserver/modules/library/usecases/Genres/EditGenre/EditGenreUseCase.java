/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.Genres.EditGenre;

import java.util.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.infra.DTOs.usecases.EditGenreRequestDTO;
import com.jamapplicationserver.infra.Persistence.database.UpdaterOrCreatorDoesNotExistException;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author dada
 */
public class EditGenreUseCase implements IUsecase<EditGenreRequestDTO, String> {
    
    private final IGenreRepository repository;
    
    private EditGenreUseCase(IGenreRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(EditGenreRequestDTO request) throws GenericAppException {
        
        try {
            
            final ArrayList combinedProps = new ArrayList<>();
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(request.id);
            combinedProps.add(idOrError);
            
            final Result<GenreTitle> titleOrError = GenreTitle.create(request.title);
            final Result<GenreTitle> titleInPersianOrError = GenreTitle.create(request.titleInPersian);
            final Result<UniqueEntityId> updaterIdOrError = UniqueEntityId.createFromString(request.updaterId);
            
            combinedProps.add(updaterIdOrError);
            if(request.title != null) combinedProps.add(titleOrError);
            if(request.titleInPersian != null) combinedProps.add(titleInPersianOrError);
            
            if(combinedProps.size() < 3) return Result.fail(new ValidationError(""));
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityId id = idOrError.getValue();
            final UniqueEntityId updaterId = updaterIdOrError.getValue();
            
            final Genre genre = this.repository.fetchById(id);
            
            if(genre == null) return Result.fail(new GenreDoesNotExistError());

            final GenreTitle title =
                    request.title != null ?
                    titleOrError.getValue() : genre.getTitle();
            final GenreTitle titleInPersian =
                    request.titleInPersian != null ?
                    titleInPersianOrError.getValue() : genre.getTitleInPersian();
            genre.edit(title, titleInPersian, updaterId);
            
            this.repository.save(genre);
            
            return Result.ok(genre);
            
        } catch(ConstraintViolationException e) {
            return Result.fail(new GenreAlreadyExistsError());
        } catch(UpdaterOrCreatorDoesNotExistException e) {
            return Result.fail(new UpdaterOrCreatorDoesNotExistError());
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static EditGenreUseCase getInstance() {
        return EditGenreUseCaseHolder.INSTANCE;
    }
    
    private static class EditGenreUseCaseHolder {

        private static final EditGenreUseCase INSTANCE =
                new EditGenreUseCase(GenreRepository.getInstance());
    }
}
