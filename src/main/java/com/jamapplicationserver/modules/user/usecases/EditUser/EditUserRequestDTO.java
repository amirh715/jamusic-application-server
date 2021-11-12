/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.EditUser;

import java.io.InputStream;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;

/**
 *
 * @author amirhossein
 */
public class EditUserRequestDTO extends DTOWithAuthClaims {
    
    public final String id;
    public final String name;
    public final String email;
    public final Boolean removeEmail;
    public final String role;
    public final InputStream profileImage;
    public final Boolean removeProfileImage;
    
    EditUserRequestDTO(
            String id,
            String name,
            String email,
            boolean removeEmail,
            String role,
            InputStream profileImage,
            boolean removeProfileImage,
            UniqueEntityId updaterId,
            UserRole updaterRole
    ) {
        super(updaterId, updaterRole);
        this.id = id;
        this.name = name;
        this.email = email;
        this.removeEmail = removeEmail;
        this.role = role;
        this.profileImage = profileImage;
        this.removeProfileImage = removeProfileImage;
    }
    
}
