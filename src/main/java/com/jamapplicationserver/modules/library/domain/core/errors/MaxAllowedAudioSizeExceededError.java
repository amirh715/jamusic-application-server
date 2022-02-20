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
public class MaxAllowedAudioSizeExceededError extends ClientErrorError {
    
    private static final String DEFAULT_MESSAGE = "حداکثر حجم فایل صوتی ۲۰ مگابایت است";
    private static final int CODE = 318;
    
    public MaxAllowedAudioSizeExceededError() {
        super(DEFAULT_MESSAGE, CODE);
    }

    public MaxAllowedAudioSizeExceededError(String message) {
        super(message, CODE);
    }
    
    public MaxAllowedAudioSizeExceededError(String message, String description) {
        super(message, CODE, description);
    }
    
}
