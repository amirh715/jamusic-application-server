/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.usecases;

import com.jamapplicationserver.core.domain.IDTO;

/**
 *
 * @author dada
 */
public class RemoveLibraryEntityRequestDTO implements IDTO {
    
    public final String id;
    public final String updaterId;
    
    public RemoveLibraryEntityRequestDTO(String id, String updaterId) {
        this.id = id;
        this.updaterId = updaterId;
    }
    
}
