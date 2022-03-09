/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.usecases.EditNotification;

import java.util.*;
import java.net.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.notification.infra.DTOs.commands.EditNotificationRequestDTO;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.notification.repository.*;
import com.jamapplicationserver.modules.notification.domain.*;
import com.jamapplicationserver.modules.notification.domain.errors.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

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
            final Result<Optional<NotifTitle>> titleOrError = NotifTitle.createNullable(request.title);
            
            final Result<Optional<Message>> messageOrError = Message.createNullable(request.message);
            final Result<DateTime> scheduledOnOrError = DateTime.create(request.scheduledOn);
            final Optional<URL> route =
                    request.route != null && !request.route.isEmpty() && !request.route.isBlank() ?
                    Optional.of(new URL(request.route)) : Optional.empty();
            final Result<Set<UniqueEntityId>> recipientsIdsOrErrors =
                    UniqueEntityId.createFromStrings(request.recipients);
            
            combinedProps.add(idOrError);
            
            if(request.title != null) combinedProps.add(titleOrError);
            if(request.message != null) combinedProps.add(titleOrError);
            if(request.scheduledOn != null) combinedProps.add(scheduledOnOrError);
            if(request.recipients != null) combinedProps.add(recipientsIdsOrErrors);
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityId id = idOrError.getValue();
            final Optional<NotifTitle> title = request.title != null ? titleOrError.getValue() : null;
            final Optional<Message> message = request.message != null ? messageOrError.getValue() : null;
            final DateTime scheduledOn = request.scheduledOn != null ? scheduledOnOrError.getValue() : null;
            final Set<UniqueEntityId> recipientsIds =
                    request.recipients != null && !request.recipients.isEmpty() ?
                    recipientsIdsOrErrors.getValue() : null;
            
            final Notification notification = repository.fetchById(id);
            if(notification == null) return Result.fail(new NotificationDoesNotExistError());
            
            final Result result = notification.edit(title, message, route, scheduledOn);
            if(result.isFailure) return result;
            
            if(recipientsIds != null && !recipientsIds.isEmpty()) {
                
                final Set<Recipient> recipients =
                        repository.fetchRecipientsByIds(recipientsIds);
                if(recipients.size() < recipientsIds.size())
                    return Result.fail(new AtleastOneRecipientDoesNotExistError());
                final Result recipientsReplacementResult = notification.replaceRecipients(recipients);
                if(recipientsReplacementResult.isFailure) return recipientsReplacementResult;
                
            }
            
            repository.save(notification);
            
            return Result.ok(notification);
            
        } catch(MalformedURLException e) {
            return Result.fail(new ValidationError("URL is invalid"));
        } catch(Exception e) {
            LogService.getInstance().error(e);
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
