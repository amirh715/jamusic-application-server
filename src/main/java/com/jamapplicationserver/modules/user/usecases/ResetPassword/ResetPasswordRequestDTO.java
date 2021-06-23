/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.ResetPassword;

import com.jamapplicationserver.core.domain.IDTO;

/**
 *
 * @author amirhossein
 */
public class ResetPasswordRequestDTO implements IDTO {
    
    public final String mobile;
    public final String resetCode;
    public final String newPassword;
    
    public ResetPasswordRequestDTO(
            String mobile,
            String resetCode,
            String newPassword
    ) {
        this.mobile = mobile;
        this.resetCode = resetCode;
        this.newPassword = newPassword;
    }
    
}
