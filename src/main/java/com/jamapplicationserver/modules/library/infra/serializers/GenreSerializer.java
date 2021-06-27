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
public class GenreSerializer implements JsonSerializer<Genre> {
    
    @Override
    public JsonElement serialize(Genre genre, Type type, JsonSerializationContext context) {
        
        JsonObject json = new JsonObject();
        
        json.add("id", new JsonPrimitive(
                genre.id.toString()
        ));
        json.add("title", new JsonPrimitive(
                genre.getTitle().getValue()
        ));
        json.add("titleInPersian", new JsonPrimitive(
                genre.getTitleInPersian().getValue()
        ));
        json.add("createdAt", new JsonPrimitive(
                genre.getCreatedAt().toJalali()
        ));
        json.add("lastModifiedAt", new JsonPrimitive(
                genre.getLastModifiedAt().toJalali()
        ));
        json.add("size", new JsonPrimitive(genre.getSize()));
        json.add("isRoot", new JsonPrimitive(genre.isRoot()));
        
        if(genre.getParent() != null) {
            final JsonElement parentGenre = this.serialize(genre.getParent(), type, context);
            json.add("parentGenre", parentGenre);
        }
        
        return json;
    }
    
}
