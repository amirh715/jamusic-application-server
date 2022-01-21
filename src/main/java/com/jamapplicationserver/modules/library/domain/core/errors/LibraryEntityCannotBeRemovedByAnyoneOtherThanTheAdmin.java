/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core.errors;

import com.jamapplicationserver.core.logic.ClientErrorError;

/**
 *
 * @author dada
 */
public class LibraryEntityCannotBeRemovedByAnyoneOtherThanTheAdmin extends ClientErrorError {
    
    private static final String DEFAULT_MESSAGE = "تنها ادمین می تواند هنرمندان و آثار را پاک کند.";
    private static final int CODE = 111;
    
    public LibraryEntityCannotBeRemovedByAnyoneOtherThanTheAdmin() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public LibraryEntityCannotBeRemovedByAnyoneOtherThanTheAdmin(String message) {
        super(message, CODE);
    }
    
    public LibraryEntityCannotBeRemovedByAnyoneOtherThanTheAdmin(String message, String description) {
        super(message, CODE, description);
    }
    
}
