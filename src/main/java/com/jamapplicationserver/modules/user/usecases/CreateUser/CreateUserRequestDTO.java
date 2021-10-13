/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.CreateUser;

import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.infra.*;

/**
 *
 * @author amirhossein
 */
public class CreateUserRequestDTO extends DTOWithAuthClaims {
    
    public final String name;
    public final String mobile;
    public final String password;
    public final String email;
    public final String role;
    public final String FCMToken;
    
    public final Boolean sendVerificationCode;

    public CreateUserRequestDTO(
            String name,
            String mobile,
            String password,
            String email,
            String role,
            String FCMToken,
            String sendVerificationCode,
            UniqueEntityId creatorId,
            UserRole creatorRole
    ) {
        super(creatorId, creatorRole);
        this.name = name;
        this.mobile = mobile;
        this.password = password;
        this.email = email;
        this.role = role;
        this.FCMToken = FCMToken;
        this.sendVerificationCode =
                Boolean.parseBoolean(sendVerificationCode);
    }

    
}
