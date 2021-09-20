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
public class GetNotificationsByFiltersUseCase {
    
    private GetNotificationsByFiltersUseCase() {
    }
    
    public static GetNotificationsByFiltersUseCase getInstance() {
        return GetNotificationsByFiltersUseCaseHolder.INSTANCE;
    }
    
    private static class GetNotificationsByFiltersUseCaseHolder {

        private static final GetNotificationsByFiltersUseCase INSTANCE = new GetNotificationsByFiltersUseCase();
    }
}
