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
public class ShowcaseDoesNotExistError extends NotFoundError {
    
    private static final String DEFAULT_MESSAGE = "آیتم ویترین وجود ندارد.";
    private static final int CODE = 601;
    
    public ShowcaseDoesNotExistError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public ShowcaseDoesNotExistError(String message) {
        super(message, CODE);
    }
    
    public ShowcaseDoesNotExistError(String message, String description) {
        super(message, CODE, description);
    }
    
    
}
