/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.EditUser;

import com.jamapplicationserver.core.domain.Email;
import com.jamapplicationserver.infra.Persistence.filesystem.FilePersistenceManager;
import java.util.*;
import java.io.*;
import java.nio.file.Path;
import org.hibernate.exception.*;
import javax.persistence.EntityNotFoundException;
import com.jamapplicationserver.modules.user.domain.errors.*;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.Result;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.user.repository.IUserRepository;
import com.jamapplicationserver.modules.user.repository.UserRepository;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.infra.Persistence.filesystem.IFilePersistenceManager;

/**
 *
 * @author amirhossein
 */
public class EditUserUseCase implements IUsecase<EditUserRequestDTO, String> {
    
    private final IUserRepository repository;
    private final IFilePersistenceManager persistence;
    
    private EditUserUseCase(IUserRepository repository, IFilePersistenceManager persistence) {
        this.repository = repository;
        this.persistence = persistence;
    }
    
    @Override
    public Result execute(EditUserRequestDTO request) throws GenericAppException {
        
        System.out.println("EditUserUseCase");
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(request.id);
            final Result<UniqueEntityId> updaterIdOrError = UniqueEntityId.createFromString(request.updaterId);
            final Result<UserName> nameOrError = UserName.create(request.name);
            final Result<Email> emailOrError = Email.create(request.email);
            
            final List<Result> combinedProps = new ArrayList<>();
            
            combinedProps.add(idOrError);
            combinedProps.add(updaterIdOrError);
            
            if(request.name != null)
                combinedProps.add(nameOrError);
            
            if(request.email != null)
                combinedProps.add(emailOrError);

            final Result combinedPropsResult = Result.combine(combinedProps);
            
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityId id =
                    request.id != null ? idOrError.getValue() : null;
            final UniqueEntityId updaterId =
                    request.updaterId != null ? updaterIdOrError.getValue() : null;
            final UserName name =
                    request.name != null ? nameOrError.getValue() : null;
            final Email email =
                    request.email != null ? emailOrError.getValue() : null;
            
            final User user = this.repository.fetchById(id);
            
            if(user == null) return Result.fail(new UserDoesNotExistError());
            
            User updater;
            
            if(!user.id.equals(updaterId))
                updater = this.repository.fetchById(updaterId);
            else
                updater = user;

            final List<Result> combinedResults = new ArrayList<>();
            
            // change/remove email
            if(request.email != null && !request.removeEmail)
                combinedResults.add(user.changeEmail(email, updater));
            else
                combinedResults.add(user.removeEmail(updater));

            // change name
            if(request.name != null)
                combinedResults.add(user.editProfile(name, updater));
            
            // remove or change profile image
            if(request.profileImage != null && !request.removeProfileImage) {
                
                final Path path = this.persistence.buildPath(User.class);
                
                combinedResults.add(user.changeProfileImage(path, updater));
                
            } else {
                combinedResults.add(user.removeProfileImage(updater));
            }
            
            final Result result = Result.combine(combinedResults);
            
            if(result.isFailure) return result;

            this.repository.save(user);
            
            return Result.ok();
            
        } catch(ConstraintViolationException e) {
            
            final String constraintName = e.getConstraintName();
            
            if(constraintName.equals("email_unique_key"))
                return Result.fail(new UserEmailAlreadyExistsError());
            
            throw new GenericAppException();
            
        } catch(EntityNotFoundException e) {
            return Result.fail(new UpdaterUserDoesNotExistError());
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static EditUserUseCase getInstance() {
        return EditUserUseCaseHolder.INSTANCE;
    }
    
    private static class EditUserUseCaseHolder {

        private static final EditUserUseCase INSTANCE =
                new EditUserUseCase(UserRepository.getInstance(), FilePersistenceManager.getInstance());
    }
}
