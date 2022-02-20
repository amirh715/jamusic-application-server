/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain;

import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.user.domain.events.*;
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

    private FCMToken fcmToken;

    private UserVerification verification;

    private EmailVerification emailVerification;

    private PasswordResetCode passwordResetVerification;

    private final UniqueEntityId creatorId;

    private UniqueEntityId updaterId;

    // creation constructor
    private User(
            UserName name,
            MobileNo mobile,
            Password password,
            UserRole role,
            Email email,
            FCMToken FCMToken,
            UniqueEntityId creatorId
    ) {
        super();
        this.name = name;
        this.mobile = mobile;
        this.password = password;
        this.state = UserState.NOT_VERIFIED;
        this.role = role;
        this.email = email;
        this.isEmailVerified = false;
        this.fcmToken = FCMToken;
        this.creatorId = creatorId != null ? creatorId : this.id;
        this.updaterId = creatorId;
    }

    // reconstitution constructor
    private User(
            UniqueEntityId id,
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
            UniqueEntityId creatorId,
            UniqueEntityId updaterId,
            UserVerification verification,
            EmailVerification emailVerification,
            PasswordResetCode passwordResetVerification
    ) {
        super(id, createdAt, lastModifiedAt);
        this.name = name;
        this.mobile = mobile;
        this.password = password;
        this.state = state;
        this.role = role;
        this.email = email;
        this.isEmailVerified = isEmailVerified;
        this.imagePath = imagePath;
        this.fcmToken = FCMToken;
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
            FCMToken FCMToken,
            UniqueEntityId creatorId
    ) {

        if (name == null) {
            return Result.fail(new ValidationError("اسم کاربر ضروری است"));
        }
        if (mobile == null) {
            return Result.fail(new ValidationError("شماره موبایل ضروری است"));
        }
        if (password == null) {
            return Result.fail(new ValidationError("رمز ضروری است"));
        }

        User instance = new User(
                name,
                mobile,
                password,
                UserRole.SUBSCRIBER,
                email,
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

        final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromUUID(id);
        final Result<UserName> nameOrError = UserName.create(name);
        final Result<MobileNo> mobileOrError = MobileNo.create(mobile);
        final Result<Password> passwordOrError = Password.create(password, true);
        final Result<Email> emailOrError
                = email != null ? Email.create(email) : Result.ok();
        final Result<UserRole> userRoleOrError = UserRole.create(role);
        final Result<UserState> userStateOrError = UserState.create(state);
        final Result<FCMToken> fcmTokenOrError = FCMToken.create(fcmToken);
        final Result<DateTime> createdAtOrError = DateTime.create(createdAt);
        final Result<DateTime> lastModifiedAtOrError = DateTime.create(lastModifiedAt);
        final Result<UniqueEntityId> creatorIdOrError = UniqueEntityId.createFromUUID(creatorId);
        final Result<UniqueEntityId> updaterIdOrError = UniqueEntityId.createFromUUID(updaterId);

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

        if (fcmToken != null) {
            combinedProps.add(fcmTokenOrError);
        }

        if (email != null) {
            combinedProps.add(emailOrError);
        } else {
            isEmailVerified = false;
        }

        final Result combinedPropsResult = Result.combine(combinedProps);

        if (combinedPropsResult.isFailure) {
            return combinedPropsResult;
        }

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

    public FCMToken getFCMToken() {
        return this.fcmToken;
    }

    public UniqueEntityId getCreatorId() {
        return this.creatorId;
    }

    public UniqueEntityId getUpdaterId() {
        return this.updaterId;
    }

    public Result editProfile(
            UserName name,
            Optional<Email> email,
            Path profileImagePath,
            Boolean removeProfileImage,
            User updater
    ) {

        if (!updater.isActive()) {
            return Result.fail(new UserIsNotActiveError());
        }

        if (!updater.equals(this) && !updater.isAdmin()) {
            return Result.fail(new UserIsNotAdminError());
        }

        if (name != null) {
            this.name = name;
        }

        if (email != null) {
            email.ifPresentOrElse(
                    newValue -> {
                        if (!newValue.equals(this.email)) {
                            this.email = newValue;
                            unverifyEmail();
                            modified();
                        }
                    },
                    () -> removeEmail()
            );
        }

        if (profileImagePath != null) {
            if (!profileImagePath.equals(this.imagePath)) {
                this.imagePath = profileImagePath;
                modified();
            }
        }

        if (removeProfileImage != null) {
            if (removeProfileImage) {
                removeProfileImage();
            }
        }

        this.updaterId = updater.id;

        return Result.ok();
    }

    public Result activateUser(User activator) {
        if (!activator.isAdmin()) {
            return Result.fail(new UserIsNotAdminError());
        }
        activateUser();
        this.updaterId = activator.id;
        return Result.ok();
    }

    private void activateUser() {
        this.state = UserState.ACTIVE;
    }

    public Result blockUser(User blocker) {
        if (!blocker.isAdmin()) {
            return Result.fail(new UserIsNotActiveError());
        }
        this.blockUser();
        this.updaterId = blocker.id;
        addDomainEvent(new UserBlocked(this));
        return Result.ok();
    }

    private void blockUser() {
        this.state = UserState.BLOCKED;
    }

    public boolean isActive() {
        return this.state.equals(UserState.ACTIVE);
    }

    public boolean isVerified() {
        return !this.state.equals(UserState.NOT_VERIFIED);
    }

    public Result requestUserVerification() {

        // active user can not request verification
        if (isVerified()) {
            return Result.fail(new UserIsAlreadyVerifiedError());
        }

        // previously requested verification code is not expired
        if (this.verification != null && !this.verification.isExpired()) {
            return Result.fail(new AccountVerificationIsNotExpiredError());
        }

        this.verification = UserVerification.create();
        addDomainEvent(new AccountVerificationRequested(this));

        return Result.ok();
    }

    public Result verify(int code) {

        // user is already verified and active
        if (isVerified()) {
            return Result.fail(new UserIsAlreadyVerifiedError());
        }

        if (verification == null) {
            return Result.fail(new AccountVerificationRequestDoesNotExistError());
        }

        // verification code is expired
        if (verification.isExpired()) {
            return Result.fail(new AccountVerificationCodeIsExpiredError());
        }

        // match verification code
        final Result matchAndExpireResult = verification.matchAndExpire(code);
        if (matchAndExpireResult.isFailure) {
            return matchAndExpireResult;
        }

        // nullify verification object
        this.verification = null;

        // activate user
        activateUser();

        modified();

        return Result.ok();
    }

    public Result requestEmailVerification() {

        if (!this.isActive()) {
            return Result.fail(new UserIsNotActiveError());
        }

        if (this.email == null) {
            return Result.fail(new NoEmailIsSetError());
        }

        if (this.isEmailVerified) {
            return Result.fail(new EmailIsAlreadyVerifiedError());
        }

        if (this.emailVerification != null && !this.emailVerification.isExpired()) {
            return Result.fail(new EmailVerificationLinkIsNotExpiredError());
        }

        this.emailVerification = EmailVerification.create();

        return Result.ok();
    }

    public Result verifyEmail(UUID token) {

        // user is not active
        if (!isActive()) {
            return Result.fail(new UserIsNotActiveError());
        }

        // no email exists
        if (this.email == null) {
            return Result.fail(new NoEmailIsSetError());
        }

        // email is already verified
        if (this.isEmailVerified) {
            return Result.fail(new EmailIsAlreadyVerifiedError());
        }

        // no email verification request exists
        if (this.emailVerification == null) {
            return Result.fail(new EmailVerificationLinkDoesNotExistError());
        }

        // email verification link is expired
        if (this.emailVerification.isExpired()) {
            return Result.fail(new EmailVerificationLinkIsExpiredError());
        }

        // email verification link does not match
        if (!this.emailVerification.doesMatch(token)) {
            return Result.fail(new EmailVerificationLinkIsInvalidError());
        }

        // verify email
        this.isEmailVerified = true;

        // nullify email verification
        this.emailVerification = null;

        modified();

        return Result.ok();
    }

    private void unverifyEmail() {
        this.isEmailVerified = false;
    }

    public Result changeEmail(Email email, User updater) {

        if (!isActive() && !updater.isAdmin()) {
            return Result.fail(new UserIsNotActiveError());
        }

        // unverify previous email
        unverifyEmail();

        // set new email
        this.email = email;

        this.updaterId = updater.id;

        modified();

        return Result.ok();
    }

    public Result removeEmail(User updater) {

        if (!this.isActive() && !updater.isAdmin()) {
            return Result.fail(new UserIsNotActiveError());
        }

        // unverify previous email
        unverifyEmail();

        // set email to null
        this.email = null;

        this.updaterId = updater.id;

        modified();

        return Result.ok();

    }

    public void removeEmail() {
        unverifyEmail();
        modified();
        this.email = null;
    }

    public Result changeProfileImage(Path imagePath, User updater) {
        if (!this.isActive() && !updater.isAdmin()) {
            return Result.fail(new UserIsNotActiveError());
        }
        this.imagePath = imagePath;
        modified();
        return Result.ok();
    }

    public void removeProfileImage() {
        this.imagePath = null;
        modified();
    }

    public Result requestPasswordReset() {

        // user is not active
        if (!isActive()) {
            return Result.fail(new UserIsNotActiveError());
        }

        // previous password reset request is not expired yet
        if (this.passwordResetVerification != null
                && !this.passwordResetVerification.isExpired()) {
            return Result.fail(new PasswordResetCodeIsNotExpiredError());
        }

        this.passwordResetVerification = PasswordResetCode.create();

        return Result.ok();
    }

    public Result resetPassword(Password newPassword, int passwordResetCode) {

        // user is not active
        if (!isActive()) {
            return Result.fail(new UserIsNotActiveError());
        }

        // no password reset request exists
        if (this.passwordResetVerification == null) {
            return Result.fail(new PasswordResetRequestDoesNotExistError());
        }

        // password reset request is expired
        if (this.passwordResetVerification.isExpired()) {
            return Result.fail(new PasswordResetRequestIsExpiredError());
        }

        // verification code does not match
        Result result = this.passwordResetVerification.matchAndExpire(passwordResetCode);

        // password reset verification code does not match
        if (result.isFailure) {
            return result;
        }

        // reset password
        this.password = newPassword;

        // nullify password reset verification
        this.passwordResetVerification = null;

        modified();

        return Result.ok();
    }

    /**
     * Change the password of the user's account.
     *
     * @param newPassword
     * @param currentPassword
     * @param updater
     * @return
     */
    public Result changePassword(
            Password newPassword,
            Password currentPassword,
            User updater
    ) {

        // new password is required
        if (newPassword == null) {
            return Result.fail(new ValidationError("رمز جدید ضروری است"));
        }

        // updating user is required
        if (updater == null) {
            return Result.fail(new ValidationError("کاربری که بروزرسانی می کند ضروری است"));
        }

        // updating user is not active
        if (!updater.isActive()) {
            return Result.fail(new UserIsNotActiveError());
        }

        // user is changing his/her password
        if (updater.equals(this)) {

            // current password is incorrect
            if (!currentPassword.equals(password)) {
                return Result.fail(new PasswordIsIncorrectError());
            }

        } else { // another user is changing this user's password

            // updating user is not an admin
            if (!updater.isAdmin()) {
                return Result.fail(new UserIsNotAdminError());
            }

        }

        this.password = newPassword;
        this.updaterId = updater.id;
        modified();

        return Result.ok();
    }

    public Result changeRole(UserRole newRole, User updater) {

        // new role is null
        if (newRole == null) {
            return Result.fail(new ValidationError("نقش جدید ضروری است"));
        }

        // updating user is null
        if (updater == null) {
            return Result.fail(new ValidationError("کاربری که بروزرسانی می کند ضروری است"));
        }

        // updating user is not active
        if (!updater.isActive()) {
            return Result.fail(new UserIsNotActiveError());
        }

        // updating user is not an admin
        if (!updater.isAdmin()) {
            return Result.fail(new AccessDeniedError("تنها مدیر سیستم می تواند نقش کاربران را تغییر بدهد"));
        }

        // only one admin can exist.
        if (newRole.equals(UserRole.ADMIN)) {
            return Result.fail(new OnlyOneAdminCanExistError());
        }

        // change role
        this.role = newRole;
        this.updaterId = updater.getId();
        modified();

        return Result.ok();
    }

    public void changeFCMToken(FCMToken fcmToken) {
        this.fcmToken = fcmToken;
        modified();
    }

    @Override
    public boolean equals(Object user) {
        if (user == this) {
            return true;
        }
        if (!(user instanceof User)) {
            return false;
        }
        User u = (User) user;
        return u.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return this.id.toValue().hashCode();
    }

}
