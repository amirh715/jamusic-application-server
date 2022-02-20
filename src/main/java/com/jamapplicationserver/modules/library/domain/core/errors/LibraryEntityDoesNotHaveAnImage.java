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
public class LibraryEntityDoesNotHaveAnImage extends NotFoundError {
    
    private static final String DEFAULT_MESSAGE = "عکس ندارد";
    private static final int CODE = 317;
    
    public LibraryEntityDoesNotHaveAnImage() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public LibraryEntityDoesNotHaveAnImage(String message) {
        super(message, CODE);
    }
    
    public LibraryEntityDoesNotHaveAnImage(String message, String description) {
        super(message, CODE, description);
    }
    
}
