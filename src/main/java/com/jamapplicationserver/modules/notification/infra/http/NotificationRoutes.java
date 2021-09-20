/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.http;

import spark.RouteGroup;
import static spark.Spark.*;
import com.jamapplicationserver.modules.notification.usecases.CreateNotification.CreateNotificationController;

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
        
        
        
        // get app notifications by recipient id
        
        
        
        // get notification by id
        
        
        
        // edit notification
        
        
        
        // mark app notification as delivered
        
        
        
        // remove notification
        
        
    }
    
    public static NotificationRoutes getInstance() {
        return NotificationRoutesHolder.INSTANCE;
    }
    
    private static class NotificationRoutesHolder {

        private static final NotificationRoutes INSTANCE = new NotificationRoutes();
    }
}
