/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.services;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.stream.*;
import com.jamapplicationserver.modules.notification.infra.services.SmtpAuthenticator;
import com.jamapplicationserver.modules.notification.domain.*;
import com.jamapplicationserver.modules.notification.infra.services.exceptions.*;

/**
 * Domain service for communicating with FCM, Email, and SMS APIs
 * @author dada
 */
public class NotificationService implements INotificationService {
    
    private NotificationService() {
    }
    
    /**
     * Send notification data to recipients using the proper third-party clients
     * based on notification type
     * @param notification 
     * @throws com.jamapplicationserver.modules.notification.infra.services.exceptions.NotificationCannotBeSentException 
     */
    @Override
    public void send(Notification notification) throws NotificationCannotBeSentException {
        
        try {
            
            if(!notification.isOnSchedule())
                throw new NotificationCannotBeSentException("Notification is not on schedule");
            
            if(notification.isSent())
                throw new NotificationCannotBeSentException("Notification is already sent");
            
            if(!canSend(notification))
                throw new NotificationCannotBeSentException("Third-party won't send this notification");
            
            // call third-party service and send
            final NotifType type = notification.getType();
            switch (type) {
                case FCM:
                    break;
                case EMAIL:
                    sendEmail(notification);
                    break;
                case SMS:
                    break;
                default:
                    break;
            }
            
            notification.markAsSent();
            
        } catch(Exception e) {
            throw new NotificationCannotBeSentException(e);
        }
        
    }

    /**
     * Check whether the third-party service can send this notification
     * @param notification
     * @return 
     */
    @Override
    public boolean canSend(Notification notification) {
        
        try {
            
            
            
            return true;
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    /**
     * Check whether the notification is delivered by the third-party service to
     * the recipient
     * @param notification
     * @param recipient
     * @return 
     */
    @Override
    public boolean isDeliveredToRecipient(Notification notification, Recipient recipient) {
        
        try {
            
            if(notification.isDelivered(recipient))
                return false;
            
            // call third-party service and check
            
            // notification.markAsDelivered(recipient, deliveredAt);
            
            return false;
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public Map<Recipient, Boolean> getAllDeliveriesOfNotification(Notification notification) {
        
        return Map.of();
    }
    
    private void sendFCM() {
        
    }
    
    private void sendEmail(Notification notification) throws NotificationCannotBeSentException {
        
        System.out.println("send");
        
        try {
        
            final Properties props = new Properties();
            props.put("mail.smtp.host", "mail.jamusicapp.ir");
            
            final SmtpAuthenticator authenticator = new SmtpAuthenticator();
            
            final Session session = Session.getDefaultInstance(props, authenticator);
            final javax.mail.Message message = new MimeMessage(session);
            
            final InternetAddress from = new InternetAddress("mail@jamusicapp.ir");
            message.setFrom(from);
            
            final String subject = notification.getTitle().getValue();
            message.setSubject(subject);
            
            final String bodyMessage = notification.getMessage().getValue();
            final MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(bodyMessage, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            
            final Address[] recipients = new InternetAddress[notification.getRecipients().size()];
            for(int i = 0; i < notification.getRecipients().size(); i++) {
                final Address recipient = new InternetAddress("dada@jamusicapp.ir");
                recipients[i] = recipient;
            }
            message.setRecipients(javax.mail.Message.RecipientType.TO, recipients);
            
            Transport.send(message);
        
        } catch(Exception e) {
            throw new NotificationCannotBeSentException(e);
        }
        
        
    }
    
    private void sendSMS() {
        
    }
    
    public static NotificationService getInstance() {
        return NotificationServiceHolder.INSTANCE;
    }
    
    private static class NotificationServiceHolder {

        private static final NotificationService INSTANCE = new NotificationService();
    }
}
