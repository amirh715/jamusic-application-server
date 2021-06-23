/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.serializers;

import java.lang.reflect.Type;
import com.google.gson.*;
import com.jamapplicationserver.modules.user.domain.*;

/**
 *
 * @author amirhossein
 */
public class UserSerializer implements JsonSerializer<User> {
    
    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext context) {
        
        JsonObject json = new JsonObject();
        
        final String email = user.getEmail()!= null ? user.getEmail().getValue() : null;
        
        json.add("id", new JsonPrimitive(user.id.toString()));
        json.add("name", new JsonPrimitive(user.getName().getValue()));
        json.add("mobile", new JsonPrimitive(user.getMobile().getValue()));
        json.add("state", new JsonPrimitive(user.getState().getValueInPersian()));
        json.add("role", new JsonPrimitive(user.getRole().getValueInPersian()));
//        json.add("email", email != null ? new JsonPrimitive(email) : null);
        json.add("emailVerified", new JsonPrimitive(user.isEmailVerified() ? "تایید شده" : "تایید نشده"));
        json.add("createdAt", new JsonPrimitive(user.getCreatedAt().toJalali()));
        json.add("lastModifiedAt", new JsonPrimitive(user.getLastModifiedAt().toJalali()));
//        json.add("creatorId", new JsonPrimitive(user.getCreatorId().toString()));
//        json.add("updaterId", new JsonPrimitive(user.getUpdaterId().toString()));
        json.add("createdAtDuration", new JsonPrimitive(user.getCreatedAt().diffFromNow()));
        json.add("lastModifiedAtDuration", new JsonPrimitive(user.getLastModifiedAt().diffFromNow()));
        
        return json;
    }

}
