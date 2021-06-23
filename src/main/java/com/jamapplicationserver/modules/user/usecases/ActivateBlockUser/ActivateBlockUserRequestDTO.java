/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.ActivateBlockUser;

import com.jamapplicationserver.core.domain.IDTO;

/**
 *
 * @author amirhossein
 */
public class ActivateBlockUserRequestDTO implements IDTO {
    
    public final String id;
    public final String state;
    public final String updaterId;
    
    public ActivateBlockUserRequestDTO(String id, String state, String updaterId) {
        this.id = id;
        this.state = state;
        this.updaterId = updaterId;
    }
    
}
