/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.CreateAlbum;

import java.util.*;
import java.nio.file.Path;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.infra.Persistence.filesystem.*;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.domain.ImageStream;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.infra.DTOs.usecases.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import java.util.stream.Collectors;

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
            final Result<UniqueEntityId> creatorIdOrError = UniqueEntityId.createFromString(request.creatorId);
            final Result<UniqueEntityId> artistIdOrError = UniqueEntityId.createFromString(request.artistId);
            final Result<RecordLabel> recordLabelOrError = RecordLabel.create(request.recordLabel);
            final Result<RecordProducer> producerOrError = RecordProducer.create(request.producer);
            final Result<ReleaseYear> releaseYearOrError = ReleaseYear.create(request.releaseYear);
            final Result<ImageStream> imageOrError = ImageStream.createAndValidate(request.image);
            
            combinedProps.add(titleOrError);
            combinedProps.add(artistIdOrError);
            combinedProps.add(creatorIdOrError);
            
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
            if(request.releaseYear != null)
                combinedProps.add(releaseYearOrError);
            if(request.image != null)
                combinedProps.add(imageOrError);
            
            // validate properties
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            // populate properties
            final Title title = titleOrError.getValue();
            final UniqueEntityId creatorId = creatorIdOrError.getValue();
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
            final ReleaseYear releaseYear =
                    request.releaseYear != null ? releaseYearOrError.getValue()
                    : null;
            final UniqueEntityId artistId = artistIdOrError.getValue();
            final ImageStream image =
                    request.image != null ? imageOrError.getValue()
                    : null;
            
            // fetch artist
            final Artist artist =
                    this.repository
                            .fetchArtistById(artistId)
                            .includeUnpublished()
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
                            creatorId,
                            recordLabel,
                            producer,
                            releaseYear,
                            artist
                    );
            if(albumOrError.isFailure) return albumOrError;
            
            final Album album = albumOrError.getValue();
                        
            final Result result = artist.addAlbum(album, creatorId);
            if(result.isFailure) return result;
                        
            // save album image
            if(request.image != null) {
                final Path path = this.persistence.buildPath(Album.class);
                album.changeImage(path, creatorId);
                this.persistence.write(image, path);
            }
            
            // save album to database
            this.repository.save(album);
            
            // save artist to database
            this.repository.save(artist);
            
            return Result.ok(album);
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
            
            final Set<UniqueEntityId> ids = idsOrErrors.getValue();
            
            final Map<UniqueEntityId, Genre> allGenres = this.genreRepository.fetchAll();
            
            final boolean doGenresExist = genreIds.containsAll(genreIds);
            if(!doGenresExist) return Result.fail(new GenreDoesNotExistError());
            
            final Set<Genre> genres = new HashSet<>();
            ids.forEach(id -> genres.add(allGenres.get(id)));
            
            return GenreList.create(genres);
        } catch(Exception e) {
            e.printStackTrace();
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
