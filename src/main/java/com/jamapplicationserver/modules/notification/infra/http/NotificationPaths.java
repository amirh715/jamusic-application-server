/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.http;

/**
 *
 * @author dada
 */
public class NotificationPaths {
    
    public static final String CREATE_NOTIFICATION = "/";
    public static final String GET_NOTIFICATIONS_BY_FILTERS = "/";
    public static final String GET_NOTIFICATION_BY_ID = "/:id";
    public static final String GET_APP_NOTIFICATIONS_BY_RECIPIENT_ID = "/app/:recipientId";
    public static final String EDIT_NOTIFICATION = "/";
    public static final String MARK_APP_NOTIFICATION_AS_DELIVERED = "/app-delivered/:recipientId";
    public static final String REMOVE_NOTIFICATION = "/";
    
}
