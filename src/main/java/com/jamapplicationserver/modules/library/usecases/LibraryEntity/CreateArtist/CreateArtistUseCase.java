/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.CreateArtist;

import java.util.*;
import java.nio.file.Path;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.infra.Persistence.filesystem.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.CreateArtistRequestDTO;
import com.jamapplicationserver.modules.library.domain.Band.Band;
import com.jamapplicationserver.modules.library.domain.Singer.Singer;

/**
 *
 * @author dada
 */
public class CreateArtistUseCase implements IUsecase<CreateArtistRequestDTO, String> {
    
    private final ILibraryEntityRepository repository;
    private final IGenreRepository genreRepository;
    private final IFilePersistenceManager persistence;
    
    private CreateArtistUseCase(
            ILibraryEntityRepository repository,
            IGenreRepository genreRepository,
            IFilePersistenceManager persistence
    ) {
        this.repository = repository;
        this.genreRepository = genreRepository;
        this.persistence = persistence;
    }
    
    @Override
    public Result execute(CreateArtistRequestDTO request) throws GenericAppException {
        
        try {
                        
            final Result<LibraryEntityType> typeOrError =
                    LibraryEntityType.create(request.type);
            if(typeOrError.isFailure) return typeOrError;
            final LibraryEntityType type = typeOrError.getValue();
            if(!type.isArtist()) return Result.fail(new ValidationError(""));
            
            // validate and instantiate properties
            final ArrayList<Result> combinedProps = new ArrayList<>();
            
            final Result<Title> titleOrError = Title.create(request.title);
            final Result<Description> descriptionOrError = Description.create(request.description);
            final Result<GenreList> genreListOrError = fetchAndCreateGenreList(request.genreIds);
            final Result<TagList> tagListOrError = TagList.createFromString(request.tags);
            final Result<Flag> flagOrError = Flag.create(request.flagNote);
            final Result<InstagramId> instagramIdOrError = InstagramId.create(request.instagramId);
            final Result<ImageStream> imageOrError = ImageStream.createAndValidate(request.image);
            
            combinedProps.add(titleOrError);
            
            if(request.description != null)
                combinedProps.add(descriptionOrError);
            if(request.genreIds != null)
                combinedProps.add(genreListOrError);
            if(request.tags != null)
                combinedProps.add(tagListOrError);
            if(request.flagNote != null)
                combinedProps.add(flagOrError);
            if(request.instagramId != null)
                combinedProps.add(instagramIdOrError);
            if(request.image != null)
                combinedProps.add(imageOrError);

            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final Title title = titleOrError.getValue();
            final Description description =
                    request.description != null ? descriptionOrError.getValue()
                    : null;
            final GenreList genreList =
                    request.genreIds != null ? genreListOrError.getValue()
                    : null;
            final TagList tagList =
                    request.tags != null ? tagListOrError.getValue()
                    : null;
            final Flag flag =
                    request.flagNote != null ? flagOrError.getValue()
                    : null;
            final InstagramId instagramId =
                    request.instagramId != null ? instagramIdOrError.getValue()
                    : null;
            final ImageStream image =
                    request.image != null ? imageOrError.getValue()
                    : null;
            final UniqueEntityId creatorId = request.creatorId;
            
            Result artistOrError;
            Artist artist;
            
            // create a band
            if(type.equals(type.B))
                artistOrError = Band.create(title, description, flag, tagList, genreList, instagramId, creatorId);
            else // create a singer
                artistOrError = Singer.create(title, description, flag, tagList, genreList, instagramId, creatorId);

            if(artistOrError.isFailure) return artistOrError;
            artist = (Artist) artistOrError.getValue();
            
            // save image
            if(image != null) {
                final Path path = this.persistence.buildPath(
                        artist.getClass(),
                        image.format.getValue()
                );
                artist.changeImage(path, creatorId);
                persistence.write(image, path);
            }
            
            // save artist to database
            this.repository.save(artist);
            
            return Result.ok(artist);
        } catch(Exception e) {
            e.printStackTrace();
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
            
            final Map<UniqueEntityId, Genre> allGenres = this.genreRepository.fetchAll();
            
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
    
    public static CreateArtistUseCase getInstance() {
        return CreateArtistUseCaseHolder.INSTANCE;
    }
    
    private static class CreateArtistUseCaseHolder {

        private static final CreateArtistUseCase INSTANCE =
                new CreateArtistUseCase(
                        LibraryEntityRepository.getInstance(),
                        GenreRepository.getInstance(),
                        FilePersistenceManager.getInstance()
                );
    }
}
