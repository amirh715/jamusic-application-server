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
public class GetAuditLogUseCase {
    
    private GetAuditLogUseCase() {
    }
    
    public static GetAuditLogUseCase getInstance() {
        return GetAuditLogUseCaseHolder.INSTANCE;
    }
    
    private static class GetAuditLogUseCaseHolder {

        private static final GetAuditLogUseCase INSTANCE = new GetAuditLogUseCase();
    }
}
