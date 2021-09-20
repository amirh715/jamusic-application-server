/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.infra;

import java.util.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.nio.file.Path;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.infra.serializers.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.infra.serializers.*;
import com.jamapplicationserver.modules.library.domain.Singer.Singer;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.modules.library.domain.Band.Band;
import com.jamapplicationserver.modules.reports.infra.serializers.ReportSerializer;
import com.jamapplicationserver.modules.reports.domain.*;


/**
 *
 * @author dada
 */
public class Serializer implements ISerializer {
    
    private final Gson gson;
    
    private Serializer(Gson gson) {
        this.gson = gson;
    }
    
    @Override
    public final String serialize(Object object) {
        
        return gson.toJson(object);
        
    }
    
    @Override
    public final Object deserialize(String string, TypeToken typeToken) {
        
        return gson.fromJson(string, typeToken.getType());
        
    }
    
    public static Serializer getInstance() {
        return SerializerHolder.INSTANCE;
    }
    
    private static class SerializerHolder {

        private static final Serializer INSTANCE = new Serializer(
            new GsonBuilder()
            .setExclusionStrategies(new GsonExclusionStrategy(Path.class))
//            .registerTypeAdapter(User.class, new UserSerializer())
//            .registerTypeAdapter(Artist.class, new ArtistSerializer())
//            .registerTypeAdapter(Album.class, new AlbumSerializer())
//            .registerTypeAdapter(Track.class, new TrackSerializer())
//            .registerTypeAdapter(Genre.class, new GenreSerializer())
//            .registerTypeAdapter(Report.class, new ReportSerializer())
            
            .registerTypeAdapter(BusinessError.class, new ErrorSerializer())
            .create()
        );
    }
}
