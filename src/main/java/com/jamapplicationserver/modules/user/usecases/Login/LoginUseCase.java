/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.Login;

import com.jamapplicationserver.core.domain.MobileNo;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.user.domain.errors.*;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.repository.UserRepository;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.infra.Services.JWT.JWTUtils;
import com.jamapplicationserver.modules.user.infra.services.LoginAuditManager;
import com.jamapplicationserver.modules.user.repository.IUserRepository;
import com.jamapplicationserver.modules.user.infra.DTOs.queries.AuthToken;

/**
 *
 * @author amirhossein
 */
public class LoginUseCase implements IUsecase<LoginRequestDTO, AuthToken> {
    
    private final IUserRepository repository;
    
    private LoginUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result<AuthToken> execute(LoginRequestDTO request) throws GenericAppException {
                
        try {
            
            Result loginResult;
            
            final Result<MobileNo> mobileOrError = MobileNo.create(request.mobile);
            final Result<Password> passwordOrError = Password.create(request.password, false);
            final Result<FCMToken> fcmTokenOrError = FCMToken.create(request.FCMToken);
            
            final Result[] combinedProps = {
                mobileOrError,
                passwordOrError
            };
            
            if(request.FCMToken != null && fcmTokenOrError.isFailure)
                return Result.fail(fcmTokenOrError.getError());
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final MobileNo mobile = mobileOrError.getValue();
            final Password password = passwordOrError.getValue();
            final FCMToken fcmToken =
                    request.FCMToken != null ? fcmTokenOrError.getValue() : null;
            
            final User user = repository.fetchByMobile(mobile);
            if(user == null)
                return Result.fail(new UserMobileOrPasswordIsIncorrectError());
            
            // password does not match
            final boolean doesMatch = user.getPassword().equals(password);
            if(!doesMatch) {
                loginResult = Result.fail(new UserMobileOrPasswordIsIncorrectError());
                auditLogin(user.id, loginResult, request);
                return loginResult;
            }

            // user is not verified
            if(!user.isVerified()) {
                loginResult = Result.fail(new UserIsNotVerifiedError());
                auditLogin(user.id, loginResult, request);
                return loginResult;
            }
            
            if(!user.isActive()) {
                loginResult = Result.fail(new UserIsNotActiveError());
                auditLogin(user.id, loginResult, request);
                return loginResult;
            }

            // subscribers cannot login throught non-mobile clients
            if(request.device.family.equals("Other") && user.getRole().isSubscriber()) {
                loginResult = Result.fail(new UserCannotLoginThroughClient());
                auditLogin(user.id, loginResult, request);
                return loginResult;
            }
            
            // generate token
            final String token =
                    JWTUtils.generateToken(user.id, user.getRole(), request.os);
            if(request.FCMToken != null) {
                
                // if current fcm token is different from the new one, replace it
                final FCMToken previousFCMToken = user.getFCMToken();
                if(previousFCMToken != null && !previousFCMToken.equals(fcmToken)) {
                    user.changeFCMToken(fcmToken);
                    repository.save(user);
                }
            }
            
            loginResult = Result.ok(new AuthToken(token));
            auditLogin(user.id, loginResult, request);
            
            return loginResult;
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    private void auditLogin(UniqueEntityId userId, Result result, LoginRequestDTO request) {
        final boolean wasSuccessful = result.isSuccess;
        final BusinessError failureReason = result.isFailure ? result.getError() : null;
        LoginAuditManager
                .getInstance()
                .audit(
                        userId,
                        wasSuccessful,
                        failureReason,
                        request.ip,
                        request.device.family,
                        request.os.family
                );
    }
    
    public static LoginUseCase getInstance() {
        return LoginUseCaseHolder.INSTANCE;
    }
    
    private static class LoginUseCaseHolder {

        private static final LoginUseCase INSTANCE =
                new LoginUseCase(UserRepository.getInstance());
    }
}
