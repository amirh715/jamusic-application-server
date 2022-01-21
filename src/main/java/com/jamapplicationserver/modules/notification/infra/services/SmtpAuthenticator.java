/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.infra.services;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author dada
 */
public class SmtpAuthenticator extends Authenticator {
    
    public SmtpAuthenticator() {
        super();
    }
    
    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        final String username = "dada";
        final String password = "Aspect123";
        if((username != null) && (username.length() > 0) && password != null && password.length() > 0)
            return new PasswordAuthentication(username, password);
        return null;
    }
    
}
