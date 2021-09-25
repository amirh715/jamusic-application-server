/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.serializers;

import java.lang.reflect.Type;
import com.google.gson.*;
import com.jamapplicationserver.modules.library.domain.Track.Track;

/**
 *
 * @author dada
 */
public class TrackSerializer implements JsonSerializer<Track> {
    
    @Override
    public JsonElement serialize(Track track, Type type, JsonSerializationContext context) {
        
        final JsonObject json = new JsonObject();
        
        json.add("id", new JsonPrimitive(track.id.toString()));
        json.add("title", new JsonPrimitive(track.getTitle().getValue()));
//        json.add("description",
//                new JsonPrimitive(
//                        track.getDescription() != null ?
//                                track.getDescription().getValue()
//                                : null
//                )
//        );

        json.add("published", new JsonPrimitive(track.isPublished()));
        
        
        return json;
    }
    
}
