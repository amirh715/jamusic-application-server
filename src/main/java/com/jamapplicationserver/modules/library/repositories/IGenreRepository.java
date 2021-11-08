/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import com.jamapplicationserver.infra.Persistence.database.Models.GenreModel;
import java.util.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.core.infra.IRepository;
import com.jamapplicationserver.core.domain.UniqueEntityId;

/**
 *
 * @author dada
 */
public interface IGenreRepository extends IRepository<Genre> {
    
    Map<UniqueEntityId, Genre> fetchAll();
    
    Set<Genre> fetchByIds(Set<UniqueEntityId> ids);
    
    Genre fetchByTitle(GenreTitle title);
    
    GenreModel toPersistence(Genre entity);
    
    Genre toDomain(GenreModel model);
    
}
