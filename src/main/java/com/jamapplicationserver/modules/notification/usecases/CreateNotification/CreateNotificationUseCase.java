/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.CreateNotification;

import java.util.*;
import java.net.*;
import java.nio.file.Path;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.CreateNotificationRequestDTO;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.notification.repository.*;
import com.jamapplicationserver.modules.notification.domain.*;
import com.jamapplicationserver.infra.Persistence.filesystem.*;

/**
 *
 * @author dada
 */
public class CreateNotificationUseCase implements IUsecase<CreateNotificationRequestDTO, String> {
    
    private final INotificationRepository repository;
    private final IFilePersistenceManager persistence;
    
    private CreateNotificationUseCase(
            INotificationRepository repository,
            IFilePersistenceManager persistence
    ) {
        this.repository = repository;
        this.persistence = persistence;
    }
    
    @Override
    public Result execute(CreateNotificationRequestDTO request) throws GenericAppException {
        
        try {
            
            final ArrayList<Result> combinedProps = new ArrayList<>();
            
            final Result<NotifType> typeOrError = NotifType.create(request.type);
            final Result<NotifTitle> titleOrError = NotifTitle.create(request.title);
            final Result<Message> messageOrError = Message.create(request.message);
            final URL route = new URL(request.route);
            final Result<DateTime> scheduledOnOrError = DateTime.create(request.scheduledOn);
            final Result<ImageStream> imageOrError = ImageStream.createAndValidate(request.image);
            
            combinedProps.add(typeOrError);
            
            if(request.title != null) combinedProps.add(titleOrError);
            if(request.message != null) combinedProps.add(messageOrError);
            if(request.scheduledOn != null) combinedProps.add(scheduledOnOrError);
            if(request.image != null) combinedProps.add(imageOrError);
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;

            final NotifType type = typeOrError.getValue();
            final NotifTitle title = request.title != null ? titleOrError.getValue() : null;
            final Message message = request.message != null ? messageOrError.getValue() : null;
            final DateTime scheduledOn = request.scheduledOn != null ? scheduledOnOrError.getValue() : DateTime.createNow();
            final ImageStream image = request.image != null ? imageOrError.getValue() : null;
            
            final Result<Notification> notificationOrError =
                    Notification.create(type, title, message, route, SenderType.USER, scheduledOn, request.subjectId);
            if(notificationOrError.isFailure) return notificationOrError;
            
            final Notification notification = notificationOrError.getValue();
            
            for(String recipient : request.recipients) {
                final Result<Recipient> recipientOrError = Recipient.create(recipient);
                if(recipientOrError.isFailure) return recipientOrError;
                final Result result = notification.addRecipient(recipientOrError.getValue());
                if(result.isFailure) return result;
            }
            
            if(image != null) {
                final Path path = persistence.buildPath(Notification.class);
                persistence.write(image, path);
            }
            
            repository.save(notification);
            
            return Result.ok(notification);
            
        } catch(Exception e) {
            e.printStackTrace();
            throw new GenericAppException(e);
        }
        
    }
    
    public static CreateNotificationUseCase getInstance() {
        return CreateNotificationUseCaseHolder.INSTANCE;
    }
    
    private static class CreateNotificationUseCaseHolder {

        private static final CreateNotificationUseCase INSTANCE =
                new CreateNotificationUseCase(
                        NotificationRepository.getInstance(),
                        FilePersistenceManager.getInstance()
                );
    }
}
