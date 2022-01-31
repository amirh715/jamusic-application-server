/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain.errors;

import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class OnlyOneAdminCanExistError extends ConflictError {

    private static final int CODE = 205;
    
    public OnlyOneAdminCanExistError() {
        super("تنها یک مدیر سیستم می تواند وجود داشته باشد", CODE);
    }
    
}
