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
public class MaxAllowedPlaylistsExceededError extends ConflictError {
    
    private static final String DEFAULT_MESSAGE = "حداکثر تعداد مجاز پلی لیست ها ۱۵۰ عدد است";
    private static final int CODE = 320;
    
    public MaxAllowedPlaylistsExceededError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public MaxAllowedPlaylistsExceededError(String message) {
        super(message, CODE);
    }
    
    public MaxAllowedPlaylistsExceededError(String message, String description) {
        super(message, CODE, description);
    } 
    
}
