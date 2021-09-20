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
    
    public boolean isArtist() {
        return (type.equals("B") || type.equals("S"));
    }
    
    public boolean isSinger() {
        return type.equals("S");
    }
    
    public boolean isBand() {
        return type.equals("B");
    }
    
    public boolean isTrack() {
        return type.equals("T");
    }
    
    public boolean isAlbum() {
        return type.equals("A");
    }
    
    private LibraryEntityType(String type) {
        this.type = type;
    }
    
    public static final Result<LibraryEntityType> create(String value) {
        
        try {
            
            if(value == null) return Result.fail(new ValidationError("Library entity type is required"));
            
            final LibraryEntityType type = LibraryEntityType.valueOf(value.toUpperCase());
            
            if(type == null) return Result.fail(new ValidationError("Library entity type is invalid"));
            
            return Result.ok(type);
            
        } catch(IllegalArgumentException e) {
            return Result.fail(new ValidationError("Library entity type is invalid"));
        }
        
    }
    
}
