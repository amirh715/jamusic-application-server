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
public class EditGenreRequestDTO implements IDTO {
    
    public final String id;
    public final String title;
    public final String titleInPersian;
    
    public EditGenreRequestDTO(String id, String title, String titleInPersian) {
        this.id = id;
        this.title = title;
        this.titleInPersian = titleInPersian;
    }
    
}
