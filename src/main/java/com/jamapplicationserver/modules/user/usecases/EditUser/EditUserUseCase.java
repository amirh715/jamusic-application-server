/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.EditUser;

import java.util.*;
import java.nio.file.Path;
import org.hibernate.exception.*;
import javax.persistence.EntityNotFoundException;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.infra.Persistence.filesystem.FilePersistenceManager;
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
            final Result<UserName> nameOrError = UserName.create(request.name);
            final Result<Optional<Email>> emailOrError = Email.createNullable(request.email);
            final Result<UserRole> roleOrError = UserRole.create(request.role);
            final Result<ImageStream> imageOrError = ImageStream.createAndValidate(request.profileImage);
            
            final List<Result> combinedProps = new ArrayList<>();
            
            combinedProps.add(idOrError);
            
            if(request.name != null)
                combinedProps.add(nameOrError);
            if(request.email != null)
                combinedProps.add(emailOrError);
            if(request.role != null)
                combinedProps.add(roleOrError);
            if(request.profileImage != null)
                combinedProps.add(imageOrError);
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityId id =
                    request.id != null ? idOrError.getValue() : null;
            final UserName name =
                    request.name != null ? nameOrError.getValue() : null;
            final Optional<Email> email =
                    request.email != null ? emailOrError.getValue() : null;
            final UserRole role =
                    request.role != null ? roleOrError.getValue() : null;
            final ImageStream image =
                    request.profileImage != null ? imageOrError.getValue() : null;
            
            final User user = repository.fetchById(id);
            if(user == null) return Result.fail(new UserDoesNotExistError());
            
            User updater;
            
            if(!user.getId().equals(request.subjectId)) {
                updater = repository.fetchById(request.subjectId);
                if(updater == null) return Result.fail(new UpdaterUserDoesNotExistError());
            } else {
                updater = user;
            }
            
            Path profileImagePath = null;
            if(image != null) {
                profileImagePath = persistence.buildPath(User.class);
            }

            final Result result =
                    user.editProfile(
                            name,
                            email,
                            profileImagePath,
                            request.removeProfileImage,
                            updater
                    );
            if(result.isFailure) return result;
            
            // save profile image
            if(image != null) {
                persistence.write(image, profileImagePath);
            }
            
            repository.save(user);
            
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
