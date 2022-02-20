/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.CreateTrack;

import java.util.*;
import java.nio.file.Path;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.CreateTrackRequestDTO;
import com.jamapplicationserver.infra.Persistence.filesystem.*;

/**
 *
 * @author dada
 */
public class CreateTrackUseCase implements IUsecase<CreateTrackRequestDTO, String> {
    
    private final ILibraryEntityRepository repository;
    private final IGenreRepository genreRepository;
    private final IFilePersistenceManager persistence;
    
    private CreateTrackUseCase(
            ILibraryEntityRepository repository,
            IGenreRepository genreRepository,
            IFilePersistenceManager persistence
    ) {
        this.repository = repository;
        this.genreRepository = genreRepository;
        this.persistence = persistence;
    }
    
    @Override
    public Result execute(CreateTrackRequestDTO request) throws GenericAppException {
        
        try {
            
            final ArrayList<Result> combinedProps = new ArrayList<>();
            
            final Result<Title> titleOrError = Title.create(request.title);
            final Result<Description> descriptionOrError = Description.create(request.description);
            final Result<GenreList> genreListOrError = fetchAndCreateGenreList(request.genreIds);
            final Result<TagList> tagListOrError = TagList.createFromString(request.tags);
            final Result<Flag> flagOrError = Flag.create(request.flagNote);
            final Result<Lyrics> lyricsOrError = Lyrics.create(request.lyrics);
            final Result<UniqueEntityId> artistIdOrError = UniqueEntityId.createFromString(request.artistId);
            final Result<ImageStream> imageOrError = ImageStream.createAndValidate(request.image);
            final Result<AudioStream> audioOrError = AudioStream.createAndValidate(request.audio);
            final Result<UniqueEntityId> albumIdOrError = UniqueEntityId.createFromString(request.albumId);
            final Result<RecordLabel> recordLabelOrError = RecordLabel.create(request.recordLabel);
            final Result<RecordProducer> producerOrError = RecordProducer.create(request.producer);
            final Result<ReleaseDate> releaseDateOrError = ReleaseDate.create(request.releaseDate);
            
            if(
                    (request.artistId == null && request.albumId == null) ||
                    (request.artistId != null && request.albumId != null)
            ) return Result.fail(new ValidationError("Either artist id or album id must be provided"));
            
            combinedProps.add(titleOrError);
            combinedProps.add(audioOrError);
            
            if(request.description != null)
                combinedProps.add(descriptionOrError);
            if(request.genreIds != null && !request.genreIds.isEmpty())
                combinedProps.add(genreListOrError);
            if(request.tags != null)
                combinedProps.add(tagListOrError);
            if(request.flagNote != null)
                combinedProps.add(flagOrError);
            if(request.lyrics != null)
                combinedProps.add(lyricsOrError);
            if(request.image != null)
                combinedProps.add(imageOrError);
            if(request.artistId != null)
                combinedProps.add(artistIdOrError);
            if(request.albumId != null)
                combinedProps.add(albumIdOrError);
            if(request.releaseDate != null)
                combinedProps.add(releaseDateOrError);

            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final Title title = titleOrError.getValue();
            final Description description =
                    request.description != null ? descriptionOrError.getValue()
                    : null;
            final GenreList genreList =
                    request.genreIds != null && !request.genreIds.isEmpty() ? genreListOrError.getValue()
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
            final Lyrics lyrics =
                    request.lyrics != null ? lyricsOrError.getValue()
                    : null;
            
            final UniqueEntityId artistId =
                    request.artistId != null ? artistIdOrError.getValue()
                    : null;
            
            final UniqueEntityId albumId =
                    request.albumId != null ? albumIdOrError.getValue()
                    : null;
            
            final ImageStream image =
                    request.image != null ? imageOrError.getValue()
                    : null;
            final AudioStream audio = audioOrError.getValue();
            
            final Path audioPath = persistence.buildPath(Track.class);
            
            Album album = null;
            Artist artist = null;
            
            if(albumId != null) {
                
                album =
                        repository.fetchAlbumById(albumId)
                        .includeUnpublished(request.subjectRole)
                        .getSingleResult();
                if(album == null) return Result.fail(new AlbumDoesNotExistError());
                
                artist = album.getArtist();
                
            } else if(artistId != null) {
                
                artist =
                        repository.fetchArtistById(artistId)
                            .includeUnpublished(request.subjectRole)
                            .getSingleResult();
                if(artist == null) return Result.fail(new ArtistDoesNotExistError());
                
            }
            
            final Result<Track> trackOrError =
                    Track.create(
                            title,
                            description,
                            flag,
                            audio.size,
                            audio.duration,
                            audioPath,
                            audio.format,
                            tagList,
                            genreList,
                            request.subjectId,
                            recordLabel,
                            producer,
                            releaseYear,
                            lyrics
                    );
            if(trackOrError.isFailure) return trackOrError;
            
            // write image to disk
            if(image != null) {
                final Path imagePath = persistence.buildPath(Track.class);
                persistence.write(image, imagePath);
            }
            
            // write audio to disk
            persistence.write(audio, audioPath);
            
            final Track track = trackOrError.getValue();

            if(album != null) {
                final Result result = album.addTrack(track, request.subjectId);
                if(result.isFailure) return result;
                repository.save(album);
            } else if(artist != null) {
                final Result result = artist.addArtwork(track, request.subjectId);
                if(result.isFailure) return result;
                repository.save(track);
                repository.save(artist);
            }
            
            return Result.ok();
        } catch(Exception e) {
            e.printStackTrace();
            throw new GenericAppException(e);
        }
        
    }
    
    private Result<GenreList> fetchAndCreateGenreList(Set<String> genreIds) throws GenericAppException {
        
        try {
            
            if(genreIds == null || genreIds.isEmpty())
                return Result.fail(new ValidationError("fetch genre error"));
            
            final Result<Set<UniqueEntityId>> idsOrErrors =
                    UniqueEntityId.createFromStrings(genreIds);
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
    
    public static CreateTrackUseCase getInstance() {
        return CreateTrackUseCaseHolder.INSTANCE;
    }
    
    private static class CreateTrackUseCaseHolder {

        private static final CreateTrackUseCase INSTANCE =
                new CreateTrackUseCase(
                        LibraryEntityRepository.getInstance(),
                        GenreRepository.getInstance(),
                        FilePersistenceManager.getInstance()
                );
    }
}
