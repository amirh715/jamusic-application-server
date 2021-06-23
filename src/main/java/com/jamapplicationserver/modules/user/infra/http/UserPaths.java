/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.http;

/**
 *
 * @author amirhossein
 */
public class UserPaths {
    
    public final static String CREATE_NEW_USER = "/";
    
    public final static String GET_ALL_USERS_BY_FILTERS = "/";
    public final static String GET_USER_BY_ID = "/:id";
    
    public final static String EDIT_USER = "/";
    
    public final static String ACTIVATE_BLOCK_USER = "/activate-block";
    
    public final static String GET_PROFILE_IMAGE = "/image/:id";
    
    public final static String REMOVE_USER = "/";
    
    public final static String REQUEST_ACCOUNT_VERIFICATION = "/request-account-verification";
    public final static String VERIFY_ACCOUNT = "/verify-account";
    
    public final static String REQUEST_EMAIL_VERIFICATION = "/request-email-verification";
    public final static String VERIFY_EMAIL = "/verify-email";
    
    public final static String REQUEST_PASSWORD_RESET_CODE = "/request-password-reset";
    public final static String RESET_PASSWORD = "/reset-password";
    public final static String CHANGE_PASSWORD = "/change-password";
    
    public final static String LOGIN = "/login";
    
}
