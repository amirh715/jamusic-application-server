/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.infra;

/**
 *
 * @author dada
 */
public class AccessControlPolicy {
    
    public final String controllerName;
    public final String accessorRole;
    
    public AccessControlPolicy(
            String controllerName,
            String accessorRole
    ) {
        this.controllerName = controllerName;
        this.accessorRole = accessorRole;
    }
    
}
