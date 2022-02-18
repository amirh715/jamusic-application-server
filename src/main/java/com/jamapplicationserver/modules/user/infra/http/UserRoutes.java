/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.http;

import com.jamapplicationserver.modules.user.usecases.ActivateBlockUser.ActivateBlockUserController;
import com.jamapplicationserver.modules.user.usecases.ChangePassword.ChangePasswordController;
import spark.RouteGroup;
import static spark.Spark.*;
import com.jamapplicationserver.modules.user.usecases.CreateUser.CreateUserController;
import com.jamapplicationserver.modules.user.usecases.EditUser.EditUserController;
import com.jamapplicationserver.modules.user.usecases.GetUsersByFilters.GetUsersByFiltersController;
import com.jamapplicationserver.modules.user.usecases.GetUserByID.GetUserByIDController;
import com.jamapplicationserver.modules.user.usecases.Login.LoginController;
import com.jamapplicationserver.modules.user.usecases.RemoveUser.RemoveUserController;
import com.jamapplicationserver.modules.user.usecases.RequestAccountVerificationCode.RequestAccountVerificationCodeController;
import com.jamapplicationserver.modules.user.usecases.RequestEmailVerificationUseCase.RequestEmailVerificationController;
import com.jamapplicationserver.modules.user.usecases.RequestPasswordReset.RequestPasswordResetController;
import com.jamapplicationserver.modules.user.usecases.ResetPassword.ResetPasswordController;
import com.jamapplicationserver.modules.user.usecases.VerifyAccount.VerifyAccountController;
import com.jamapplicationserver.modules.user.usecases.VerifyEmail.VerifyEmailController;
import com.jamapplicationserver.modules.user.usecases.GetUserProfileImage.GetUserProfileImageController;
import com.jamapplicationserver.modules.user.usecases.GetMyProfileInfo.GetMyProfileInfoController;
import com.jamapplicationserver.modules.user.usecases.GetMyProfileImage.GetMyProfileImageController;
import com.jamapplicationserver.modules.user.usecases.GetUserLoginAudits.GetUserLoginAuditsController;
import com.jamapplicationserver.modules.user.usecases.GetAllLoginAudits.GetAllLoginAuditsController;
import com.jamapplicationserver.modules.user.usecases.UpdateFCMToken.UpdateFCMTokenController;

/**
 *
 * @author amirhossein
 */
public class UserRoutes implements RouteGroup {
    
    private UserRoutes() {
    }
    
    @Override
    public void addRoutes() {
        
        before("/*", (req, res) -> System.out.println(req.url()));
        
        // create new user
        post(
                UserPaths.CREATE_NEW_USER,
                CreateUserController.getInstance()
        );
        
        // get users using the provided filters
        get(
                UserPaths.GET_ALL_USERS_BY_FILTERS,
                GetUsersByFiltersController.getInstance()
        );
        
        // get my profile info
        get(
                UserPaths.GET_MY_PROFILE_INFO,
                GetMyProfileInfoController.getInstance()
        );
        
        // get user by id
        get(
                UserPaths.GET_USER_BY_ID,
                GetUserByIDController.getInstance()
        );
        
        // get user login audits
        get(
                UserPaths.GET_LOGIN_AUDITS,
                GetUserLoginAuditsController.getInstance()
        );
        
        get(
                UserPaths.GET_ALL_LOGINS,
                GetAllLoginAuditsController.getInstance()
        );
        
        // edit user
        put(UserPaths.EDIT_USER,
                EditUserController.getInstance()
        );
        
        // activate/block user
        put(
                UserPaths.ACTIVATE_BLOCK_USER,
                ActivateBlockUserController.getInstance()
        );
        
        // get my profile image
        get(
                UserPaths.GET_MY_PROFILE_IMAGE,
                GetMyProfileImageController.getInstance()
        );
        
        // get profile image
        get(
                UserPaths.GET_PROFILE_IMAGE,
                GetUserProfileImageController.getInstance()
        );
        
        // request account verification
        post(
                UserPaths.REQUEST_ACCOUNT_VERIFICATION,
                RequestAccountVerificationCodeController.getInstance()
        );
        
        // verify account
        put(
                UserPaths.VERIFY_ACCOUNT,
                VerifyAccountController.getInstance()
        );
        
        // request email verification
        post(
                UserPaths.REQUEST_EMAIL_VERIFICATION,
                RequestEmailVerificationController.getInstance()
        );
        
        // verify email
        get(
                UserPaths.VERIFY_EMAIL,
                VerifyEmailController.getInstance()
        );
        
        // request password reset code
        post(
                UserPaths.REQUEST_PASSWORD_RESET_CODE,
                RequestPasswordResetController.getInstance()
        );
        
        // reset password
        put(
                UserPaths.RESET_PASSWORD,
                ResetPasswordController.getInstance()
        );
        
        put(
                UserPaths.CHANGE_PASSWORD,
                ChangePasswordController.getInstance()
        );
        
        // login
        post(
                UserPaths.LOGIN,
                LoginController.getInstance()
        );
        
        // update fcm token
        post(
                UserPaths.UPDATE_FCM_TOKEN,
                UpdateFCMTokenController.getInstance()
        );
        
        // remove user
        delete(
                UserPaths.REMOVE_USER,
                RemoveUserController.getInstance()
        );
        
    }
    
    public static UserRoutes getInstance() {
        return UserRoutesHolder.INSTANCE;
    }
    
    private static class UserRoutesHolder {

        private static final UserRoutes INSTANCE = new UserRoutes();
    }
}
