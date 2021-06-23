/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import java.util.Set;
import com.jamapplicationserver.modules.library.domain.core.*;

/**
 *
 * @author dada
 */
public interface IGenreRepository {
    
    void save(Genre genre);
    
    boolean exists(GenreTitle title);
    
    Genre fetchByTitle(GenreTitle title);
    
    Set<Genre> fetchAll();
    
    void remove(Genre genre);
    
    
}
