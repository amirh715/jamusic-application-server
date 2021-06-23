/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.ClientVerifier;

import java.util.Set;

/**
 *
 * @author amirhossein
 */
public class CLIENT_VERSIONS {
    
    private static final Set<String> validClientVersions =
            Set.of(
                    "MOBILE_1.0.0",
                    "ADMIN_1.0.0"
            );
    
    public static boolean isClientValid(String id) {
        return validClientVersions.contains(id);
    }
    
}
