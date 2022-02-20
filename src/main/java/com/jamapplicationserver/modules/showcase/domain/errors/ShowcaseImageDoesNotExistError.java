/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.domain.errors;

import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class ShowcaseImageDoesNotExistError extends NotFoundError {
    
    private static final String DEFAULT_MESSAGE = "آیتم ویترین عکس ندارد.";
    private static final int CODE = 602;
    
    public ShowcaseImageDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public ShowcaseImageDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public ShowcaseImageDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
    
}
