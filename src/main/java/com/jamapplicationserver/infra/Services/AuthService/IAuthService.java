/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Services.AuthService;

import com.jamapplicationserver.core.infra.AccessControlPolicy;

/**
 *
 * @author amirhossein
 */
public interface IAuthService {
    
    boolean canAccess(AccessControlPolicy acp);
    
}
