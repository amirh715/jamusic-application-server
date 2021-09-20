/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.serializers;

import java.lang.reflect.Type;
import com.google.gson.*;
import com.jamapplicationserver.modules.library.domain.Band.Band;

/**
 *
 * @author dada
 */
public class BandSerializer implements JsonSerializer<Band> {
    
    @Override
    public JsonElement serialize(Band band, Type type, JsonSerializationContext context) {
        
        final JsonObject json = new JsonObject();
        
        json.add("id", new JsonPrimitive(band.id.toString()));
        json.add("title", new JsonPrimitive(band.getTitle().getValue()));
        json.add("description", new JsonPrimitive(band.getDescription().getValue()));
        
        
        
        return json;
    }
    
}
