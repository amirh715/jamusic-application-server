/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.infra;

import java.lang.reflect.Type;
import com.google.gson.*;
import com.jamapplicationserver.core.logic.BusinessError;

/**
 *
 * @author dada
 */
public class ErrorSerializer implements JsonSerializer<BusinessError> {
    
    @Override
    public JsonElement serialize(BusinessError error, Type type, JsonSerializationContext context) {

        JsonObject json = new JsonObject();
        
        json.add("code", new JsonPrimitive(error.code));
        json.add("type", new JsonPrimitive(error.type));
        json.add("message", new JsonPrimitive(error.message));
        json.add("description", new JsonPrimitive(error.description));
        
        return json;
    }
    
}
