/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetUsersByFilters;

import java.util.Set;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.core.domain.IDTO;

/**
 *
 * @author amirhossein
 */
public class GetUsersByFiltersResponseDTO implements IDTO {
    
    public final Set<User> users;
    
    public GetUsersByFiltersResponseDTO(Set<User> users) {
        this.users = users;
    }
    
    
}
