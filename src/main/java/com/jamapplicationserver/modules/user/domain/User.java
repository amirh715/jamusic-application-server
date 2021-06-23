/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain;

import java.nio.file.*;
import java.net.URL;
import java.util.*;
import java.time.LocalDateTime;
import com.jamapplicationserver.core.domain.UniqueEntityID;
import com.jamapplicationserver.core.domain.AggregateRoot;
import com.jamapplicationserver.core.domain.DateTime;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.domain.errors.*;

/**
 *
 * @author amirhossein
 */
public class User extends AggregateRoot {
    
    private UserName name;
    
    private final MobileNo mobile;
    
    private Password password;
    
    private UserState state;
    
    private UserRole role;
    
    private Email email;
    
    private boolean isEmailVerified;
    
    private Path imagePath;
    
    private final DateTime createdAt;
    
    private final DateTime lastModifiedAt;
    
    private FCMToken fcmToken;
    
    private UserVerification verification;
    
    private EmailVerification emailVerification;
    
    private PasswordResetCode passwordResetVerification;
    
    private final UniqueEntityID creatorId;
    
    private UniqueEntityID updaterId;

    // creation constructor
    private User(
            UserName name,
            MobileNo mobile,
            Password password,
            UserRole role,
            Email email,
            Path imagePath,
            FCMToken FCMToken,
            UniqueEntityID creatorId
    ) {
        super();
        this.name = name;
        this.mobile = mobile;
        this.password = password;
        this.state = UserState.NOT_VERIFIED;
        this.role = role;
        this.email = email;
        this.isEmailVerified = false;
        this.imagePath = imagePath;
        this.fcmToken = FCMToken;
        this.createdAt = DateTime.createNow();
        this.lastModifiedAt = DateTime.createNow();
        this.creatorId = creatorId != null ? creatorId : this.id;
        this.updaterId = creatorId;
    }
    
    // reconstitution constructor
    private User(
            UniqueEntityID id,
            UserName name,
            MobileNo mobile,
            Password password,
            UserState state,
            UserRole role,
            Email email,
            boolean isEmailVerified,
            Path imagePath,
            FCMToken FCMToken,
            DateTime createdAt,
            DateTime lastModifiedAt,
            UniqueEntityID creatorId,
            UniqueEntityID updaterId,
            UserVerification verification,
            EmailVerification emailVerification,
            PasswordResetCode passwordResetVerification
    ) {
        super(id);
        this.name = name;
        this.mobile = mobile;
        this.password = password;
        this.state = state;
        this.role = role;
        this.email = email;
        this.isEmailVerified = isEmailVerified;
        this.imagePath = imagePath;
        this.fcmToken = FCMToken;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.creatorId = creatorId;
        this.updaterId = updaterId;
        this.verification = verification;
        this.emailVerification = emailVerification;
        this.passwordResetVerification = passwordResetVerification;
    }
    
    public static final Result<User> create(
            UserName name,
            MobileNo mobile,
            Password password,
            Email email,
            Path imagePath,
            FCMToken FCMToken,
            UniqueEntityID creatorId
    ) {
        
        if(name == null) return Result.fail(new ValidationError("User name is required."));
        if(mobile == null) return Result.fail(new ValidationError("Mobile number is required."));
        if(password == null) return Result.fail(new ValidationError("Password is required."));
        
        User instance = new User(
                name,
                mobile,
                password,
                UserRole.SUBSCRIBER,
                email,
                imagePath,
                FCMToken,
                creatorId
        );
        
        return Result.ok(instance);
    }
    
    public static final Result<User> reconstitute(
            UUID id,
            String name,
            String mobile,
            String password,
            String role,
            String state,
            String email,
            boolean isEmailVerified,
            Path imagePath,
            String fcmToken,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt,
            UUID creatorId,
            UUID updaterId,
            UserVerification verification,
            EmailVerification emailVerification,
            PasswordResetCode passwordResetVerification
    ) {

        final Result<UniqueEntityID> idOrError = UniqueEntityID.createFromUUID(id);
        final Result<UserName> nameOrError = UserName.create(name);
        final Result<MobileNo> mobileOrError = MobileNo.create(mobile);
        final Result<Password> passwordOrError = Password.create(password, true);
        final Result<Email> emailOrError =
                email != null ? Email.create(email) : Result.ok();
        final Result<UserRole> userRoleOrError = UserRole.create(role);
        final Result<UserState> userStateOrError = UserState.create(state);
        final Result<FCMToken> fcmTokenOrError = FCMToken.create(fcmToken);
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        final Result<DateTime> lastModifiedAtOrError = DateTime.create(lastModifiedAt);
        final Result<UniqueEntityID> creatorIdOrError = UniqueEntityID.createFromUUID(creatorId);
        final Result<UniqueEntityID> updaterIdOrError = UniqueEntityID.createFromUUID(updaterId);

        final List<Result> combinedProps = new ArrayList<>();
        
        combinedProps.add(idOrError);
        combinedProps.add(nameOrError);
        combinedProps.add(mobileOrError);
        combinedProps.add(passwordOrError);
        combinedProps.add(userRoleOrError);
        combinedProps.add(userStateOrError);
        combinedProps.add(createdAtOrError);
        combinedProps.add(lastModifiedAtOrError);
        combinedProps.add(creatorIdOrError);
        combinedProps.add(updaterIdOrError);
        
        if(fcmToken != null)
            combinedProps.add(fcmTokenOrError);
        
        if(email != null)
            combinedProps.add(emailOrError);
        else
            isEmailVerified = false;
        
        final Result combinedPropsResult = Result.combine(combinedProps);
        
        if(combinedPropsResult.isFailure) return combinedPropsResult;

        User instance = new User(
                idOrError.getValue(),
                nameOrError.getValue(),
                mobileOrError.getValue(),
                passwordOrError.getValue(),
                userStateOrError.getValue(),
                userRoleOrError.getValue(),
                email != null ? emailOrError.getValue() : null,
                isEmailVerified,
                imagePath,
                fcmToken != null ? fcmTokenOrError.getValue() : null,
                createdAtOrError.getValue(),
                lastModifiedAtOrError.getValue(),
                creatorIdOrError.getValue(),
                updaterIdOrError.getValue(),
                verification,
                emailVerification,
                passwordResetVerification
        );

        return Result.ok(instance);
    }
    
    public UserName getName() {
        return this.name;
    }
    
    public MobileNo getMobile() {
        return this.mobile;
    }
    
    public Password getPassword() {
        return this.password;
    }
    
    public UserState getState() {
        return this.state;
    }
    
    public UserRole getRole() {
        return this.role;
    }
    
    public boolean isAdmin() {
        return this.role.equals(UserRole.ADMIN);
    }
    
    public Email getEmail() {
        return this.email;
    }
    
    public boolean isEmailVerified() {
        return this.isEmailVerified;
    }
    
    public boolean hasEmail() {
        return this.email != null;
    }
    
    public EmailVerification getEmailVerification() {
        return this.emailVerification;
    }
    
    public UserVerification getUserVerification() {
        return this.verification;
    }
    
    public PasswordResetCode getPasswordResetCode() {
        return this.passwordResetVerification;
    }
    
    public boolean hasProfileImage() {
        return this.imagePath != null;
    }
    
    public Path getImagePath() {
        return this.imagePath;
    }
    
    public DateTime getCreatedAt() {
        return this.createdAt;
    }
    
    public DateTime getLastModifiedAt() {
        return this.lastModifiedAt;
    }
    
    public FCMToken getFCMToken() {
        return this.fcmToken;
    }
    
    public UniqueEntityID getCreatorId() {
        return this.creatorId;
    }
    
    public UniqueEntityID getUpdaterId() {
        return this.updaterId;
    }
    
    public Result editProfile(
            UserName name,
            User updater
    ) {
        
        if(!this.isActive() && !updater.isAdmin())
            return Result.fail(new UserIsNotActiveError());
        
        this.name = name;
        
        this.updaterId = updater.id;
        
        return Result.ok();
    }
    
    public Result activateUser(User activator) {
        if(!activator.isAdmin()) return Result.fail(new UserIsNotAdminError("Admin is not active"));
        this.activateUser();
        this.updaterId = activator.id;
        return Result.ok();
    }
    
    private void activateUser() {
        this.state = UserState.ACTIVE;
    }
    
    public Result blockUser(User blocker) {
        if(!blocker.isAdmin()) return Result.fail(new UserIsNotActiveError());
        this.blockUser();
        this.updaterId = blocker.id;
        return Result.ok();
    }
    
    private void blockUser() {
        this.state = UserState.BLOCKED;
    }
    
    public boolean isActive() {
        return this.state.equals(UserState.ACTIVE);
    }
    
    public boolean isVerified() {
        return
                this.state.equals(UserState.ACTIVE) ||
                this.state.equals(UserState.BLOCKED);
    }
    
    public Result requestUserVerification() {

        // active user can not request verification
        if(this.isVerified())
            return Result.fail(new UserIsAlreadyVerifiedError());

        // previously requested verification code is not expired
        if(this.verification != null && !this.verification.isExpired())
            return Result.fail(new AccountVerificationIsNotExpiredError());
        
        this.verification = UserVerification.create();
        
        return Result.ok();
    }
    
    public Result verify(int code) {
        
        // user is already verified and active
        if(this.isVerified())
            return Result.fail(new UserIsAlreadyVerifiedError());
        
        if(this.verification == null)
            return Result.fail(new AccountVerificationRequestDoesNotExistError());
        
        // verification code is expired
        if(this.verification.isExpired())
            return Result.fail(new AccountVerificationCodeIsExpiredError());
        
        // match verification code
        final Result matchAndExpireResult = this.verification.matchAndExpire(code);
        if(matchAndExpireResult.isFailure) return matchAndExpireResult;
        
        // nullify verification object
        this.verification = null;
        
        // activate user
        this.activateUser();
        
        return Result.ok();
    }
    
    public Result requestEmailVerification() {
        
        if(!this.isActive())
            return Result.fail(new UserIsNotActiveError());
        
        if(this.email == null)
            return Result.fail(new NoEmailIsSetError());
        
        if(this.isEmailVerified)
            return Result.fail(new EmailIsAlreadyVerifiedError());
        
        if(this.emailVerification != null && !this.emailVerification.isExpired())
            return Result.fail(new EmailVerificationLinkIsNotExpiredError());
        
        this.emailVerification = EmailVerification.create();
        
        return Result.ok();
    }
    
    public Result verifyEmail(URL link) {
        
        // user is not active
        if(!this.isActive())
            return Result.fail(new UserIsNotActiveError());
        
        // no email exists
        if(this.email == null)
            return Result.fail(new NoEmailIsSetError());
        
        // email is already verified
        if(this.isEmailVerified)
            return Result.fail(new EmailIsAlreadyVerifiedError());
        
        // no email verification request exists
        if(this.emailVerification == null)
            return Result.fail(new EmailVerificationLinkDoesNotExistError());
            
        // email verification link is expired
        if(this.emailVerification.isExpired())
            return Result.fail(new EmailVerificationLinkIsExpiredError());
        
        // email verification link does not match
        if(!this.emailVerification.doesMatch(link))
            return Result.fail(new EmailVerificationLinkIsInvalidError());
        
        // verify email
        this.isEmailVerified = true;
        
        // nullify email verification
        this.emailVerification = null;
        
        return Result.ok();
    }
    
    private void unverifyEmail() {
        this.isEmailVerified = false;
    }
    
    public Result changeEmail(Email email, User updater) {
        
        if(!this.isActive() && !updater.isAdmin())
            return Result.fail(new UserIsNotActiveError());
        
        // unverify previous email
        this.unverifyEmail();
        
        // set new email
        this.email = email;
        
        this.updaterId = updater.id;
        
        return Result.ok();
    }
    
    public Result removeEmail(User updater) {
        
        if(!this.isActive() && !updater.isAdmin())
            return Result.fail(new UserIsNotActiveError());
        
        // unverify previous email
        this.unverifyEmail();
        
        // set email to null
        this.email = null;
        
        this.updaterId = updater.id;
        
        return Result.ok();
        
    }
    
    public Result changeProfileImage(Path imagePath, User updater) {
        if(!this.isActive() && !updater.isAdmin())
            return Result.fail(new UserIsNotActiveError());
        this.imagePath = imagePath;
        return Result.ok();
    }
    
    public Result removeProfileImage(User updater) {
        
        if(!this.isActive() && !updater.isAdmin())
            return Result.fail(new UserIsNotActiveError());
        
        this.imagePath = null;
        
        this.updaterId = updater.id;
        
        return Result.ok();
    }
    
    public Result requestPasswordReset() {
        
        // user is not active
        if(!this.isActive())
            return Result.fail(new UserIsNotActiveError());
        
        // previous password reset request is not expired yet
        if(
                this.passwordResetVerification != null &&
                !this.passwordResetVerification.isExpired()
        ) return Result.fail(new PasswordResetCodeIsNotExpiredError());
        
        this.passwordResetVerification = PasswordResetCode.create();

        return Result.ok();
    }
    
    public Result resetPassword(Password newPassword, int passwordResetCode) {
        
        // user is not active
        if(!this.isActive())
            return Result.fail(new UserIsNotActiveError());
        
        // no password reset request exists
        if(this.passwordResetVerification == null)
            return Result.fail(new PasswordResetRequestDoesNotExistError());
        
        // password reset request is expired
        if(this.passwordResetVerification.isExpired())
            return Result.fail(new PasswordResetRequestIsExpiredError());
        
        // verification code does not match
        Result result = this.passwordResetVerification.matchAndExpire(passwordResetCode);
        
        // password reset verification code does not match
        if(result.isFailure) return result;
        
        // reset password
        this.password = newPassword;
        
        // nullify password reset verification
        this.passwordResetVerification = null;

        return Result.ok();
    }
    
    public Result changePassword(
            Password newPassword,
            Password currentPassword,
            User updater
    ) {
        if(newPassword == null)
            return Result.fail(new ValidationError("Password is required"));
        if(updater == null)
            return Result.fail(new ValidationError("Updater user is required"));
        if(!this.isActive() && !updater.isAdmin())
            return Result.fail(new UserIsNotActiveError());
        if(!this.password.equals(currentPassword))
            return Result.fail(new PasswordIsIncorrectError());
        this.password = newPassword;
        this.updaterId = updater.id;
        return Result.ok();
    }
    
    public Result changeRole(UserRole newRole, User updater) {
        
        // new role is null
        if(newRole == null)
            return Result.fail(new ValidationError("New role is required."));
        
        // updating user is null
        if(updater == null)
            return Result.fail(new ValidationError("Updating user is required."));
        
        // updating user is not an admin
        if(!updater.isAdmin())
            return Result.fail(new AccessDeniedError("Only the admin can change user roles"));
        
        // only one admin can exist.
        if(newRole.equals(updater.role))
            return Result.fail(new OnlyOneAdminCanExistError());
        
        // change role
        this.role = newRole;
        
        return Result.ok();
    }
    
    public void changeFCMToken(FCMToken fcmToken) {
        this.fcmToken = fcmToken;
    }
    
    @Override
    public boolean equals(Object user) {
        if(user == this)
            return true;
        if(!(user instanceof User))
            return false;
        User u = (User) user;
        return u.id.equals(this.id);
    }
    
    @Override
    public int hashCode() {
        return this.id.toValue().hashCode();
    }
    
}
