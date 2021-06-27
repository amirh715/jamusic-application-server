/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import java.util.Set;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.core.domain.UniqueEntityID;
import com.jamapplicationserver.core.infra.IRepository;

/**
 *
 * @author dada
 */
public interface IGenreRepository extends IRepository<Genre> {
    
    Genre fetchByTitle(GenreTitle title);
    
    Set<Genre> fetchAll();
    
    
}
