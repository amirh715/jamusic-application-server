/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.Player;

import com.jamapplicationserver.core.domain.ValueObject;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class PlayerRole extends ValueObject {
    
    private final String value;
    
    private PlayerRole(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public static final Result<PlayerRole> create(String value) {
        
        if(value == null) return Result.fail(new ValidationError("Player role is required"));
        
        
        return Result.ok(new PlayerRole(value));
    }
    
}
