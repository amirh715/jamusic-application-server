/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.GetNotificationById;

/**
 *
 * @author dada
 */
public class GetNotificationByIdUseCase {
    
    private GetNotificationByIdUseCase() {
    }
    
    public static GetNotificationByIdUseCase getInstance() {
        return GetNotificationByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetNotificationByIdUseCaseHolder {

        private static final GetNotificationByIdUseCase INSTANCE = new GetNotificationByIdUseCase();
    }
}
