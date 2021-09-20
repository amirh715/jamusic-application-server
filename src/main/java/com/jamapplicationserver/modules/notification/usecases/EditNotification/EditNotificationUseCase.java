/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.EditNotification;

import java.util.*;
import java.net.URL;
import com.jamapplicationserver.modules.notification.infra.Services.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.EditNotificationRequestDTO;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.notification.repository.*;
import com.jamapplicationserver.modules.notification.domain.*;

/**
 *
 * @author dada
 */
public class EditNotificationUseCase implements IUsecase<EditNotificationRequestDTO, String> {
    
    private final INotificationRepository repository;
    
    private EditNotificationUseCase(
            INotificationRepository repository
    ) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(EditNotificationRequestDTO request) throws GenericAppException {
        
        try {
            
            final ArrayList<Result> combinedProps = new ArrayList<>();
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(request.id);
            final Result<NotifType> typeOrError = NotifType.create(request.type);
            final Result<NotifTitle> titleOrError = NotifTitle.create(request.title);
            final Result<Message> messageOrError = Message.create(request.message);
            final Result<DateTime> scheduledOnOrError = DateTime.create(request.scheduledOn);
            final URL route = new URL(request.route);
            
            request.recipients
                    .forEach(recipient -> {
                        final Result<Recipient> recipientOrError = Recipient.create(recipient);
                        combinedProps.add(recipientOrError);
                    });
            
            combinedProps.add(idOrError);
            
            if(request.type != null) combinedProps.add(typeOrError);
            if(request.title != null) combinedProps.add(titleOrError);
            if(request.message != null) combinedProps.add(titleOrError);
            if(request.scheduledOn != null) combinedProps.add(scheduledOnOrError);
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityId id = idOrError.getValue();
            final NotifType type = typeOrError.getValue();
            final NotifTitle title = titleOrError.getValue();
            final Message message = messageOrError.getValue();
            final DateTime scheduledOn = scheduledOnOrError.getValue();
            
            final Notification notification = this.repository.fetchById(id);
            
            final Result result = notification.edit(title, type, message, route, scheduledOn);
            if(result.isFailure) return result;
            
            if(request.recipients != null) {
                for(String recipient : request.recipients) {
                    final Result<Recipient> recipientOrError = Recipient.create(recipient);
                    if(recipientOrError.isFailure) return recipientOrError;
                    notification.addRecipient(recipientOrError.getValue());
                }
            }
            
            this.repository.save(notification);
            
            return Result.ok(notification);
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static EditNotificationUseCase getInstance() {
        return EditNotificationUseCaseHolder.INSTANCE;
    }
    
    private static class EditNotificationUseCaseHolder {

        private static final EditNotificationUseCase INSTANCE =
                new EditNotificationUseCase(NotificationRepository.getInstance());
    }
}
