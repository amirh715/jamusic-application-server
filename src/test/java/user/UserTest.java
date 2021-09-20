/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import com.jamapplicationserver.core.domain.UserRole;
import com.jamapplicationserver.core.domain.FCMToken;
import com.jamapplicationserver.core.domain.MobileNo;
import com.jamapplicationserver.core.domain.Email;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.net.*;

import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.user.domain.errors.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author amirhossein
 */
public class UserTest {
    
    private UniqueEntityId id;
    private UserName name;
    private MobileNo mobile;
    private Password password;
    private UserRole role;
    private UserState state;
    private Email email;
    private Path imagePath;
    private FCMToken fcmToken;
    private DateTime createdAt;
    private DateTime lastModifiedAt;
    private UniqueEntityId creatorId;
    private UniqueEntityId updaterId;
    
    private User admin;
    private User libraryManager;
    private User subscriber;
    
    private URL invalidEmailVerificationLink;
    private URL validEmailVerificationLink;
    
    @BeforeEach
    public void setup() throws MalformedURLException {
        
        this.id = UniqueEntityId.createFromUUID(UUID.randomUUID()).getValue();
        this.name = UserName.create("Amirhossein Al.").getValue();
        this.mobile = MobileNo.create("989124974163").getValue();
        this.password = Password.create("12345678", false).getValue();
        this.email = Email.create("amirh715@gmail.com").getValue();
        this.role = UserRole.SUBSCRIBER;
        this.state = UserState.NOT_VERIFIED;
        this.imagePath = null;
        this.fcmToken = FCMToken.create("1234").getValue();
        this.createdAt = DateTime.createNow();
        this.lastModifiedAt = DateTime.createNow();
        this.creatorId = this.id;
        this.updaterId = this.id;
        
        this.invalidEmailVerificationLink = new URL("https://api.jamusic.ir");
        this.validEmailVerificationLink = new URL("https://api.jamusic.ir/v1/user/reset_link/" + UUID.randomUUID().toString());
        
        this.admin = User.reconstitute(
                UUID.randomUUID(),
                name.getValue(),
                mobile.getValue(),
                password.getValue(),
                UserRole.ADMIN.getValue(),
                UserState.ACTIVE.getValue(),
                email.getValue(),
                true,
                imagePath,
                fcmToken.getValue(),
                createdAt.getValue(),
                lastModifiedAt.getValue(),
                creatorId.toValue(),
                updaterId.toValue(),
                null,
                null,
                null
        ).getValue();

    }
    
    // A. Validation
    
    @Test
    @DisplayName("User must have name")
    public void testUserMustHaveName() {
        
        final Result<User> result =
                User.create(null, mobile, password, email, imagePath, fcmToken, creatorId);
        
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof ValidationError);
        
    }
    
    @Test
    @DisplayName("User must have a mobile no.")
    public void testUserMustHaveMobileNo() {
        
        final Result<User> result =
                User.create(name, null, password, email, imagePath, fcmToken, creatorId);
        
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof ValidationError);
        
    }
    
    @Test
    @DisplayName("User must have a password")
    public void testUserMustHavePassword() {

        final Result<User> result =
                User.create(name, mobile, null, email, imagePath, fcmToken, creatorId);
        
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof ValidationError);
        
    }
    
//     B. User State Logic
    
    @Test
    @DisplayName("User state must be 'NOT_VERIFIED' upon creation")
    public void testUserStateMustBeNotVerifiedUponCreation() {
        
        final Result<User> result =
                User.create(name, mobile, password, email, imagePath, fcmToken, creatorId);
        
        assertTrue(result.isSuccess);
        assertTrue(result.getValue().getState().equals(UserState.NOT_VERIFIED));
        
    }
    
//    C. Email Change/Deletion/Verification
    
    @Test
    @DisplayName("Email verification request must fail when user is not active")
    public void testEmailVerificationRequestWhenUserIsNotActive() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.NOT_VERIFIED.getValue(), // user is not active
                        email.getValue(), // email is set
                        false, // email is not verified
                        null,
                        null,
                        LocalDateTime.MAX,
                        LocalDateTime.MIN,
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        final Result result = user.requestEmailVerification();
        
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof UserIsNotActiveError);
        
    }
    
        
    @Test
    @DisplayName("Email verification request must fail when no email is set")
    public void testEmailVerificationRequestWhenNoEmailIsSet() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.ACTIVE.getValue(), // user is active
                        null, // no email is set
                        false, // email is not verified
                        null,
                        null,
                        LocalDateTime.MAX,
                        LocalDateTime.MIN,
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        final Result result = user.requestEmailVerification();
        
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof NoEmailIsSetError);
        
    }

        
    @Test
    @DisplayName("Email verification request must fail when already verified")
    public void testEmailVerificationRequestWhenAlreadyVerified() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.ACTIVE.getValue(), // user is active
                        email.getValue(), // email is set
                        true, // email is verified
                        null,
                        null,
                        LocalDateTime.MAX,
                        LocalDateTime.MIN,
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        final Result result = user.requestEmailVerification();
        
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof EmailIsAlreadyVerifiedError);
        
    }

        
    @Test
    @DisplayName("Email verification request must fail when previous request is not expired")
    public void testEmailVerificationRequestWhenPrevRequestIsNotExpired() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.ACTIVE.getValue(), // user is active
                        email.getValue(), // email is set
                        false, // email is not verified
                        null,
                        null,
                        LocalDateTime.MAX,
                        LocalDateTime.MIN,
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        Result result;
        
        result = user.requestEmailVerification();
        assertTrue(result.isSuccess);
        
        result = user.requestEmailVerification();
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof EmailVerificationLinkIsNotExpiredError);
        
    }

        
    @Test
    @DisplayName("Email verification must fail when user is not active")
    public void testEmailVerificationWhenUserIsNotActive() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.BLOCKED.getValue(), // user is not active
                        email.getValue(), // email is set
                        false, // email is not verified
                        null,
                        null,
                        LocalDateTime.MAX,
                        LocalDateTime.MIN,
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        final Result result = user.verifyEmail(invalidEmailVerificationLink);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof UserIsNotActiveError);
        
    }

        
    @Test
    @DisplayName("Email verification must fail when no email is set")
    public void testEmailVerificationWhenNoEmailIsSet() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.ACTIVE.getValue(), // user is active
                        null, // no email is set
                        false, // email is not verified
                        null,
                        null,
                        LocalDateTime.MAX,
                        LocalDateTime.MIN,
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        final Result result = user.verifyEmail(invalidEmailVerificationLink);
        
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof NoEmailIsSetError);
        
    }

        
    @Test
    @DisplayName("Email verification must fail when email is already verified")
    public void testEmailVerificationWhenEmailIsAlreadyVerified() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.ACTIVE.getValue(), // user is active
                        email.getValue(), // email is set
                        true, // email is verified
                        null,
                        null,
                        LocalDateTime.MAX,
                        LocalDateTime.MIN,
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        final Result result = user.verifyEmail(invalidEmailVerificationLink);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof EmailIsAlreadyVerifiedError);
        
    }

        
    @Test
    @DisplayName("Email verification must fail when no verification request is requested")
    public void testEmailVerificationWhenNoVerificationIsRequested() {
        
            final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.ACTIVE.getValue(), // user is active
                        email.getValue(), // email is set
                        false, // email is not verified
                        null,
                        null,
                        LocalDateTime.MAX,
                        LocalDateTime.MIN,
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null, // no email verification link requested
                        null
                ).getValue();
        
        final Result result = user.verifyEmail(invalidEmailVerificationLink);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof EmailVerificationLinkDoesNotExistError);
        
    }
    
    @Test
    @DisplayName("Email verification must fail when verification link is expired")
    public void testEmailVerificationWhenVerificationLinkIsExpired() {
        
        final DateTime expiredDateTime =
                DateTime.create(
                    LocalDateTime
                            .now()
                            .minus(
                                    EmailVerification.LINK_VALID_DURATION
                                    .plusSeconds(1)
                            )
                ).getValue();
                
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.ACTIVE.getValue(), // user is active
                        email.getValue(),
                        false,
                        null,
                        null,
                        LocalDateTime.MAX,
                        LocalDateTime.MIN,
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        EmailVerification.create(
                                invalidEmailVerificationLink,
                                expiredDateTime
                        ).getValue(),
                        null
                ).getValue();
        
        final Result result = user.verifyEmail(invalidEmailVerificationLink);
        
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof EmailVerificationLinkIsExpiredError);
        
    }
    
    @Test
    @DisplayName("Email verification must fail when email is incorrect")
    public void testEmailVerificationWhenLinkIsIncorrect() {
        
        final DateTime issuedDateTime =
                DateTime.create(
                        LocalDateTime
                                .now()
                                .minus(
                                        Duration
                                                .from(EmailVerification.LINK_VALID_DURATION)
                                                .minusSeconds(1)
                                )
                ).getValue();
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.ACTIVE.getValue(),
                        email.getValue(),
                        false,
                        null,
                        null,
                        LocalDateTime.MAX,
                        LocalDateTime.MIN,
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        EmailVerification.create(
                                validEmailVerificationLink,
                                issuedDateTime
                        ).getValue(),
                        null
                ).getValue();
        
        final Result result = user.verifyEmail(invalidEmailVerificationLink);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof EmailVerificationLinkIsInvalidError);
        
    }

        
    @Test
    @DisplayName("Email must be unverified upon change/deletion")
    public void testEmailVerificationStateUponChangeOrDeletion() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.ACTIVE.getValue(), // user is active
                        email.getValue(), // email is set
                        true, // email is verified
                        null,
                        null,
                        LocalDateTime.MAX,
                        LocalDateTime.MIN,
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        final Email newEmail = Email.create("amirh717@gmail.com").getValue();
        
        assertTrue(user.isEmailVerified());
        
        final Result result = user.changeEmail(newEmail, user);
        assertTrue(result.isSuccess);
        assertFalse(user.isEmailVerified());
        
    }

        
    @Test
    @DisplayName("Email must fail when user is not active and must proceed when the user taking the action is an admin")
    public void testEmailChangeOrDeletionWhenUserIsNotActive() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        UserRole.SUBSCRIBER.getValue(), // user is a subscriber
                        UserState.BLOCKED.getValue(), // user is not active
                        email.getValue(), // email is set
                        true, // email is verified
                        null,
                        null,
                        LocalDateTime.MAX,
                        LocalDateTime.MIN,
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        Result result;
        
        result = user.changeEmail(email, user);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof UserIsNotActiveError);
        
        result = user.changeEmail(email, admin);
        assertTrue(result.isSuccess);
        
    }
    
//    D. Account Verification
    
    @Test
    @DisplayName("Account verification request must fail when account is verified(active or blocked)")
    public void testAccountVerificationRequestWhenAccountIsAlreadyVerified() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.BLOCKED.getValue(), // user is blocked(but verified)
                        email.getValue(),
                        true,
                        null,
                        null,
                        DateTime.createNow().getValue(),
                        DateTime.createNow().getValue(),
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();

        final Result result = user.requestUserVerification();
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof UserIsAlreadyVerifiedError);
        
    }
    
    @Test
    @DisplayName("Account verification request must fail when previous request is not expired")
    public void testAccountVerificationRequestWhenPrevRequestIsNotExpired() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.NOT_VERIFIED.getValue(), // user is not verified
                        null,
                        false,
                        null,
                        null,
                        DateTime.createNow().getValue(),
                        DateTime.createNow().getValue(),
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        Result result;
        
        result = user.requestUserVerification();
        assertTrue(result.isSuccess);
        
        result = user.requestUserVerification();
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof AccountVerificationIsNotExpiredError);
        
        
    }
    
    @Test
    @DisplayName("Account verification must fail when already verified(active or blocked)")
    public void testAccountVerificationWhenAccountIsAlreadyVerified() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.ACTIVE.getValue(), // user is active(verified)
                        email.getValue(),
                        true,
                        null,
                        null,
                        DateTime.createNow().getValue(),
                        DateTime.createNow().getValue(),
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        final Result result = user.verify(1111);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof UserIsAlreadyVerifiedError);
        
    }
    
    @Test
    @DisplayName("Account verification must fail when no verification request is requested")
    public void testAccountVerificationWhenNoRequestIsMade() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.NOT_VERIFIED.getValue(),
                        email.getValue(),
                        true,
                        null,
                        null,
                        DateTime.createNow().getValue(),
                        DateTime.createNow().getValue(),
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        final Result result = user.verify(1111);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof AccountVerificationRequestDoesNotExistError);
        
    }
    
    @Test
    @DisplayName("Account verification must fail when verification code is expired")
    public void testAccountVerificationWhenVerificationRequestIsExpired() {
        
        final DateTime expiredDateTime =
                DateTime
                        .create(
                                LocalDateTime
                                        .now()
                                        .minus(
                                                UserVerification.CODE_VALID_DURATION
                                        ).minusSeconds(1)
                        ).getValue();
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.NOT_VERIFIED.getValue(),
                        email.getValue(),
                        true,
                        null,
                        null,
                        DateTime.createNow().getValue(),
                        DateTime.createNow().getValue(),
                        creatorId.toValue(),
                        updaterId.toValue(),
                        UserVerification.create(
                                expiredDateTime,
                                1111
                        ),
                        null,
                        null
                ).getValue();
        
        final Result result = user.verify(1111);
        assertTrue(result.isFailure);
        System.out.println(result.getError());
        assertTrue(result.getError() instanceof AccountVerificationCodeIsExpiredError);
        
    }

    @Test
    @DisplayName("Account verification code must fail when verification code is incorrect")
    public void testAccountVerificationWhenVerificationCodeIsIncorrect() {
        
        final DateTime issuedAtDateTime =
                DateTime
                        .create(
                                LocalDateTime
                                        .now()
                                        .minus(
                                                UserVerification.CODE_VALID_DURATION
                                        ).plusSeconds(1)
                        ).getValue();
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.NOT_VERIFIED.getValue(), // user is not verified
                        email.getValue(),
                        false,
                        null,
                        null,
                        DateTime.createNow().getValue(),
                        DateTime.createNow().getValue(),
                        creatorId.toValue(),
                        updaterId.toValue(),
                        UserVerification.create(
                                issuedAtDateTime,
                                1111
                        ),
                        null,
                        null
                ).getValue();
        
        final Result result = user.verify(1112);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof AccountVerificationCodeIsIncorrectError);
        
    }
    
//    E. Password Change/Reset
    
    @Test
    @DisplayName("Password reset request must fail when user is not active")
    public void testPasswordResetRequestWhenUserIsNotActive() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.BLOCKED.getValue(), // user is not active
                        email.getValue(),
                        true,
                        null,
                        null,
                        DateTime.createNow().getValue(),
                        DateTime.createNow().getValue(),
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        final Result result = user.requestPasswordReset();
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof UserIsNotActiveError);
        
    }
    
    @Test
    @DisplayName("Password reset must fail when user is not active")
    public void testPasswordResetWhenUserIsNotActive() {
        
        final DateTime issuedAtDateTime =
                DateTime
                        .create(
                                LocalDateTime
                                    .now()
                        ).getValue();
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.BLOCKED.getValue(), // user is not active
                        email.getValue(),
                        true,
                        null,
                        null,
                        DateTime.createNow().getValue(),
                        DateTime.createNow().getValue(),
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        PasswordResetCode.create(
                                1111,
                                issuedAtDateTime
                        ).getValue()
                ).getValue();
        
        final Password newPassword = Password.create("1234567890", false).getValue();
        
        final Result result = user.resetPassword(newPassword, 1111);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof UserIsNotActiveError);
        
    }
    
    @Test
    @DisplayName("Password reset must fail when reset code is expired")
    public void testPasswordResetWhenResetCodeIsExpired() {
        
        final DateTime expiredDateTime =
                DateTime
                        .create(
                                LocalDateTime.now()
                                    .minus(
                                            Duration
                                            .from(PasswordResetCode.CODE_VALID_DURATION)
                                            .plusSeconds(1)
                                    )
                        )
                        .getValue();
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.ACTIVE.getValue(), // user is active
                        email.getValue(),
                        true,
                        null,
                        null,
                        DateTime.createNow().getValue(),
                        DateTime.createNow().getValue(),
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        PasswordResetCode
                                .create(
                                        1111,
                                        expiredDateTime
                        ).getValue()
                ).getValue();
        
        final Password newPassword = Password.create("1234567890", false).getValue();
        
        final Result result =
                user.resetPassword(newPassword, 1111);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof PasswordResetRequestIsExpiredError);
        
    }
    
    @Test
    @DisplayName("Password reset must fail when reset code is incorrect")
    public void testPasswordResetWhenResetCodeIsIncorrect() {
        
        final DateTime issuedAtDateTime =
                DateTime.createNow();
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.ACTIVE.getValue(), // user is active
                        email.getValue(),
                        true,
                        null,
                        null,
                        DateTime.createNow().getValue(),
                        DateTime.createNow().getValue(),
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        PasswordResetCode
                                .create(
                                        4501,
                                        issuedAtDateTime
                        ).getValue()
                ).getValue();
        
        final Password newPassword = Password.create("0987654321", false).getValue();
        
        final Result result = user.resetPassword(newPassword, 4500);
        assertTrue(result.isFailure);
        System.out.println(result.getError());
        assertTrue(result.getError() instanceof PasswordResetCodeIsIncorrectError);
        
    }
    
    @Test
    @DisplayName("Password reset must fail when no reset request is requested")
    public void testPasswordResetWhenNoResetRequestIsRequested() {
        
                
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.ACTIVE.getValue(), // user is active
                        email.getValue(),
                        true,
                        null,
                        null,
                        DateTime.createNow().getValue(),
                        DateTime.createNow().getValue(),
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null // no password reset request
                ).getValue();
        
        final Password newPassword = Password.create("0987654321", false).getValue();
        
        final Result result = user.resetPassword(newPassword, 1111);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof PasswordResetRequestDoesNotExistError);
        
    }
    
    @Test
    @DisplayName("Password change must fail when user is not active (unless an admin is taking the action)")
    public void testPasswordChangeWhenUserIsNotActive() {
        
                
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.BLOCKED.getValue(), // user is not active
                        email.getValue(),
                        true,
                        null,
                        null,
                        DateTime.createNow().getValue(),
                        DateTime.createNow().getValue(),
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        Result result;
        
        final Password newPassword = Password.create("0987654321", false).getValue();
        
        // user changing password
        result = user.changePassword(newPassword, user.getPassword(), user);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof UserIsNotActiveError);
        
        // admin changing password
        result = user.changePassword(newPassword, user.getPassword(), admin);
        assertTrue(result.isSuccess);
        
    }
    
    @Test
    @DisplayName("Password change must fail when currentPassword is incorrect")
    public void testPasswordChangeWhenCurrentPasswordIsIncorrect() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.ACTIVE.getValue(), // user is active
                        email.getValue(),
                        true,
                        null,
                        null,
                        DateTime.createNow().getValue(),
                        DateTime.createNow().getValue(),
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        final Password incorrectPassword = Password.create("kjdiubdiufb", false).getValue();
        final Password newPassword = Password.create("12345678", false).getValue();
        
        final Result result = user.changePassword(newPassword, incorrectPassword, user);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof PasswordIsIncorrectError);
        
    }
    
//    F. Profile Editing
    
    @Test
    @DisplayName("Profile editing must fail when user is not active (unless an admin is taking the action)")
    public void testProfileEditingWhenUserIsNotActive() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        role.getValue(),
                        UserState.BLOCKED.getValue(), // user is not active
                        email.getValue(),
                        true,
                        null,
                        null,
                        DateTime.createNow().getValue(),
                        DateTime.createNow().getValue(),
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        final UserName newName = UserName.create("Dada").getValue();
        
        Result result;
        
        // user editing profile
        result = user.editProfile(newName, user);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof UserIsNotActiveError);
        
        // admin editing profile
        result = user.editProfile(newName, admin);
        assertTrue(result.isSuccess);
        
    }
    
//    G. Role Change
    
    @Test
    @DisplayName("Role change must fail when the user taking the action is not an admin")
    public void testRoleChangeWhenUserTakingActionIsNotAnAdmin() {
        
        final User user =
                User.reconstitute(
                        id.toValue(),
                        name.getValue(),
                        mobile.getValue(),
                        password.getValue(),
                        UserRole.SUBSCRIBER.getValue(),
                        UserState.ACTIVE.getValue(), // user is active
                        email.getValue(),
                        true,
                        null,
                        null,
                        DateTime.createNow().getValue(),
                        DateTime.createNow().getValue(),
                        creatorId.toValue(),
                        updaterId.toValue(),
                        null,
                        null,
                        null
                ).getValue();
        
        Result result;
        
        result = user.changeRole(UserRole.ADMIN, user);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof AccessDeniedError);
        
        result = user.changeRole(UserRole.ADMIN, admin);
        assertTrue(result.isFailure);
        assertTrue(result.getError() instanceof OnlyOneAdminCanExistError);
        
        result = user.changeRole(UserRole.LIBRARY_MANAGER, admin);
        assertTrue(result.isSuccess);
        
    }

    
}
