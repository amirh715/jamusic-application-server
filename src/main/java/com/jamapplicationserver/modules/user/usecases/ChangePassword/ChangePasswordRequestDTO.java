/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.ChangePassword;

import com.jamapplicationserver.core.domain.IDTO;

/**
 *
 * @author amirhossein
 */
public class ChangePasswordRequestDTO implements IDTO {
    
    public final String id;
    public final String currentPassword;
    public final String newPassword;
    public final String updaterId;
    
    public ChangePasswordRequestDTO(
            String id,
            String currentPassword,
            String newPassword,
            String updaterId
    ) {
        this.id = id;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.updaterId = updaterId;
    }

}
