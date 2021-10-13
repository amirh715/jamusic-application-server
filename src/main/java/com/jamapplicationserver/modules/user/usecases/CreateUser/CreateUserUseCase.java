/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.CreateUser;

import java.util.*;
import org.hibernate.exception.ConstraintViolationException;
import com.jamapplicationserver.core.domain.UserRole;
import com.jamapplicationserver.core.domain.FCMToken;
import com.jamapplicationserver.core.domain.MobileNo;
import com.jamapplicationserver.core.domain.Email;
import com.jamapplicationserver.modules.user.domain.errors.*;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.repository.exceptions.*;
import com.jamapplicationserver.modules.user.repository.UserRepository;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.repository.IUserRepository;

/**
 *
 * @author amirhossein
 */
public class CreateUserUseCase implements IUsecase<CreateUserRequestDTO, String> {
    
    private final IUserRepository repository;
    
    private CreateUserUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(CreateUserRequestDTO request) throws GenericAppException {
        
        System.out.println("CreateUserUseCase");

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
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            
            if(combinedPropsResult.isFailure)
                return combinedPropsResult;
            
            final UserName name = nameOrError.getValue();
            final MobileNo mobile = mobileOrError.getValue();
            final Password password = passwordOrError.getValue();
            final Email email = request.email != null ? emailOrError.getValue() : null;
            final FCMToken fcmToken = request.FCMToken != null ? FCMTokenOrError.getValue() : null;
            final UserRole role = request.role != null ? roleOrError.getValue() : null;
            
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
            
            // save user image

            // save user to database
            this.repository.save(user);

            return Result.ok(user);
                    
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
            throw new GenericAppException();
        }
        
    }
    
    public static CreateUserUseCase getInstance() {
        return CreateUserUseCaseHolder.INSTANCE;
    }
    
    private static class CreateUserUseCaseHolder {

        private static final CreateUserUseCase INSTANCE =
                new CreateUserUseCase(UserRepository.getInstance());
    }
}
