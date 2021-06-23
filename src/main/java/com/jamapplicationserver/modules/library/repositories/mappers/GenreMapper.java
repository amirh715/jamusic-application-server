/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories.mappers;

import java.util.*;
import com.jamapplicationserver.infra.Persistence.database.Models.GenreModel;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.infra.DTOs.entities.GenreDTO;

/**
 *
 * @author dada
 */
public class GenreMapper {
    
    public static final Genre toDomain(GenreModel model) {
        
        final GenreTitle title = GenreTitle.create(model.getTitle()).getValue();
        final GenreTitle titleInPersian = GenreTitle.create(model.getTitleInPersian()).getValue();
        final Genre parent = GenreMapper.toDomain(model);
        
        final Genre entity = Genre.create(title, titleInPersian, parent).getValue();
        
        return entity;
    }
    
    public static final GenreModel toPersistence(Genre entity) {
        
        final GenreModel model = new GenreModel();
        
        model.setTitle(entity.getTitle().getValue());
        model.setTitleInPersian(entity.getTitleInPersian().getValue());
        model.setParentGenre(GenreMapper.toPersistence(entity.getParent()));
        
        return model;
    }
    
    public static final GenreModel mergeForPersistence(Genre entity, GenreModel model) {
        
        model.setTitle(entity.getTitle().getValue());
        model.setTitleInPersian(entity.getTitleInPersian().getValue());
        model.setParentGenre(GenreMapper.mergeForPersistence(entity.getParent(), model.getParentGenre()));
        
        return model;
    }

    
}
