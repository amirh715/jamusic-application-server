/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.VerifyEmail;

import com.jamapplicationserver.core.domain.IDTO;

/**
 *
 * @author amirhossein
 */
public class VerifyEmailRequestDTO implements IDTO {
    
    public final String link;
    
    public VerifyEmailRequestDTO(
            String link
    ) {
        this.link = link;
    }
    
}
