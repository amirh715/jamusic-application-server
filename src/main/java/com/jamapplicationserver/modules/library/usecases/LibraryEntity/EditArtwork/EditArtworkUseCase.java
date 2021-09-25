/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.EditArtwork;

import java.util.*;
import java.nio.file.Path;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.infra.DTOs.usecases.EditArtworkRequestDTO;
import com.jamapplicationserver.infra.Persistence.filesystem.*;
import com.jamapplicationserver.modules.library.repositories.*;

/**
 *
 * @author dada
 */
public class EditArtworkUseCase implements IUsecase<EditArtworkRequestDTO, String> {
    
    private final ILibraryEntityRepository repository;
    private final IGenreRepository genreRepository;
    private final IFilePersistenceManager persistence;
    
    private EditArtworkUseCase(
            ILibraryEntityRepository repository,
            IGenreRepository genreRepository,
            IFilePersistenceManager persistence
    ) {
        this.repository = repository;
        this.genreRepository = genreRepository;
        this.persistence = persistence;
    }
    
    @Override
    public Result execute(EditArtworkRequestDTO request) throws GenericAppException {
        
        try {
            
            final ArrayList<Result> combinedProps = new ArrayList();
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(request.id);
            final Result<UniqueEntityId> updaterIdOrError = UniqueEntityId.createFromString(request.updaterId);
            final Result<Title> titleOrError = Title.create(request.title);
            final Result<Description> descriptionOrError = Description.create(request.description);
            final Result<TagList> tagsOrError = TagList.createFromString(request.tags);
            final Result<Flag> flagOrError = Flag.create(request.flagNote);
            final Result<Set<UniqueEntityId>> genreIdsOrError = UniqueEntityId.createFromStrings(request.genreIds);
            
            final Result<RecordLabel> recordLabelOrError = RecordLabel.create(request.recordLabel);
            final Result<RecordProducer> producerOrError = RecordProducer.create(request.producer);
            final Result<ReleaseDate> releaseYearOrError = ReleaseDate.create(request.releaseYear);
            
            final Result<Lyrics> lyricsOrError = Lyrics.create(request.lyrics);
            
            combinedProps.add(idOrError);
            combinedProps.add(updaterIdOrError);
            
            if(request.title != null)
                combinedProps.add(titleOrError);
            if(request.description != null)
                combinedProps.add(descriptionOrError);
            if(request.tags != null && !request.tags.isEmpty())
                combinedProps.add(tagsOrError);
            if(request.flagNote != null)
                combinedProps.add(flagOrError);
            if(request.genreIds != null && !request.genreIds.isEmpty()) // ??
                combinedProps.add(idOrError);
            
            if(request.recordLabel != null)
                combinedProps.add(recordLabelOrError);
            if(request.producer != null)
                combinedProps.add(producerOrError);
            if(request.releaseYear != null)
                combinedProps.add(releaseYearOrError);
            
            if(request.lyrics != null)
                combinedProps.add(lyricsOrError);
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityId id = idOrError.getValue();
            final UniqueEntityId updaterId = updaterIdOrError.getValue();
            
            final Title title =
                    request.title != null ?
                    titleOrError.getValue() :
                    null;
            final Description description =
                    request.description != null ?
                    descriptionOrError.getValue() :
                    null;
            final TagList tags =
                    request.tags != null && !request.tags.isEmpty() ?
                    tagsOrError.getValue() :
                    null;
            final Flag flag =
                    request.flagNote != null ?
                    flagOrError.getValue() :
                    null;
            final Set<UniqueEntityId> genreIds =
                    request.genreIds != null && !request.genreIds.isEmpty() ?
                    genreIdsOrError.getValue() :
                    null;
            
            final RecordLabel recordLabel =
                    request.recordLabel != null ?
                    recordLabelOrError.getValue() :
                    null;
            final RecordProducer producer =
                    request.producer != null ?
                    producerOrError.getValue() :
                    null;
            final ReleaseDate releaseYear =
                    request.releaseYear != null ?
                    releaseYearOrError.getValue() :
                    null;
            
            final Lyrics lyrics =
                    request.lyrics != null ?
                    lyricsOrError.getValue() :
                    null;
            
            GenreList genres = null;
            if(request.genreIds != null && !request.genreIds.isEmpty()) {
                Result<GenreList> genresOrError = createGenreList(genreIds);
                if(genresOrError.isFailure) return genresOrError;
                genres = genresOrError.getValue();
            }
            
            final Artwork artwork =
                    repository
                            .fetchArtworkById(id)
                            .includeUnpublished()
                            .getSingleResult();
            if(artwork == null) return Result.fail(new ArtworkDoesNotExistError());
            
            final Result result = artwork.edit(
                    title,
                    description,
                    tags,
                    genres,
                    flag,
                    recordLabel,
                    producer,
                    releaseYear,
                    updaterId
            );
            if(result.isFailure) return result;
            
            // track-specific - change lyrics
            if(artwork instanceof Track && request.lyrics != null)
                ((Track) artwork).changeLyrics(lyrics);
            
            // change image
            if(request.image != null) {
                
                final Result<ImageStream> imageOrError = ImageStream.createAndValidate(request.image);
                if(imageOrError.isFailure) return imageOrError;
                
                final ImageStream image = imageOrError.getValue();
                final Path imagePath = persistence.buildPath(artwork.getClass(), image.format.getValue());
                
                persistence.write(image, imagePath);
                artwork.changeImage(imagePath, updaterId);
                
            }
            
            // remove image
            if(request.removeImage) {
                artwork.removeImage(updaterId);
            }
            
            this.repository.save(artwork);
            
            return Result.ok();
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    private Result<GenreList> createGenreList(Set<UniqueEntityId> ids) {
        final Set<Genre> genres = genreRepository.fetchByIds(ids);
        return GenreList.create(genres);
    }
    
    public static EditArtworkUseCase getInstance() {
        return EditArtworkUseCaseHolder.INSTANCE;
    }
    
    private static class EditArtworkUseCaseHolder {

        private static final EditArtworkUseCase INSTANCE =
                new EditArtworkUseCase(
                        LibraryEntityRepository.getInstance(),
                        GenreRepository.getInstance(),
                        FilePersistenceManager.getInstance()
                );
    }
}
