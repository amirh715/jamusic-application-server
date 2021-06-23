/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.RemoveUser;

import com.jamapplicationserver.core.domain.IDTO;

/**
 *
 * @author dada
 */
public class RemoveUserRequestDTO implements IDTO {
    
    public final String id;
    public final String updater;
    
    public RemoveUserRequestDTO(String id, String updater) {
        this.id = id;
        this.updater = updater;
    }
    
}
