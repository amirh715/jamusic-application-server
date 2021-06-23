/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.serializers;

import java.lang.reflect.Type;
import com.google.gson.*;
import com.jamapplicationserver.modules.library.domain.core.*;

/**
 *
 * @author dada
 */
public class ArtistSerializer implements JsonSerializer<Artist> {
    
    @Override
    public JsonElement serialize(Artist artist, Type type, JsonSerializationContext context) {
        
        JsonObject json = new JsonObject();
        
        json.add("id", new JsonPrimitive(artist.id.toString()));
        json.add("title", new JsonPrimitive(artist.getTitle().getValue()));
        json.add("description", new JsonPrimitive(artist.getDescription().getValue()));
//        json.add("tags", new JsonObject);
//        json.add("genres", new JsonPrimitive());
        json.add("flagNote", new JsonPrimitive(artist.getFlag().getValue()));
        json.add("published", new JsonPrimitive(artist.isPublished() ? "منتشر شده" : "آرشیو شده"));
        json.add("createdAt", new JsonPrimitive(artist.getCreatedAt().toJalali()));
        json.add("lastModifiedAt", new JsonPrimitive(artist.getLastModifiedAt().toJalali()));
//        json.add("creatorId", new JsonPrimitive());
        json.add("totalPlayedCount", new JsonPrimitive(artist.getTotalPlayedCount()));
        json.add("rate", new JsonPrimitive(artist.getRate().getValue()));
        
        return json;
    }
    
}
