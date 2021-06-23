/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.CreateUser;

import com.jamapplicationserver.core.domain.DateTime;
import com.jamapplicationserver.modules.user.domain.errors.*;
import java.util.*;
import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.core.domain.UniqueEntityID;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.repository.exceptions.*;
import com.jamapplicationserver.modules.user.repository.UserRepository;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.repository.IUserRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import org.hibernate.exception.ConstraintViolationException;


/**
 *
 * @author amirhossein
 */
public class CreateUserUseCase implements IUseCase<CreateUserRequestDTO, CreateUserResponseDTO> {
    
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
            
            final Result<UniqueEntityID> creatorIdOrError =
                    UniqueEntityID.createFromString(request.creatorId);
            
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
            
            if(request.creatorId != null)
                combinedProps.add(creatorIdOrError);
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            
            if(combinedPropsResult.isFailure)
                return combinedPropsResult;
            
            final UserName name = nameOrError.getValue();
            final MobileNo mobile = mobileOrError.getValue();
            final Password password = passwordOrError.getValue();
            final Email email = request.email != null ? emailOrError.getValue() : null;
            final FCMToken fcmToken = request.FCMToken != null ? FCMTokenOrError.getValue() : null;
            final UniqueEntityID creatorId = request.creatorId != null ? creatorIdOrError.getValue() : null;
            final UserRole role = request.role != null ? roleOrError.getValue() : null;
            
            final Result<User> userOrError = User.create(
                    name,
                    mobile,
                    password,
                    email,
                    null,
                    fcmToken,
                    creatorId
            );
            
            if(userOrError.isFailure)
                return userOrError;           

            final User user = userOrError.getValue();
            
            // fetch creator user & change user role
            User creator;
            if(
                    creatorId != null &&
                    !creatorId.equals(user.id) &&
                    role != null &&
                    this.repository.exists(creatorId)
            ) {
                creator = this.repository.fetchById(creatorId);
                final Result roleChangeResult = user.changeRole(role, creator);
                if(roleChangeResult.isFailure) return roleChangeResult;
            }

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
