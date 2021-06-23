/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.serializers;

import java.lang.reflect.Type;
import com.google.gson.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.infra.DTOs.entities.*;

/**
 *
 * @author dada
 */
public class GenreSerializer implements JsonSerializer<Genre> {
    
    @Override
    public JsonElement serialize(Genre genre, Type type, JsonSerializationContext context) {
        
        JsonObject json = new JsonObject();
        
        json.add("title", new JsonPrimitive(
                genre.getTitleInPersian().getValue())
        );
        json.add("parentGenre",
                new JsonPrimitive(
                        genre.getParent().getTitleInPersian().getValue()
                )
        );
        
        return json;
    }
    
}
