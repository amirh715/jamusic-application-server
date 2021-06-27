/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.CreateLibraryEntity;

import java.util.*;
import java.util.stream.Collectors;
import java.nio.file.Path;
import com.jamapplicationserver.modules.library.infra.DTOs.usecases.CreateLibraryEntityRequestDTO;
import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.infra.Persistence.filesystem.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.infra.DTOs.entities.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.Band.Band;
import com.jamapplicationserver.modules.library.domain.Singer.Singer;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.domain.Track.*;
import com.jamapplicationserver.modules.library.domain.core.AudioStream;

/**
 *
 * @author dada
 */
public class CreateLibraryEntityUseCase implements IUseCase<CreateLibraryEntityRequestDTO, String> {
    
    private final ILibraryEntityRepository repository;
    private final IFilePersistenceManager persistence;
    
    private CreateLibraryEntityUseCase(
            ILibraryEntityRepository repository,
            IFilePersistenceManager persistence
    ) {
        this.repository = repository;
        this.persistence = persistence;
    }
    
    @Override
    public Result execute(CreateLibraryEntityRequestDTO request) throws GenericAppException  {
        
        try {
            
            // validate and determine entity type
            final Result<LibraryEntityType> typeOrError = LibraryEntityType.create(request.type);
            if(typeOrError.isFailure) return typeOrError;
            final LibraryEntityType type = typeOrError.getValue();
            
            final ArrayList<Result> combinedProps = new ArrayList<>();
            
            // validate common props
            final Result<Title> titleOrError = Title.create(request.title);
            final Result<Description> descriptionOrError = Description.create(request.description);
            final Result<TagList> tagsOrError = TagList.create(request.tags);
            final Result<Flag> flagOrError = Flag.create(request.flagNote);
            
            if(!request.genres.isEmpty()) {
                
                
                
            }
            
            final Set<Result<Genre>> genresOrError =
                    request.genres
                    .stream()
                    .map(dto -> createGenre(dto))
                    .collect(Collectors.toSet());
            
            final Set<Genre> genres = new HashSet<>();
            
            for(Result<Genre> genreOrError : genresOrError) {
                if(genreOrError.isFailure) return genreOrError;
                genres.add(genreOrError.getValue());
            }
            
            final Result<GenreList> genreListOrError = GenreList.create(genres);
            
            combinedProps.add(titleOrError);
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            
            if(combinedPropsResult.isFailure) return combinedPropsResult;
                        
            if(request.description != null)
                combinedProps.add(descriptionOrError);
            
            if(request.tags != null)
                combinedProps.add(tagsOrError);
            
            if(request.flagNote != null)
                combinedProps.add(flagOrError);
            
            // validate artist-specific props
            final Result<InstagramId> instagramIdOrError =
                    InstagramId.create(request.instagramId);
            
            if(request.instagramId != null)
                combinedProps.add(instagramIdOrError);
            
            // validate track and album specific props
            final Result<UniqueEntityID> artistIdOrError =
                    UniqueEntityID.createFromString(request.artistId);
            
            if(request.artistId != null)
                combinedProps.add(artistIdOrError);
            
            // validate track-specific props
            final Result<Lyrics> lyricsOrError =
                    Lyrics.create(request.lyrics);

            if(request.lyrics != null)
                combinedProps.add(lyricsOrError);
            
            final Title title = titleOrError.getValue();
            
            final Description description =
                    request.description != null ?
                    descriptionOrError.getValue() : null;
            
            final GenreList genreList =
                    request.genres != null ?
                    genreListOrError.getValue() : null;
            
            final TagList tags =
                    request.tags != null ?
                    tagsOrError.getValue() : null;
            
            final Flag flag =
                    request.flagNote != null ?
                    flagOrError.getValue() : null;
            
            final InstagramId instagramId =
                    request.instagramId != null ?
                    instagramIdOrError.getValue() : null;
            
            final UniqueEntityID artistId =
                    request.artistId != null ?
                    artistIdOrError.getValue() : null;
            
            final Lyrics lyrics =
                    request.lyrics != null ?
                    lyricsOrError.getValue() : null;
            
            LibraryEntity createdEntity;
            
            Artist artist;
            
            Result result;
            
            // create entity
            switch(type) {
                case B:
                    
                    final Result<Band> bandOrError =
                            Band.create(title, description, flag, tags, genreList, instagramId);
                    if(bandOrError.isFailure) return bandOrError;
                    final Band band = bandOrError.getValue();
                    
                    artist = band;
                    
                    createdEntity = band;
                    
                break;
                case S:
                    
                    final Result<Singer> singerOrError =
                            Singer.create(title, description, flag, tags, genreList, instagramId);
                    if(singerOrError.isFailure) return singerOrError;
                    final Singer singer = singerOrError.getValue();
                    
                    artist = singer;
                    
                    createdEntity = singer;
                    
                break;
                case A:
                    
                    // fetch artist
                    artist = this.repository
                            .fetchArtistById(artistId)
                            .includeUnpublished()
                            .getSingleResult();
                    
                    if(artist == null) return Result.fail(new ArtistDoesNotExistError());
                    
                    final Set<Track> tracks = new HashSet<>();

                    // create album tracks
                    for(AlbumTrackDTO dto : request.tracks) {
                        
                        final Result<Track> t = createAlbumTrack(dto);
                        
                        if(t.isFailure) return t;
                        
                        final Track track = t.getValue();
                        
                        final Result<AudioStream> audioOrError = AudioStream.createAndValidate(dto.audio);
                        final Path audioPath = track.getAudioPath();
                        
                        if(audioOrError.isFailure) return audioOrError;
                        
                        final AudioStream audio = audioOrError.getValue();
                        
                        // save audio file
                        this.persistence.write(audio.getStream(), audioPath);
                        
                        // add to album tracks
                        tracks.add(track);
                        
                    }
                    
                    final Result<Album> albumOrError = Album.create(title, description, flag, tags, genreList, tracks);
                    if(albumOrError.isFailure) return albumOrError;
                    
                    final Album album = albumOrError.getValue();
                    
                    result = artist.addAlbum(album);
                    
                    if(result.isFailure) return result;
                    
                    this.repository.save(artist);
                    
                    createdEntity = album;
                    
                break;

                case T:
                    
                    final Result<AudioStream> audioOrError =
                            AudioStream.createAndValidate(request.audio);
                    
                    if(audioOrError.isFailure) return audioOrError;
                    
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
                                    tags,
                                    genreList,
                                    lyrics
                            );
                    if(trackOrError.isFailure) return trackOrError;
                    final Track track = trackOrError.getValue();

                    artist = this.repository
                        .fetchArtistById(artistId)
                        .includeUnpublished()
                        .getSingleResult();

                    if(artist == null) return Result.fail(new ArtistDoesNotExistError());

                    result = artist.addTrack(track);

                    if(result.isFailure) return result;

                    this.persistence.write(audio, audioPath);
                    
                    createdEntity = track;
                    
                break;
                default:
                    return Result.fail(new ValidationError(""));
            }

            // save entity to database
            this.repository.save(artist);
            
            return Result.ok(createdEntity);
            
        } catch(Exception e) {
            e.printStackTrace();
            throw new GenericAppException(e);
        }
        
    }
    
    private Result<Track> createAlbumTrack(AlbumTrackDTO dto) {
        
        final Result<Title> titleOrError = Title.create(dto.title);
        final Result<Description> descriptionOrError = Description.create(dto.description);
        final Result<Flag> flagOrError = Flag.create(dto.flagNote);
        final Result<TagList> tagsOrError = TagList.create(dto.tags);
        final Result<Lyrics> lyricsOrError = Lyrics.create(dto.lyrics);
        
        final ArrayList<Result> combinedProps = new ArrayList<>();
        
        combinedProps.add(titleOrError);
        
        if(dto.description != null) combinedProps.add(descriptionOrError);
        if(dto.flagNote != null) combinedProps.add(flagOrError);
        if(dto.tags != null) combinedProps.add(tagsOrError);
        if(dto.lyrics != null) combinedProps.add(lyricsOrError);
        
        final Result combinedPropsResult = Result.combine(combinedProps);
        if(combinedPropsResult.isFailure) return combinedPropsResult;
        
        final Title title = titleOrError.getValue();
        final Description description = descriptionOrError.getValue();
        final Flag flag = flagOrError.getValue();
        final TagList tags = tagsOrError.getValue();
        final Lyrics lyrics = lyricsOrError.getValue();
        
        final Result<AudioStream> audioOrError = AudioStream.createAndValidate(dto.audio);
        
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
                        tags,
                        null,
                        lyrics
                );
        
        return trackOrError;
    }

    private Result<Genre> createGenre(GenreDTO dto) {
        final Result<GenreTitle> titleOrError = GenreTitle.create(dto.title);
        if(titleOrError.isFailure) return Result.fail(titleOrError.getError());
        final GenreTitle title = titleOrError.getValue();
        return Genre.create(title, null, null);
    }
    
    public static CreateLibraryEntityUseCase getInstance() {
        return CreateLibraryEntityUseCaseHolder.INSTANCE;
    }
    
    private static class CreateLibraryEntityUseCaseHolder {

        private static final CreateLibraryEntityUseCase INSTANCE =
                new CreateLibraryEntityUseCase(
                        LibraryEntityRepository.getInstance(),
                        FilePersistenceManager.getInstance()
                );
    }
}
