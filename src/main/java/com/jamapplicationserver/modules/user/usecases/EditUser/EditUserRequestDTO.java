/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.EditUser;

import com.jamapplicationserver.core.domain.IDTO;
import java.io.InputStream;

/**
 *
 * @author amirhossein
 */
public class EditUserRequestDTO implements IDTO {
    
    public final String id;
    public final String name;
    public final String email;
    public final Boolean removeEmail;
    public final String updaterId;
    public final InputStream profileImage;
    public final Boolean removeProfileImage;
    
    EditUserRequestDTO(
            String id,
            String name,
            String email,
            boolean removeEmail,
            String updaterId,
            InputStream profileImage,
            boolean removeProfileImage
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.removeEmail = removeEmail;
        this.updaterId = updaterId;
        this.profileImage = profileImage;
        this.removeProfileImage = removeProfileImage;
    }
    
}
