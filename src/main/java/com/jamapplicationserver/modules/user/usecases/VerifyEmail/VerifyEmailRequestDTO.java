/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.VerifyEmail;

/**
 *
 * @author amirhossein
 */
public class VerifyEmailRequestDTO {
    
    public final String token;
    
    public VerifyEmailRequestDTO(
            String token
    ) {
        this.token = token;
    }
    
}
