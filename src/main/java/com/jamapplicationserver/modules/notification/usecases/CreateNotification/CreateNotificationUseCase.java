/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.CreateNotification;

import java.util.*;
import java.time.*;
import java.net.*;
import java.nio.file.Path;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.commands.CreateNotificationRequestDTO;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.notification.repository.*;
import com.jamapplicationserver.modules.notification.domain.*;
import com.jamapplicationserver.modules.notification.domain.errors.*;
import com.jamapplicationserver.infra.Persistence.filesystem.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

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
            final URL route = request.route != null ? new URL(request.route) : null;
            final Result<DateTime> scheduledOnOrError = DateTime.create(request.scheduledOn);
            final Result<ImageStream> imageOrError = ImageStream.createAndValidate(request.image);
            final Result<Set<UniqueEntityId>> recipientsIdsOrErrors =
                    UniqueEntityId.createFromStrings(request.recipients);
            
            combinedProps.add(typeOrError);
            combinedProps.add(recipientsIdsOrErrors);
            
            if(request.title != null) combinedProps.add(titleOrError);
            if(request.message != null) combinedProps.add(messageOrError);
            if(request.scheduledOn != null) combinedProps.add(scheduledOnOrError);
            if(request.image != null) combinedProps.add(imageOrError);
                
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;

            final NotifType type = typeOrError.getValue();
            final NotifTitle title = request.title != null ? titleOrError.getValue() : null;
            final Message message = request.message != null ? messageOrError.getValue() : null;
            final DateTime scheduledOn = request.scheduledOn != null ?
                    scheduledOnOrError.getValue() :
                    DateTime.createWithoutValidation(LocalDateTime.now().plusMinutes(5));
            final ImageStream image = request.image != null ? imageOrError.getValue() : null;
            final Set<UniqueEntityId> recipientsIds =
                    request.recipients != null && !request.recipients.isEmpty() ?
                    recipientsIdsOrErrors.getValue() : null;

            // ## POSSIBLE DOMAIN LOGIC LEAKAGE
            final Set<Recipient> recipients = repository.fetchRecipientsByIds(recipientsIds);
            if(recipients.size() != recipientsIds.size())
                return Result.fail(new AtleastOneRecipientDoesNotExistError());
            // ##
            
            final Result<Notification> notificationOrError =
                    Notification.create(
                            type,
                            title,
                            message,
                            route,
                            SenderType.USER,
                            scheduledOn,
                            request.subjectId,
                            recipients
                    );
            if(notificationOrError.isFailure) return notificationOrError;
            
            final Notification notification = notificationOrError.getValue();
            
            if(image != null) {
                final Path path = persistence.buildPath(Notification.class);
                persistence.write(image, path);
            }
            
            repository.save(notification);
            
            return Result.ok(notification);
            
        } catch(MalformedURLException e) {
            return Result.fail(new ValidationError("Notification link is invalid"));
        } catch(Exception e) {
            LogService.getInstance().error(e);
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
