/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.net.*;
import java.util.stream.*;
import com.google.gson.*;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.jamapplicationserver.modules.notification.domain.*;
import com.jamapplicationserver.modules.notification.infra.services.exceptions.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 * Domain service for communicating with FCM, Email, and SMS APIs
 * @author dada
 */
public class NotificationService implements INotificationService {
    
    private NotificationService() {
        try {
            String filePath = "./src/main/resources/Config/jamusic-5fd24-firebase-adminsdk-55r1y-d460748557.json";
            final GoogleCredentials credentials =
                    GoogleCredentials.fromStream(new FileInputStream(filePath));
            final FirebaseOptions options =
                    new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch(Exception e) {
            LogService.getInstance().error(e);
        }
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
                    sendFCM(notification);
                    break;
                case EMAIL:
                    sendEmail(notification);
                    break;
                case SMS:
                    sendSMS(notification);
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
        return true;
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
    
    private void sendFCM(Notification notification) throws NotificationCannotBeSentException {
        try {
            
            List<String> registrationTokens =
                    notification
                    .getRecipients()
                    .stream()
                    .map(n -> n.getFCMToken())
                    .collect(Collectors.toList());
            
            final List<com.google.firebase.messaging.Message> messages =
                    registrationTokens
                    .stream()
                    .map(token -> {
                        return com.google.firebase.messaging.Message.builder()
                        .setNotification(
                                com.google.firebase.messaging.Notification.builder()
                                .setTitle(notification.getTitle().getValue())
                                .setBody(notification.getMessage() != null ? notification.getMessage().getValue() : null)
                                .build()
                        )
                        .setToken(token)
                        .build();
                    })
                    .collect(Collectors.toList());

            FirebaseMessaging.getInstance().sendAll(messages);
            
        } catch(FirebaseMessagingException e) {
            LogService.getInstance().error(e);
            throw new NotificationCannotBeSentException(e);
        } catch(Exception e) {
            LogService.getInstance().error(e);
        }
    }
    
    private void sendEmail(Notification notification) throws NotificationCannotBeSentException {
                
        try {
        
            final Properties props = new Properties();
            props.put("mail.smtp.host", "mail.jamusicapp.ir");
            props.put("mail.smtp.port", "2525");
            
            final SmtpAuthenticator authenticator = new SmtpAuthenticator();
            
            final Session session = Session.getDefaultInstance(props, authenticator);
            final javax.mail.Message message = new MimeMessage(session);
            
            final InternetAddress from = new InternetAddress("mail@jamusicapp.ir");
            message.setFrom(from);
            
            final String subject = notification.getTitle().getValue();
            message.setSubject(subject);
            
            final String bodyMessage = notification.getMessage() != null ? notification.getMessage().getValue() : "";
            final MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(bodyMessage, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            
            final Address[] recipients = new InternetAddress[notification.getRecipients().size()];
            for(int i = 0; i < notification.getRecipients().size(); i++) {
                final Address recipient = new InternetAddress("amirh715@gmail.com");
                recipients[i] = recipient;
            }
            message.setRecipients(javax.mail.Message.RecipientType.TO, recipients);
            
            Transport.send(message);
        
        } catch(Exception e) {
            LogService.getInstance().error(e);
            throw new NotificationCannotBeSentException(e);
        }
        
        
    }
    
    private void sendSMS(Notification notification) throws NotificationCannotBeSentException {
        final Gson gson = new Gson();
        HttpURLConnection conn = null;
        try {
            URL url = new URL("https://console.melipayamak.com/api/send/advanced/84fe9126365244368709a1422f281926");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true); conn.setDoInput(true);
            final List<String> recipients =
                    notification
                    .getRecipients()
                    .stream()
                    .map(recipient -> recipient.getMobile())
                    .collect(Collectors.toList());
            final JsonObject json = new JsonObject();
            json.addProperty("from", "50004001974163");
            json.addProperty("to", gson.toJson(recipients));
            json.addProperty("text", notification.getMessage() != null ? notification.getMessage().getValue() : "");
            
            final String stringified =
                    json
                    .toString()
                    .replaceAll("\"\\[", "[").replaceAll("]\"","]")
                    .replaceAll("\\\\", "");
            
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            byte[] paramsAsByte = stringified.getBytes("utf-8");
            dos.write(paramsAsByte, 0, paramsAsByte.length);
            dos.flush(); dos.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String output;
            while((output = br.readLine()) != null) {
              sb.append(output);
            }

            conn.disconnect();
        }
        catch (Exception e) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            StringBuilder sb = new StringBuilder();
            String output = null;
            while(true) {
              try {
                if ((output = br.readLine()) == null) break;
              } catch (IOException ex) {
                ex.printStackTrace();
              }
              sb.append(output);
            }
            throw new NotificationCannotBeSentException(sb.toString());
        }
    }
    
    public static NotificationService getInstance() {
        return NotificationServiceHolder.INSTANCE;
    }
    
    private static class NotificationServiceHolder {

        private static final NotificationService INSTANCE = new NotificationService();
    }
}
