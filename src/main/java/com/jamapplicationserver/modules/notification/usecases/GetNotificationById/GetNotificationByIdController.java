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
public class GetNotificationByIdController {
    
    private GetNotificationByIdController() {
    }
    
    public static GetNotificationByIdController getInstance() {
        return GetNotificationByIdControllerHolder.INSTANCE;
    }
    
    private static class GetNotificationByIdControllerHolder {

        private static final GetNotificationByIdController INSTANCE = new GetNotificationByIdController();
    }
}
