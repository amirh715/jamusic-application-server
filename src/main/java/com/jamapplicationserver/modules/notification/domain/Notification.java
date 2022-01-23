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
        this.scheduledOn = scheduledOn != null ? scheduledOn : DateTime.createNow();
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
        return getRecipients().size() > 1;
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
    
    public Set<NotificationDelivery> getDeliveries() {
        return this.deliveries.values().stream().collect(Collectors.toSet());
    }
    
    public boolean hasRemainingDeliveries() {
        return this.deliveries.values()
                .stream()
                .filter(d -> !d.isDelivered())
                .count() > 0;
    }
    
    public NotifType getType() {
        if(this instanceof FCMNotification)
            return NotifType.FCM;
        if(this instanceof EmailNotification)
            return NotifType.EMAIL;
        if(this instanceof SMSNotification)
            return NotifType.SMS;
        else
            return null;
    }
    
    public final Result addRecipient(Recipient recipient) {
        
        if(isSent())
            return Result.fail(new NotificationIsAlreadySentError());
        
        if(getRecipients().size() >= MAX_ALLOWED_RECIPIENTS)
            return Result.fail(new MaxAllowedRecipientsExceededError());
        
        switch(getType()) {
            case FCM:
                if(!recipient.hasFCMToken())
                    return Result.fail(new RecipientIsNotEligibleForThisNotificationTypeError());
                break;
            case EMAIL:
                if(!recipient.hasEmailAddress())
                    return Result.fail(new RecipientIsNotEligibleForThisNotificationTypeError());
                if(!recipient.isEmailVerified())
                    return Result.fail(new RecipientIsNotEligibleForThisNotificationTypeError());
                break;
            case SMS:
                if(!recipient.hasMobileNo())
                    return Result.fail(new RecipientIsNotEligibleForThisNotificationTypeError());
                break;
            default:
                return Result.fail(new ValidationError("Notification type is invalid"));
        }
            
        final NotificationDelivery delivery = NotificationDelivery.create(recipient);
        deliveries.put(recipient, delivery);
        
        modified();
        
        return Result.ok();
    }
    
    public final void removeRecipient(Recipient recipient) {
        if(isSent()) return;
        deliveries.remove(recipient);
        modified();
    }
    
    public final Result replaceRecipients(Set<Recipient> recipients) {
        
        getRecipients().stream()
                .filter(recipient -> !recipients.contains(recipient))
                .forEach(recipientToRemove -> removeRecipient(recipientToRemove));
        
        for(Recipient recipient : recipients) {
            final Result result = addRecipient(recipient);
            if(result.isFailure) return result;
        }
        
        return Result.ok();
    }
    
    public final boolean hasRecipient(Recipient recipient) {
        return this.deliveries.containsKey(recipient);
    }
    
    public final void markAsDelivered(
            Recipient recipient,
            DateTime deliveredAt
    ) {
        
        if(!isSent()) return;
        if(isDelivered(recipient)) return;
        
        final NotificationDelivery delivery =
                NotificationDelivery.create(recipient, deliveredAt, true);
        
        deliveries.put(recipient, delivery);
        
        modified();
    }
    
    public final void markAsSent() {
        if(isSent()) return;
        if(!isOnSchedule()) return;
        this.sentAt = DateTime.createNow();
        this.isSent = true;
        modified();
    }
    
    public boolean isOnSchedule() {
        return this.scheduledOn.getValue().isBefore(DateTime.createNow().getValue());
    }
    
    public final boolean isDelivered(Recipient recipient) {
        return
                deliveries.containsKey(recipient) &&
                deliveries.get(recipient).isDelivered();
    }
    
    public final boolean isSent() {
        return this.isSent;
    }
    
    public Result edit(
            Optional<NotifTitle> title,
            Optional<Message> message,
            Optional<URL> route,
            DateTime scheduledOn
    ) {
        
        if(isSent())
            return Result.fail(new NotificationIsAlreadySentError());

        if(title != null) {
            title.ifPresentOrElse(
                    newValue -> {
                        if(!newValue.equals(this.title)) {
                            this.title = newValue;
                            modified();
                        }
                    },
                    () -> {
                        if(this.title != null) {
                            this.title = null;
                            modified();
                        }
                    }
            );
        }
        
        if(message != null) {
            message.ifPresentOrElse(
                    newValue -> {
                        if(!newValue.equals(this.message)) {
                            this.message = newValue;
                            modified();
                        }
                    },
                    () -> {
                        if(this.message != null) {
                            this.message = null;
                            modified();
                        }
                    }
            );
        }
        
        if(route != null) {
            route.ifPresentOrElse(
                    newValue -> {
                        if(!newValue.equals(this.route)) {
                            this.route = newValue;
                            modified();
                        }
                    },
                    () -> {
                        if(this.route != null) {
                            this.route = null;
                            modified();
                        }
                    }
            );
        }

        if(scheduledOn != null) {
            this.scheduledOn = scheduledOn;
            modified();
        }
        
        return Result.ok();
    }
    
    public Result edit(
            Optional<NotifTitle> title,
            Optional<Message> message,
            Optional<URL> route,
            DateTime scheduledOn,
            Set<Recipient> recipients
    ) {
        
        final Result editResult = edit(title, message, route, scheduledOn);
        if(editResult.isFailure) return editResult;
        
        final Result recipientsReplacementResult = replaceRecipients(recipients);
        if(recipientsReplacementResult.isFailure) return recipientsReplacementResult;

        return Result.ok();
    }
    
    public static final Result<Notification> create(
            NotifType type,
            NotifTitle title,
            Message message,
            URL route,
            SenderType senderType,
            DateTime scheduledOn,
            UniqueEntityId senderId,
            Set<Recipient> recipients
    ) {
        
        if(type == null)
            return Result.fail(new ValidationError("Notification type is required"));
        if(senderType == null)
            return Result.fail(new ValidationError("Notification sender type is required"));
        if(scheduledOn == null)
            return Result.fail(new ValidationError("Notification needs to be scheduled"));
        if(senderId == null)
            return Result.fail(new ValidationError("Notification sender id is required"));
        if(scheduledOn.getValue().isBefore(LocalDateTime.now()))
            return Result.fail(new ValidationError("Notification is scheduled in the past"));
        if(recipients == null || recipients.isEmpty())
            return Result.fail(new ValidationError("Notification must have at least one recipient"));
        
        Notification instance;
        
        switch(type) {
            case SMS:
                
                instance = new SMSNotification(
                        title,
                        message,
                        route,
                        senderType,
                        scheduledOn,
                        senderId
                );
                
                break;
            case FCM:
                
                instance = new FCMNotification(
                        title,
                        message,
                        route,
                        senderType,
                        scheduledOn,
                        senderId
                );
                
                break;
            case EMAIL:
                
                instance = new EmailNotification(
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
        
        for(Recipient recipient : recipients) {
            final Result result = instance.addRecipient(recipient);
            if(result.isFailure) return result;
        }
        
        return Result.ok(instance);
    }

    public static final Result<Notification> reconstitute(
            String type,
            UUID id,
            String title,
            String message,
            URL route,
            String senderType,
            LocalDateTime sentAt,
            boolean isSent,
            LocalDateTime scheduledOn,
            UUID senderId,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            Set<NotificationDelivery> deliveries
    ) {
        
        Notification instance;
        
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
        
        switch(typeOrError.getValue()) {
            case SMS:
                
                instance =
                        new SMSNotification(
                                idOrError.getValue(),
                                title != null ? titleOrError.getValue() : null,
                                message != null ? messageOrError.getValue() : null,
                                routeURL != null ? routeURL : null,
                                senderTypeOrError.getValue(),
                                scheduledOnOrError.getValue(),
                                senderIdOrError.getValue(),
                                sentAt != null ? sentAtOrError.getValue() : null,
                                isSent,
                                createdAtOrError.getValue(),
                                lastModifiedAtOrError.getValue(),
                                deliveries.stream()
                                    .collect(Collectors.toMap(k -> k.getRecipient(), v -> v))
                        );
                
            break;
            case FCM:
                
                instance =
                        new FCMNotification(
                                idOrError.getValue(),
                                titleOrError.getValue(),
                                message != null ? messageOrError.getValue() : null,
                                routeURL,
                                senderTypeOrError.getValue(),
                                scheduledOnOrError.getValue(),
                                senderIdOrError.getValue(),
                                sentAtOrError.getValue(),
                                isSent,
                                createdAtOrError.getValue(),
                                lastModifiedAtOrError.getValue(),
                                deliveries.stream()
                                    .collect(Collectors.toMap(k -> k.getRecipient(), v -> v))
                        );
                
            break;
            case EMAIL:
                instance =
                        new EmailNotification(
                                idOrError.getValue(),
                                titleOrError.getValue(),
                                message != null ? messageOrError.getValue() : null,
                                routeURL,
                                senderTypeOrError.getValue(),
                                scheduledOnOrError.getValue(),
                                senderIdOrError.getValue(),
                                sentAtOrError.getValue(),
                                isSent,
                                createdAtOrError.getValue(),
                                lastModifiedAtOrError.getValue(),
                                deliveries.stream()
                                    .collect(Collectors.toMap(k -> k.getRecipient(), v -> v))
                        );
                
            break;
            default:
                instance = null;
        }
        
        return Result.ok(instance);
    }
    
}
