/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.entities;

import com.jamapplicationserver.core.domain.IDTO;

/**
 *
 * @author dada
 */
public class GenreDTO implements IDTO {
    
    public String title;
    public String titleInPersian;
    public GenreDTO parentGenre;
    
    public GenreDTO(String title, String titleInPersian, GenreDTO parentGenre) {
        this.title = title;
        this.titleInPersian = titleInPersian;
        this.parentGenre = parentGenre;
    }
    
}
