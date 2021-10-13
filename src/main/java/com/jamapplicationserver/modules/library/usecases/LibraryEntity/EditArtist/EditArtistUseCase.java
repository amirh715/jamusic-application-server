/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.EditArtist;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.EditArtistRequestDTO;
import com.jamapplicationserver.infra.Persistence.filesystem.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;

/**
 *
 * @author dada
 */
public class EditArtistUseCase implements IUsecase<EditArtistRequestDTO, String> {
    
    private final ILibraryEntityRepository repository;
    private final IGenreRepository genreRepository;
    private final IFilePersistenceManager persistence;
    
    private EditArtistUseCase(
            ILibraryEntityRepository repository,
            IGenreRepository genreRepository,
            IFilePersistenceManager persistence
    ) {
        this.repository = repository;
        this.genreRepository = genreRepository;
        this.persistence = persistence;
    }
    
    @Override
    public Result execute(EditArtistRequestDTO request) throws GenericAppException {
        
        try {

            final ArrayList<Result> combinedProps = new ArrayList<>();
            
            final Result<UniqueEntityId> idOrError =
                    UniqueEntityId.createFromString(request.id);
            final Result<Title> titleOrError =
                    Title.create(request.title);
            final Result<Description> descriptionOrError =
                    Description.create(request.description);
            final Result<TagList> tagsOrError =
                    TagList.createFromString(request.tags);
            final Result<Flag> flagOrError =
                    Flag.create(request.flagNote);
            final Result<GenreList> genresOrError =
                    fetchAndCreateGenreList(request.genreIds);
            final Result<InstagramId> instagramIdOrError =
                    InstagramId.create(request.instagramId);
            
            if(request.title != null)
                combinedProps.add(titleOrError);
            if(request.description != null)
                combinedProps.add(descriptionOrError);
            if(request.tags != null)
                combinedProps.add(tagsOrError);
            if(request.flagNote != null)
                combinedProps.add(flagOrError);
            if(request.genreIds != null)
                combinedProps.add(genresOrError);
            if(request.instagramId != null)
                combinedProps.add(instagramIdOrError);
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityId id = idOrError.getValue();
            
            final Title title =
                    request.title != null ? titleOrError.getValue()
                    : null;
            final Description description =
                    request.description != null ? descriptionOrError.getValue()
                    : null;
            final TagList tags =
                    request.tags != null ? tagsOrError.getValue()
                    : null;
            final Flag flag =
                    request.flagNote != null ? flagOrError.getValue()
                    : null;
            final GenreList genres =
                    request.genreIds != null ? genresOrError.getValue()
                    : null;
            final InstagramId instagramId =
                    request.instagramId != null ? instagramIdOrError.getValue()
                    : null;

            final Artist artist =
                    repository.fetchArtistById(id)
                    .includeUnpublished(request.subjectRole)
                    .getSingleResult();
            if(artist == null) return Result.fail(new ArtistDoesNotExistError());
 
            final Result result =
                    artist.edit(title, description, tags, genres, flag, instagramId, request.subjectId);
            if(result.isFailure) return result;
            
            if(request.removeImage) {
                artist.removeImage(request.subjectId);
            }
            
            repository.save(artist);
            
            return Result.ok();
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    private Result<GenreList> fetchAndCreateGenreList(Set<String> genreIds) throws GenericAppException {
        
        try {
            
            if(genreIds == null || genreIds.isEmpty())
                return Result.fail(new ValidationError("fetch genre error"));
            
            final Result<Set<UniqueEntityId>> idsOrErrors = UniqueEntityId.createFromStrings(genreIds);
            if(idsOrErrors.isFailure) return Result.fail(idsOrErrors.getError());
            
            final Set<UniqueEntityId> ids = idsOrErrors.getValue();
            
            final Map<UniqueEntityId, Genre> allGenres = genreRepository.fetchAll();
            
            final boolean doGenresExist = allGenres.keySet().containsAll(ids);
            if(!doGenresExist) return Result.fail(new GenreDoesNotExistError());
            
            final Set<Genre> genres = new HashSet<>();
            ids.forEach(id -> genres.add(allGenres.get(id)));
            
            return GenreList.create(genres);
        } catch(Exception e) {
            e.printStackTrace();
            throw new GenericAppException(e);
        }
        
    }
    
    public static EditArtistUseCase getInstance() {
        return EditArtistUseCaseHolder.INSTANCE;
    }
    
    private static class EditArtistUseCaseHolder {

        private static final EditArtistUseCase INSTANCE =
                new EditArtistUseCase(
                        LibraryEntityRepository.getInstance(),
                        GenreRepository.getInstance(),
                        FilePersistenceManager.getInstance()
                );
    }
}
