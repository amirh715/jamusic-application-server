/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.filesystem.errors;

import com.jamapplicationserver.core.logic.ClientErrorError;

/**
 *
 * @author amirhossein
 */
public class FileInvalidError extends ClientErrorError {
    
    private static final int CODE = 111;
    private static final String DEFAULT_MESSAGE = "فرمت فایل قابل قبول نیست یا حجم آن بیش از بیست مگابایت است";
    
    public FileInvalidError() {
        super(DEFAULT_MESSAGE, CODE);
    }
    
    public FileInvalidError(String message) {
        super(message, CODE);
    }
    
    public FileInvalidError(String message, String description) {
        super(message, CODE, description);
    }
    
}
