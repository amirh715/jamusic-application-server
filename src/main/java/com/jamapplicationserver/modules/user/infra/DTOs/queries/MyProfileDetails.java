/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.DTOs.queries;

import java.util.*;
import com.jamapplicationserver.core.infra.IQueryResponseDTO;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class MyProfileDetails implements IQueryResponseDTO {
    
    public final UUID id;
    public final String name;
    public final String email;
    public final Boolean isEmailVerified;
    
    public MyProfileDetails(
            UUID id,
            String name,
            String email,
            Boolean isEmailVerified
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isEmailVerified = isEmailVerified;
    }
    
    @Override
    public MyProfileDetails filter(UserRole role) {
        return this;
    }
    
}
