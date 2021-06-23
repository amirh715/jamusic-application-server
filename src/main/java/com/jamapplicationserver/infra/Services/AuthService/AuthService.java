/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Services.AuthService;

/**
 *
 * @author amirhossein
 */
public class AuthService implements IAuthService {
    
    private AuthService() {
    }
    
    // 1. verify and decode token.
    // 2. control access to resources.
    @Override
    public boolean canAccess() {
        
        return false;
    }
    
    public static AuthService getInstance() {
        return AuthServiceHolder.INSTANCE;
    }
    
    private static class AuthServiceHolder {

        private static final AuthService INSTANCE = new AuthService();
    }
}
