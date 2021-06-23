/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.CreateUser;
import com.jamapplicationserver.core.domain.IDTO;
import java.util.*;

/**
 *
 * @author amirhossein
 */
public class CreateUserRequestDTO implements IDTO {
    
    public final String name;
    public final String mobile;
    public final String password;
    public final String email;
    public final String role;
    public final String FCMToken;
    public final String creatorId;
    
    public final String sendVerificationCode;

    public static CreateUserRequestDTO buildFromMap(Map<String, String> map) {
        return new CreateUserRequestDTO(
                map.get("name"),
                map.get("mobile"),
                map.get("password"),
                map.get("email"),
                map.get("role"),
                map.get("FCMToken"),
                map.get("creatorId"),
                map.get("sendVerificationCode")
        );
    }

    private CreateUserRequestDTO(
            String name,
            String mobile,
            String password,
            String email,
            String role,
            String FCMToken,
            String creatorId,
            String sendVerificationCode
    ) {
        this.name = name;
        this.mobile = mobile;
        this.password = password;
        this.email = email;
        this.role = role;
        this.FCMToken = FCMToken;
        this.creatorId = creatorId;
        this.sendVerificationCode = sendVerificationCode;
    }


    
    


    
}
