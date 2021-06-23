/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.util.*;
import com.jamapplicationserver.modules.library.domain.core.Description;
import com.jamapplicationserver.modules.library.domain.core.Flag;
import com.jamapplicationserver.modules.library.domain.core.Genre;
import com.jamapplicationserver.modules.library.domain.core.GenreList;
import com.jamapplicationserver.modules.library.domain.core.GenreTitle;
import com.jamapplicationserver.modules.library.domain.core.InstagramId;
import com.jamapplicationserver.modules.library.domain.core.TagList;
import com.jamapplicationserver.modules.library.domain.core.Title;
import com.jamapplicationserver.modules.library.domain.Singer.Singer;
import com.jamapplicationserver.modules.library.domain.Band.Band;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.domain.core.*;
import java.nio.file.Path;
import java.time.Duration;

/**
 *
 * @author amirhossein
 */
public class LibraryEntityGenerator {
    
    public static final Set<Singer> createSingers(int numberOrSingers) {
        
        final Set<Singer> singers = new HashSet<>();
        
        for(int i=0; i<numberOrSingers; i++) {
            final Title title = Title.create("Title " + i).getValue();
            final Description description = Description.create("Description " + i).getValue();
            final Flag flag = Flag.create("Flag " + i).getValue();
            final TagList tags = createTagList();
            final GenreList genres = createGenreList();
            final InstagramId instagramId = InstagramId.create("").getValue();
            final Singer singer =
                    Singer.create(title, description, flag, tags, genres, instagramId).getValue();
            singers.add(singer);
        }
        return singers;
    }
    
    public static final Set<Band> createBands(int numberOfBands) {
        
        final Set<Band> bands = new HashSet<>();
        
        for(int i=0; i<numberOfBands; i++) {
            final Title title = Title.create("Title " + i).getValue();
            final Description description = Description.create("Description " + i).getValue();
            final Flag flag = Flag.create("Flag " + i).getValue();
            final TagList tags = createTagList();
            final GenreList genres = createGenreList();
            final InstagramId instagramId = InstagramId.create("aaaa").getValue();
            final Band band =
                    Band.create(title, description, flag, tags, genres, instagramId).getValue();
            bands.add(band);
        }
        
        return bands;
    }
    
    public static final Set<Track> createTracks(int numberOfTracks) {
        
        final Set<Track> tracks = new HashSet<>();
        
        for(int i=0; i<numberOfTracks; i++) {
            final Title title = Title.create("Title " + i).getValue();
            final Description description = Description.create("Description " + i).getValue();
            final Flag flag = Flag.create("Flag " + i).getValue();
            final TagList tags = createTagList();
            final GenreList genres = createGenreList();
            final AudioFormat format = AudioFormat.create().getValue();
            final Lyrics lyrics = Lyrics.create("Lyrics").getValue();
            final Track track =
                    Track.create(title, description, flag, 1, Duration.ZERO, format, Path.of(""), tags, genres, lyrics).getValue();
            tracks.add(track);
        }
        
        return tracks;
    }
    
    public static final Set<Album> createAlbums(int numberOfAlbums, int numberOfAlbumsTracks) {
        
        final Set<Album> albums = new HashSet<>();
        
        for(int i=0; i<numberOfAlbums; i++) {
            final Title title = Title.create("Title " + i).getValue();
            final Description description = Description.create("Description " + i).getValue();
            final Flag flag = Flag.create("Flag " + i).getValue();
            final TagList tags = createTagList();
            final GenreList genres = createGenreList();
            final Set<Track> tracks = createTracks(numberOfAlbumsTracks);
            final Album album =
                    Album.create(title, description, flag, tags, genres, tracks).getValue();
            albums.add(album);
        }
        
        return albums;
    }
    
    public static final GenreList createGenreList() {
        
        final Set<Genre> genres = new HashSet<>();
        for(int i=0; i<GenreList.MAX_ALLOWED_GENRES; i++) {
            final GenreTitle title = GenreTitle.create("Genre " + i).getValue();
            final GenreTitle titleInPersian = GenreTitle.create("Genre " + i).getValue();
            final Genre genre = Genre.create(title, titleInPersian, null).getValue();
            genres.add(genre);
        }
        return GenreList.create(genres).getValue();
    }
    
    public static final TagList createTagList() {
        
        final Set<Tag> tags = new HashSet<>();
        for(int i=0; i<TagList.MAX_ALLOWED_TAGS; i++) {
            final Tag tag = Tag.create("Tag_" + i).getValue();
            tags.add(tag);
        }
        return TagList.create(tags).getValue();
    }
    
    public static final Title createTitle() {
        return Title.create("Title").getValue();
    }
    
    public static final Description createDescription() {
        return Description.create("Description").getValue();
    }
    
    public static final Flag createFlag() {
        return Flag.create("Flag note").getValue();
    }
    
    public static final InstagramId createInstagramId() {
        return InstagramId.create("@amirh715").getValue();
    }
    
    public static final Lyrics createLyrics() {
        return Lyrics.create("Aha aha aaha aahaaaa").getValue();
    }
    
}
