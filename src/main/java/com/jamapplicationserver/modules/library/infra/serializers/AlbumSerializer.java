/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.serializers;

import java.lang.reflect.Type;
import com.google.gson.*;
import com.jamapplicationserver.modules.library.domain.Album.Album;

/**
 *
 * @author dada
 */
public class AlbumSerializer implements JsonSerializer<Album> {
    
    @Override
    public JsonElement serialize(Album album, Type type, JsonSerializationContext context) {
        
        JsonObject json = new JsonObject();
        
        json.add("id", new JsonPrimitive(album.id.toString()));
        json.add("title", new JsonPrimitive(album.getTitle().getValue()));
        
        
        return json;
    }
    
}
