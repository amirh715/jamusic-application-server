/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.VerifyAccount;

/**
 *
 * @author amirhossein
 */
public class VerifyAccountRequestDTO {
    
    public final String id;
    public final int code;
    
    public VerifyAccountRequestDTO(
            String id,
            String code
    ) {
        this.id = id;
        this.code = Integer.parseInt(code);
    }
    
}
