/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.domain;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;
import java.net.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.notification.domain.errors.*;

/**
 *
 * @author amirhossein
 */
public abstract class Notification extends AggregateRoot {
    
    protected static final int MAX_ALLOWED_RECIPIENTS = 5000;
    
    protected NotifTitle title;
    
    protected Message message;
       
    protected URL route;
    
    protected final SenderType senderType;
    
    protected DateTime sentAt;
    
    protected boolean isSent;
    
    protected DateTime scheduledOn;
    
    protected final UniqueEntityId senderId;
    
    protected final Map<Recipient, NotificationDelivery> deliveries;
    
    // creation constructor
    protected Notification(
            NotifTitle title,
            Message message,
            URL route,
            SenderType senderType,
            DateTime scheduledOn,
            UniqueEntityId senderId
    ) {
        super();
        this.title = title;
        this.message = message;
        this.route = route;
        this.senderType = senderType;
        this.scheduledOn = scheduledOn;
        this.isSent = false;
        this.senderId = senderId;
        this.deliveries = new HashMap<>();
    }
    
    // reconstitution constructor
    protected Notification(
            UniqueEntityId id,
            NotifTitle title,
            Message message,
            URL route,
            SenderType senderType,
            DateTime scheduledOn,
            UniqueEntityId senderId,
            DateTime sentAt,
            boolean isSent,
            DateTime createdAt,
            DateTime lastModifiedAt,
            Map<Recipient, NotificationDelivery> deliveries
    ) {
        super(id, createdAt, lastModifiedAt);
        this.title = title;
        this.message = message;
        this.route = route;
        this.senderType = senderType;
        this.scheduledOn = scheduledOn;
        this.senderId = senderId;
        this.sentAt = sentAt;
        this.isSent = isSent;
        this.deliveries = deliveries;
    }
    
    public NotifTitle getTitle() {
        return this.title;
    }
    
    public Message getMessage() {
        return this.message;
    }
    
    public URL getRoute() {
        return this.route;
    }
    
    public boolean isBulk() {
        return this.getRecipients().size() > 1;
    }
    
    public DateTime getSentAt() {
        return this.sentAt;
    }
    
    public SenderType getSenderType() {
        return this.senderType;
    }
    
    public DateTime getScheduledOn() {
        return this.scheduledOn;
    }
    
    public UniqueEntityId getSenderId() {
        return this.senderId;
    }
    
    public Set<Recipient> getRecipients() {
        return this.deliveries.keySet();
    }
    
    public Map<Recipient, NotificationDelivery> getDeliveries() {
        return this.deliveries;
    }
    
    public final Result addRecipient(Recipient recipient) {
        
        if(this.isSent())
            return Result.fail(new NotificationIsAlreadySentError());
        
        if(this.getRecipients().size() >= MAX_ALLOWED_RECIPIENTS)
            return Result.fail(new MaxAllowedRecipientsExceededError());
            
        final NotificationDelivery delivery = NotificationDelivery.create(recipient);
        this.deliveries.put(recipient, delivery);
        
        this.modified();
        
        return Result.ok();
    }
    
    public final void removeRecipient(Recipient recipient) {
        
        if(this.isSent()) return;
        
        this.deliveries.remove(recipient);
        
        this.modified();
        
    }
    
    public final boolean hasRecipient(Recipient recipient) {
        return this.deliveries.containsKey(recipient);
    }
    
    public final void markAsDelivered(
            Recipient recipient,
            DateTime deliveredAt
    ) {
        
        if(!this.isSent()) return;
        
        final NotificationDelivery delivery =
                NotificationDelivery.create(recipient, deliveredAt, true);
        
        this.deliveries.replace(recipient, delivery);
        
        this.modified();
    }
    
    public final void markAsSent() {
        if(this.isSent()) return;
        if(!this.isOnSchedule()) return;
        this.sentAt = DateTime.createNow();
        this.isSent = true;
        this.modified();
        // domain event ??
    }
    
    public boolean isOnSchedule() {
        return this.scheduledOn.getValue().isBefore(DateTime.createNow().getValue());
    }
    
    public final boolean isDelivered(Recipient recipient) {
        return this.deliveries.get(recipient).isDelivered(); 
    }
    
    public final boolean isSent() {
        return this.isSent;
    }
    
    public Result edit(
            NotifTitle title,
            NotifType type,
            Message message,
            URL route,
            DateTime scheduledOn
    ) {
        
        if(this.isSent())
            return Result.fail(new NotificationIsAlreadySentError());
        
        this.title = title;
        this.message = message;
        this.route = route;
        this.scheduledOn = scheduledOn;
        
        this.modified();
        
        return Result.ok();
    }
    
    public static final Result<Notification> create(
            NotifType type,
            NotifTitle title,
            Message message,
            URL route,
            SenderType senderType,
            DateTime scheduledOn,
            UniqueEntityId senderId
    ) {
        
        if(type == null)
            return Result.fail(new ValidationError("Notification type is required"));
        if(senderType == null)
            return Result.fail(new ValidationError("Notification sender type is required"));
        if(scheduledOn == null)
            return Result.fail(new ValidationError("Notification needs to be scheduled"));
        if(senderId == null)
            return Result.fail(new ValidationError("Notification sender id is required"));
        
        Notification entity;
        
        switch(type) {
            case APP:
                
                entity = new AppNotification(
                        title,
                        message,
                        route,
                        senderType,
                        scheduledOn,
                        senderId
                );
                
                break;
            case SMS:
                
                entity = new SMSNotification(
                        title,
                        message,
                        route,
                        senderType,
                        scheduledOn,
                        senderId
                );
                
                break;
            case FCM:
                
                entity = new FCMNotification(
                        title,
                        message,
                        route,
                        senderType,
                        scheduledOn,
                        senderId
                );
                
                break;
            case EMAIL:
                
                entity = new EmailNotification(
                        title,
                        message,
                        route,
                        senderType,
                        scheduledOn,
                        senderId
                );
                
                break;
            default:
                return Result.fail(new ValidationError("Notification type is unknown"));
        }
        
        return Result.ok(entity);
    }

    public static final Result<Notification> reconstitute(
            String type,
            UUID id,
            String title,
            String message,
            String route,
            String senderType,
            LocalDateTime sentAt,
            boolean isSent,
            LocalDateTime scheduledOn,
            UUID senderId,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            Map<Recipient, NotificationDelivery> deliveries
    ) {
        
        Notification entity;
        
        final ArrayList<Result> combinedProps = new ArrayList<>();
        
        final Result<NotifType> typeOrError = NotifType.create(type);
        final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromUUID(id);
        final Result<NotifTitle> titleOrError = NotifTitle.create(title);
        final Result<Message> messageOrError = Message.create(message);
        final Result<SenderType> senderTypeOrError = SenderType.create(senderType);
        final Result<DateTime> sentAtOrError = DateTime.create(sentAt);
        final Result<DateTime> scheduledOnOrError = DateTime.create(scheduledOn);
        final Result<UniqueEntityId> senderIdOrError = UniqueEntityId.createFromUUID(senderId);
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        final Result<DateTime> lastModifiedAtOrError = DateTime.create(lastModifiedAt);
        
        combinedProps.add(typeOrError);
        combinedProps.add(idOrError);
        combinedProps.add(senderTypeOrError);
        combinedProps.add(scheduledOnOrError);
        combinedProps.add(senderIdOrError);
        combinedProps.add(createdAtOrError);
        combinedProps.add(lastModifiedAtOrError);
        
        if(title != null) combinedProps.add(titleOrError);
        if(message != null) combinedProps.add(messageOrError);
        if(sentAt != null) combinedProps.add(sentAtOrError);
        
        final Result combinedPropsResult = Result.combine(combinedProps);
        if(combinedPropsResult.isFailure) return combinedPropsResult;
        
        URL routeURL = null;
        try {
            if(route != null) routeURL = new URL(route);
        } catch(MalformedURLException e) {
            return Result.fail(new ValidationError("Route is invalid"));
        }
        
        switch(typeOrError.getValue()) {
            case APP:
                
                entity =
                        new AppNotification(
                                idOrError.getValue(),
                                titleOrError.getValue(),
                                messageOrError.getValue(),
                                routeURL,
                                senderTypeOrError.getValue(),
                                scheduledOnOrError.getValue(),
                                senderIdOrError.getValue(),
                                sentAtOrError.getValue(),
                                isSent,
                                createdAtOrError.getValue(),
                                lastModifiedAtOrError.getValue(),
                                deliveries
                        );
                
            break;
            case SMS:
                
                entity =
                        new SMSNotification(
                                idOrError.getValue(),
                                titleOrError.getValue(),
                                messageOrError.getValue(),
                                routeURL,
                                senderTypeOrError.getValue(),
                                scheduledOnOrError.getValue(),
                                senderIdOrError.getValue(),
                                sentAtOrError.getValue(),
                                isSent,
                                createdAtOrError.getValue(),
                                lastModifiedAtOrError.getValue(),
                                deliveries
                        );
                
            break;
            case FCM:
                
                entity =
                        new FCMNotification(
                                idOrError.getValue(),
                                titleOrError.getValue(),
                                messageOrError.getValue(),
                                routeURL,
                                senderTypeOrError.getValue(),
                                scheduledOnOrError.getValue(),
                                senderIdOrError.getValue(),
                                sentAtOrError.getValue(),
                                isSent,
                                createdAtOrError.getValue(),
                                lastModifiedAtOrError.getValue(),
                                deliveries
                        );
                
            break;
            case EMAIL:
                
                entity =
                        new EmailNotification(
                                idOrError.getValue(),
                                titleOrError.getValue(),
                                messageOrError.getValue(),
                                routeURL,
                                senderTypeOrError.getValue(),
                                scheduledOnOrError.getValue(),
                                senderIdOrError.getValue(),
                                sentAtOrError.getValue(),
                                isSent,
                                createdAtOrError.getValue(),
                                lastModifiedAtOrError.getValue(),
                                deliveries
                        );
                
            break;
            default:
                entity = null;
        }
        
        return Result.ok(entity);
    }
    
}
