/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.ChangePassword;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;

/**
 *
 * @author amirhossein
 */
public class ChangePasswordRequestDTO extends DTOWithAuthClaims {
    
    public final String id;
    public final String currentPassword;
    public final String newPassword;
    
    public ChangePasswordRequestDTO(
            String id,
            String currentPassword,
            String newPassword,
            UniqueEntityId updaterId,
            UserRole updaterRole
    ) {
        super(updaterId, updaterRole);
        this.id = id;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

}
