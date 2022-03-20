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

/**
 *
 * @author amirhossein
 */
public class AuthService implements IAuthService {
    
    private Enforcer enforcer;
    
    private AuthService() {
        final InputStream model = getClass().getClassLoader().getResourceAsStream("jCasbin/model.conf");
        final InputStream policy = getClass().getClassLoader().getResourceAsStream("jCasbin/policy.csv");
        try {
            final Path modelPath = Files.write(Path.of("temp_model"), model.readAllBytes(), StandardOpenOption.CREATE_NEW);
            final Path policyPath = Files.write(Path.of("temp_policy"), policy.readAllBytes(), StandardOpenOption.CREATE_NEW);
            this.enforcer = new Enforcer(modelPath.toAbsolutePath().toString(), policyPath.toAbsolutePath().toString());
        } catch(Exception e) {
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
