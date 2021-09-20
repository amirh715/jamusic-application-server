/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database.Models;

import javax.persistence.*;

/**
 *
 * @author dada
 */
@Entity
@DiscriminatorValue("FCM")
public class FCMNotificationModel extends NotificationModel {
    
    public FCMNotificationModel() {
        
    }
    
}
