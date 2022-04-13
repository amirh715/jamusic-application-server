/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.CreateAlbum;

import java.util.*;
import java.nio.file.Path;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.CreateAlbumRequestDTO;
import com.jamapplicationserver.infra.Persistence.filesystem.*;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author dada
 */
public class CreateAlbumUseCase implements IUsecase<CreateAlbumRequestDTO, String> {
    
    private final ILibraryEntityRepository repository;
    private final IGenreRepository genreRepository;
    private final IFilePersistenceManager persistence;
    
    private CreateAlbumUseCase(
            ILibraryEntityRepository repository,
            IGenreRepository genreRepository,
            IFilePersistenceManager persistence
    ) {
        this.repository = repository;
        this.genreRepository = genreRepository;
        this.persistence = persistence;
    }
    
    @Override
    public Result execute(CreateAlbumRequestDTO request) throws GenericAppException {
        
        try {
                        
            final ArrayList<Result> combinedProps = new ArrayList<>();
            
            final Result<Title> titleOrError = Title.create(request.title);
            final Result<Description> descriptionOrError = Description.create(request.description);
            final Result<GenreList> genreListOrError = fetchAndCreateGenreList(request.genreIds);
            final Result<TagList> tagListOrError = TagList.createFromString(request.tags);
            final Result<Flag> flagOrError = Flag.create(request.flagNote);
            final Result<UniqueEntityId> artistIdOrError = UniqueEntityId.createFromString(request.artistId);
            final Result<RecordLabel> recordLabelOrError = RecordLabel.create(request.recordLabel);
            final Result<RecordProducer> producerOrError = RecordProducer.create(request.producer);
            final Result<ReleaseDate> releaseDateOrError = ReleaseDate.create(request.releaseDate);
            final Result<ImageStream> imageOrError = ImageStream.createAndValidate(request.image);
            
            combinedProps.add(titleOrError);
            combinedProps.add(artistIdOrError);
            
            if(request.description != null)
                combinedProps.add(descriptionOrError);
            if(request.genreIds != null)
                combinedProps.add(genreListOrError);
            if(request.tags != null)
                combinedProps.add(tagListOrError);
            if(request.flagNote != null)
                combinedProps.add(flagOrError);
            if(request.recordLabel != null)
                combinedProps.add(recordLabelOrError);
            if(request.producer != null)
                combinedProps.add(producerOrError);
            if(request.releaseDate != null)
                combinedProps.add(releaseDateOrError);
            if(request.image != null)
                combinedProps.add(imageOrError);
            
            // validate properties
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            // populate properties
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
            final RecordLabel recordLabel =
                    request.recordLabel != null ? recordLabelOrError.getValue()
                    : null;
            final RecordProducer producer =
                    request.producer != null ? producerOrError.getValue()
                    : null;
            final ReleaseDate releaseYear =
                    request.releaseDate != null ? releaseDateOrError.getValue()
                    : null;
            final UniqueEntityId artistId = artistIdOrError.getValue();
            final ImageStream image =
                    request.image != null ? imageOrError.getValue()
                    : null;
            
            // fetch artist
            final Artist artist =
                    repository
                            .fetchArtistById(artistId)
                            .includeUnpublished(request.subjectRole)
                            .getSingleResult();
            if(artist == null) return Result.fail(new ArtistDoesNotExistError());
            
            // create album
            final Result<Album> albumOrError =
                    Album.create(
                            title,
                            description,
                            flag,
                            tagList,
                            genreList,
                            request.subjectId,
                            recordLabel,
                            producer,
                            releaseYear
                    );
            if(albumOrError.isFailure) return albumOrError;
            
            final Album album = albumOrError.getValue();
                        
            final Result result = artist.addArtwork(album, request.subjectId);
            if(result.isFailure) return result;
                        
            // save album image
            if(image != null) {
                final Path path = persistence.buildPath(Album.class, image.format.getValue());
                album.changeImage(path, request.subjectId);
                persistence.write(image, path);
            }
            
            // save album to database
            repository.save(album);
            
            // save artist to database
            repository.save(artist);
            
            return Result.ok(album);
        } catch(Exception e) {
            e.printStackTrace();
            LogService.getInstance().error(e);
            throw new GenericAppException(e);
        }
        
    }
    
    private Result<GenreList> fetchAndCreateGenreList(Set<String> genreIds) throws GenericAppException {
        
        try {
            
            if(genreIds == null || genreIds.isEmpty())
                return Result.fail(new ValidationError("fetch genre error"));
            
            final Result<Set<UniqueEntityId>> idsOrErrors = UniqueEntityId.createFromStrings(genreIds);
            
            final Set<UniqueEntityId> ids = idsOrErrors.getValue();
            
            final Map<UniqueEntityId, Genre> allGenres = genreRepository.fetchAll();
            
            final boolean doGenresExist =
                    allGenres.keySet()
                    .containsAll(ids);
            if(!doGenresExist) return Result.fail(new GenreDoesNotExistError());
            
            final Set<Genre> genres = new HashSet<>();
            ids.forEach(id -> genres.add(allGenres.get(id)));
            
            return GenreList.create(genres);
        } catch(Exception e) {
            LogService.getInstance().error(e);
            throw new GenericAppException(e);
        }
        
    }
    
    public static CreateAlbumUseCase getInstance() {
        return CreateAlbumUseCaseHolder.INSTANCE;
    }
    
    private static class CreateAlbumUseCaseHolder {

        private static final CreateAlbumUseCase INSTANCE =
                new CreateAlbumUseCase(
                        LibraryEntityRepository.getInstance(),
                        GenreRepository.getInstance(),
                        FilePersistenceManager.getInstance()
                );
    }
}
