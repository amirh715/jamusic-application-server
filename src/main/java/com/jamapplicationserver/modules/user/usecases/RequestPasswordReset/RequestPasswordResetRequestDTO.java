/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.RequestPasswordReset;

/**
 *
 * @author amirhossein
 */
public class RequestPasswordResetRequestDTO {
    
    public final String mobile;
    
    public RequestPasswordResetRequestDTO(String mobile) {
        this.mobile = mobile;
    }
    
}
