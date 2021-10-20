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
import com.jamapplicationserver.modules.library.infra.DTOs.commands.EditArtworkRequestDTO;
import com.jamapplicationserver.infra.Persistence.filesystem.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.Player.*;

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
            final Result<Title> titleOrError = Title.create(request.title);
            final Result<Description> descriptionOrError = Description.create(request.description);
            final Result<TagList> tagsOrError = TagList.createFromString(request.tags);
            final Result<Flag> flagOrError = Flag.create(request.flagNote);
            final Result<GenreList> genresOrError = fetchAndCreateGenreList(request.genreIds);
            
            final Result<RecordLabel> recordLabelOrError = RecordLabel.create(request.recordLabel);
            final Result<RecordProducer> producerOrError = RecordProducer.create(request.producer);
            final Result<ReleaseDate> releaseYearOrError = ReleaseDate.create(request.releaseYear);
            
            final Result<Lyrics> lyricsOrError = Lyrics.create(request.lyrics);
            
            combinedProps.add(idOrError);
            
            if(request.title != null)
                combinedProps.add(titleOrError);
            if(request.description != null)
                combinedProps.add(descriptionOrError);
            if(request.tags != null && !request.tags.isEmpty())
                combinedProps.add(tagsOrError);
            if(request.flagNote != null)
                combinedProps.add(flagOrError);
            if(request.genreIds != null && !request.genreIds.isEmpty())
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
            final GenreList genres =
                    request.genreIds != null ? genresOrError.getValue()
                    : null;
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
            
            final Artwork artwork =
                    (Artwork) repository
                            .fetchArtworkById(id)
                            .includeUnpublished(request.subjectRole)
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
                    request.subjectId
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
                artwork.changeImage(imagePath, request.subjectId);
                
            }
            
            // remove image
            if(request.removeImage) {
                artwork.removeImage(request.subjectId);
            }
            
            repository.save(artwork);
            
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
