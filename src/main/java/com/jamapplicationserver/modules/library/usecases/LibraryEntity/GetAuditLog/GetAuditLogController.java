/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetAuditLog;

/**
 *
 * @author dada
 */
public class GetAuditLogController {
    
    private GetAuditLogController() {
    }
    
    public static GetAuditLogController getInstance() {
        return GetAuditLogControllerHolder.INSTANCE;
    }
    
    private static class GetAuditLogControllerHolder {

        private static final GetAuditLogController INSTANCE = new GetAuditLogController();
    }
}
