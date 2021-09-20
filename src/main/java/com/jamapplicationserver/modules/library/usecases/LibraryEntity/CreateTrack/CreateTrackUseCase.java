/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.CreateTrack;

import java.util.*;
import java.util.stream.*;
import java.nio.file.Path;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.infra.DTOs.usecases.CreateTrackRequestDTO;
import com.jamapplicationserver.infra.Persistence.filesystem.*;
import java.time.Duration;

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
            final Result<UniqueEntityId> creatorIdOrError = UniqueEntityId.createFromString(request.creatorId);
            final Result<RecordLabel> recordLabelOrError = RecordLabel.create(request.recordLabel);
            final Result<RecordProducer> producerOrError = RecordProducer.create(request.producer);
            final Result<ReleaseYear> releaseYearOrError = ReleaseYear.create(request.releaseYear);
            
            if(
                    (request.artistId == null && request.albumId == null) ||
                    (request.artistId != null && request.albumId != null)
            ) return Result.fail(new ValidationError("Either artist id or album id must be provided"));
            
            combinedProps.add(titleOrError);
            combinedProps.add(audioOrError);
            combinedProps.add(creatorIdOrError);
            
            if(request.description != null)
                combinedProps.add(descriptionOrError);
            if(request.genreIds != null)
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

            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
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
            
            final Path audioPath = this.persistence.buildPath(Track.class);
            
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
                            creatorId,
                            recordLabel,
                            producer,
                            releaseYear,
                            lyrics,
                            null,
                            null
                    );
            if(trackOrError.isFailure) return trackOrError;
            
            final Track track = trackOrError.getValue();
            
            Album album = null;
            Artist artist = null;
            
            if(albumId != null) { // album track
                
                album =
                        this.repository.fetchAlbumById(albumId)
                        .includeUnpublished()
                        .getSingleResult();
                if(album == null) return Result.fail(new AlbumDoesNotExistError());
                
                final Result result = album.addTrack(track, creatorId);
                if(result.isFailure) return result;
                
            } else { // single track
                
                artist =
                        this.repository.fetchArtistById(artistId)
                        .includeUnpublished()
                        .getSingleResult();
                if(artist == null) return Result.fail(new ArtistDoesNotExistError());
                
                final Result result = artist.addTrack(track, creatorId);
                if(result.isFailure) return result;
                
            }
            
            // save track audio file
            this.persistence.write(audio, audioPath);
            
            // save track image file
            if(image != null) {
                this.persistence.write(image, audioPath);
            }
            
            if(albumId != null) { // save album
                this.repository.save(album);
            } else { // save artist
                this.repository.save(track);
                this.repository.save(artist);
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
            
            final Map<UniqueEntityId, Genre> allGenres = this.genreRepository.fetchAll();
            
            final boolean doGenresExist = genreIds.containsAll(ids);
            if(!doGenresExist) return Result.fail(new GenreDoesNotExistError());
            
            final Set<Genre> genres = new HashSet<>();
            ids.forEach(id -> genres.add(allGenres.get(id)));
            
            return GenreList.create(genres);
        } catch(Exception e) {
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
