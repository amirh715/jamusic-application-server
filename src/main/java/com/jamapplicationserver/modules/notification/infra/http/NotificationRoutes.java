/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.http;

import spark.RouteGroup;
import static spark.Spark.*;
import com.jamapplicationserver.modules.notification.usecases.CreateNotification.CreateNotificationController;
import com.jamapplicationserver.modules.notification.usecases.GetNotificationsByFilters.GetNotificationsByFiltersController;
import com.jamapplicationserver.modules.notification.usecases.GetNotificationById.GetNotificationByIdController;
import com.jamapplicationserver.modules.notification.usecases.GetNotificationsOfRecipient.GetNotificationsOfRecipientController;
import com.jamapplicationserver.modules.notification.usecases.EditNotification.EditNotificationController;
import com.jamapplicationserver.modules.notification.usecases.RemoveNotification.RemoveNotificationController;

/**
 *
 * @author dada
 */
public class NotificationRoutes implements RouteGroup {
    
    private NotificationRoutes() {
    }
    
    @Override
    public void addRoutes() {
        
        // create new notification
        post(
                NotificationPaths.CREATE_NOTIFICATION,
                CreateNotificationController.getInstance()
        );

        // get notifications by filters
        get(
                NotificationPaths.GET_NOTIFICATIONS_BY_FILTERS,
                GetNotificationsByFiltersController.getInstance()
        );
        
        // get app notifications by recipient id
        get(
                NotificationPaths.GET_NOTIFICATIONS_OF_RECIPIENT,
                GetNotificationsOfRecipientController.getInstance()
        );

        // get notification by id
        get(
                NotificationPaths.GET_NOTIFICATION_BY_ID,
                GetNotificationByIdController.getInstance()
        );
        
        // edit notification
        put(
                NotificationPaths.EDIT_NOTIFICATION,
                EditNotificationController.getInstance()
        );
        
        // remove notification
        delete(
                NotificationPaths.REMOVE_NOTIFICATION,
                RemoveNotificationController.getInstance()
        );
        
    }
    
    public static NotificationRoutes getInstance() {
        return NotificationRoutesHolder.INSTANCE;
    }
    
    private static class NotificationRoutesHolder {

        private static final NotificationRoutes INSTANCE = new NotificationRoutes();
    }
}
