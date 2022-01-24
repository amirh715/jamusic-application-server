/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Services.AuthService;

import org.casbin.jcasbin.main.Enforcer;
import com.jamapplicationserver.core.infra.*;

/**
 *
 * @author amirhossein
 */
public class AuthService implements IAuthService {
    
    private Enforcer enforcer;
    
    private AuthService() {
        this.enforcer = new Enforcer("src/main/resources/jCasbin/model.conf", "src/main/resources/jCasbin/policy.csv");
    }

    @Override
    public boolean canAccess(AccessControlPolicy acp) {
        return enforcer.enforce(acp.accessorRole, acp.controllerName);
    }
    
    public static AuthService getInstance() {
        return AuthServiceHolder.INSTANCE;
    }
    
    private static class AuthServiceHolder {

        private static final AuthService INSTANCE = new AuthService();
    }
}
