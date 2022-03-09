/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.CreateUser;

import java.util.*;
import java.nio.file.Path;
import org.hibernate.exception.ConstraintViolationException;
import com.jamapplicationserver.infra.Persistence.filesystem.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.user.domain.errors.*;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.repository.exceptions.*;
import com.jamapplicationserver.modules.user.repository.UserRepository;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.repository.IUserRepository;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author amirhossein
 */
public class CreateUserUseCase implements IUsecase<CreateUserRequestDTO, String> {
    
    private final IUserRepository repository;
    private final IFilePersistenceManager persistence;
    
    private CreateUserUseCase(
            IUserRepository repository,
            IFilePersistenceManager persistence
    ) {
        this.repository = repository;
        this.persistence = persistence;
    }
    
    @Override
    public Result execute(CreateUserRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<UserName> nameOrError =
                    UserName.create(request.name);
            
            final Result<MobileNo> mobileOrError =
                    MobileNo.create(request.mobile);
            
            final Result<Password> passwordOrError =
                    Password.create(request.password, false);
            
            final Result<Email> emailOrError =
                    Email.create(request.email);
            
            final Result<UserRole> roleOrError =
                    UserRole.create(request.role);
            
            final Result<FCMToken> FCMTokenOrError =
                    FCMToken.create(request.FCMToken);
            
            final Result<ImageStream> imageOrError =
                    ImageStream.createAndValidate(request.image);
            
            final List<Result> combinedProps = new ArrayList<>();
            
            combinedProps.add(nameOrError);
            combinedProps.add(mobileOrError);
            combinedProps.add(passwordOrError);
            
            if(request.email != null)
                combinedProps.add(emailOrError);
            
            if(request.role != null)
                combinedProps.add(roleOrError);
            
            if(request.FCMToken != null)
                combinedProps.add(FCMTokenOrError);
            
            if(request.image != null)
                combinedProps.add(imageOrError);
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            
            if(combinedPropsResult.isFailure)
                return combinedPropsResult;
            
            final UserName name = nameOrError.getValue();
            final MobileNo mobile = mobileOrError.getValue();
            final Password password = passwordOrError.getValue();
            final Email email = request.email != null ? emailOrError.getValue() : null;
            final FCMToken fcmToken = request.FCMToken != null ? FCMTokenOrError.getValue() : null;
            final UserRole role = request.role != null ? roleOrError.getValue() : null;
            final ImageStream image = request.image != null ? imageOrError.getValue() : null;
            
            final Result<User> userOrError = User.create(
                    name,
                    mobile,
                    password,
                    email,
                    fcmToken,
                    request.subjectId != null ? request.subjectId : null
            );
            
            if(userOrError.isFailure)
                return userOrError;           

            final User user = userOrError.getValue();
            
            // ## POSSIBLE DOMAIN LOGIC LEAK
            // fetch creator user & change user role
            if(
                    request.subjectId != null &&
                    !request.subjectId.equals(user.id) &&
                    role != null &&
                    repository.exists(request.subjectId)
            ) {
                final User creator = repository.fetchById(request.subjectId);
                final Result roleChangeResult = user.changeRole(role, creator);
                if(roleChangeResult.isFailure) return roleChangeResult;
            }
            // ##
            
            // save profile image
            if(image != null) {
                final User updater = repository.fetchById(request.subjectId);
                final Path imagePath = persistence.buildPath(User.class);
                persistence.write(image, imagePath);
                final Result r = user.changeProfileImage(imagePath, updater);
                if(r.isFailure) return r;
            }

            // save user to database
            repository.save(user);

            return Result.ok();
                    
        } catch(ConstraintViolationException e) {
            
            final String constraintName = e.getConstraintName();
            
            if(constraintName.equals("mobile_unique_key"))
                return Result.fail(new UserAlreadyExistsError());
            
            if(constraintName.equals("email_unique_key"))
                return Result.fail(new UserEmailAlreadyExistsError());
            
            if(constraintName.equals("creator_id_fk_key"))
                return Result.fail(new UserDoesNotExistError("Creator user does not exist"));
            
            throw new GenericAppException();
            
        } catch(MaxAllowedUserLimitReachedException e) {
            return Result.fail(new MaxAllowedUserLimitReachedError());
            
        } catch(OnlyOneAdminCanExistException e) {
            return Result.fail(new OnlyOneAdminCanExistError());
        } catch(Exception e) {
            LogService.getInstance().error(e);
            throw new GenericAppException();
        }
        
    }
    
    public static CreateUserUseCase getInstance() {
        return CreateUserUseCaseHolder.INSTANCE;
    }
    
    private static class CreateUserUseCaseHolder {

        private static final CreateUserUseCase INSTANCE =
                new CreateUserUseCase(
                        UserRepository.getInstance(),
                        FilePersistenceManager.getInstance()
                );
    }
}
