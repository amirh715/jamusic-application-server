/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Services.AuthService;

import java.util.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import org.apache.commons.io.*;
import org.casbin.jcasbin.model.*;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.config.Config;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;
import java.nio.file.Files;
import com.jamapplicationserver.utils.GetResourceAsStreamToFile;

/**
 *
 * @author amirhossein
 */
public class AuthService implements IAuthService {
    
    private Enforcer enforcer;
    
    private AuthService() {
        try {
            final File modelPath = GetResourceAsStreamToFile.toFile("jCasbin/model.conf");
            final File policyPath = GetResourceAsStreamToFile.toFile("jCasbin/policy.csv");
            this.enforcer = new Enforcer(modelPath.getAbsolutePath(), policyPath.getAbsolutePath());
        } catch(Exception e) {
            LogService.getInstance().fatal(e);
            e.printStackTrace();
        }
    }

    @Override
    public boolean canAccess(AccessControlPolicy acp) {
        return enforcer.enforce(acp.accessorRole, acp.controllerName);
    }
    
    public static AuthService getInstance() {
        try {
            if(AuthServiceHolder.INSTANCE == null)
                AuthServiceHolder.INSTANCE = new AuthService();
            return AuthServiceHolder.INSTANCE;
        } catch(Exception e) {
            LogService.getInstance().fatal(e);
            return null;
        }
    }
    
    private static class AuthServiceHolder {
        private static AuthService INSTANCE = new AuthService();
    }
}
