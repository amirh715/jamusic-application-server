/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.GetNotificationsByFilters;

/**
 *
 * @author dada
 */
public class GetNotificationsByFiltersController {
    
    private GetNotificationsByFiltersController() {
    }
    
    public static GetNotificationsByFiltersController getInstance() {
        return GetNotificationsByFiltersControllerHolder.INSTANCE;
    }
    
    private static class GetNotificationsByFiltersControllerHolder {

        private static final GetNotificationsByFiltersController INSTANCE = new GetNotificationsByFiltersController();
    }
}
