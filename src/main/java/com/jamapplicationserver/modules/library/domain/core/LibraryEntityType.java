/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public enum LibraryEntityType {
    
    S("S"), B("B"), A("A"), T("T");
    
    private final String type;

    public String getValue() {
        return this.type;
    }
    
    private LibraryEntityType(String type) {
        this.type = type;
    }
    
    public static final Result<LibraryEntityType> create(String value) {
        if(value == null)
            return Result.fail(new ValidationError("Library entity type is required"));
        
        final LibraryEntityType type = LibraryEntityType.valueOf(value);
        
        if(type == null)
            return Result.fail(new ValidationError("Library entity type is incorrect"));
        
        return Result.ok(type);
    }
    
}
