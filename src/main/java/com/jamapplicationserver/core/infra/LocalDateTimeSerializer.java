/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.infra;

import java.time.*;
import java.lang.reflect.Type;
import com.google.gson.*;
import com.jamapplicationserver.core.domain.DateTime;

/**
 *
 * @author dada
 */
public class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
    
    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(DateTime.createWithoutValidation(localDateTime).toJalali());
    }
    
}
