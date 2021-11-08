/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.http;

/**
 *
 * @author dada
 */
public class LibraryPaths {
    
    public final static String CREATE_NEW_ARTIST = "/artist/";
    public final static String CREATE_NEW_ALBUM = "/album/";
    public final static String CREATE_NEW_TRACK = "/track/";
    
    public static final String GET_ENTITIES_BY_FILTERS = "/";
    public static final String GET_ENTITY_BY_ID = "/:id";
    public static final String GET_ENTITY_IMAGE_BY_ID = "/image/:id";
    public static final String GET_TRACK_AUDIO = "/audio/:id";
    
    public static final String GET_COLLECTIONS = "/collections/";
    
    public static final String EDIT_ARTIST = "/artist/";
    public static final String EDIT_ARTWORK = "/artwork/";
    
    public static final String PUBLISH_ARCHIVE_ENTITY = "/publish-or-archive";
    
    public static final String REMOVE_ENTITY = "/";
    
    public static final String CREATE_NEW_GENRE = "/genres/";
    
    public static final String GET_ALL_GENRES = "/genres/";
    public static final String GET_GENRE_BY_TITLE = "/genres/:title";
    public static final String GET_GENRE_BY_ID = "/genres/:id";
    
    public static final String EDIT_GENRE = "/genres/";
    
    public static final String REMOVE_GENRE = "/genres/";
    
    public static final String PLAYED_TRACK = "/played-track";
    
    public static final String CREATE_PLAYLIST = "/playlist/";
    public static final String EDIT_PLAYLIST = "/playlist/";
    public static final String GET_PLAYLIST_BY_ID = "/playlist/:id";
    public static final String GET_PLAYLISTS_BY_PLAYER_ID = "/playlist/";
    public static final String REMOVE_PLAYLIST = "/playlist/";
    
    
}
