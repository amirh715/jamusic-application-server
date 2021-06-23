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
    public final Collection<Object> deserialize(String string, TypeToken typeToken) {
        
        return gson.fromJson(string, typeToken.getType());
        
    }
    
    public static Serializer getInstance() {
        return SerializerHolder.INSTANCE;
    }
    
    private static class SerializerHolder {

        private static final Serializer INSTANCE = new Serializer(
            new GsonBuilder()
            .setExclusionStrategies(new GsonExclusionStrategy(Path.class))
            .registerTypeAdapter(User.class, new UserSerializer())
            .registerTypeAdapter(BusinessError.class, new ErrorSerializer())
            .create()
        );
    }
}
