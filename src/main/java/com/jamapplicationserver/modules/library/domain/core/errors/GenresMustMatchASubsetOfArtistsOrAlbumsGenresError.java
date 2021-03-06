/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core.errors;

import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class GenresMustMatchASubsetOfArtistsOrAlbumsGenresError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "سبک اثر باید زیرمجموعه ای از سبک های هنرمند آن اثر باشد";
    private static final int CODE = 314;
    
    public GenresMustMatchASubsetOfArtistsOrAlbumsGenresError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public GenresMustMatchASubsetOfArtistsOrAlbumsGenresError(String message) {
        super(message, CODE);
    }
    
    public GenresMustMatchASubsetOfArtistsOrAlbumsGenresError(String message, String description) {
        super(message, CODE, description);
    }
    
}
