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
public class PlaylistDoesNotExistError extends NotFoundError {
    
    private static final String DEFAULT_MESSAGE = "پلی لیست وجود ندارد";
    private static final int CODE = 324;
    
    public PlaylistDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public PlaylistDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public PlaylistDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
}
