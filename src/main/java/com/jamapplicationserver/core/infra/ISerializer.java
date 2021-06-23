/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.infra;

import java.util.*;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author dada
 */
public interface ISerializer {
    
    String serialize(Object object);
    
    <T> T deserialize(String string, TypeToken typeToken);
    
}
