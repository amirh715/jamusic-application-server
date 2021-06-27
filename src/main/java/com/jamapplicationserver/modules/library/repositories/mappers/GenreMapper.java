/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories.mappers;

import com.jamapplicationserver.infra.Persistence.database.Models.GenreModel;
import com.jamapplicationserver.modules.library.domain.core.*;

/**
 *
 * @author dada
 */
public class GenreMapper {
    
    public static final Genre toDomain(GenreModel model) {
        
        final Genre entity =
                Genre.reconstitute(
                        model.getId(),
                        model.getTitle(),
                        model.getTitleInPersian(),
                        model.getParentGenre() != null ? toDomain(model.getParentGenre()) : null,
                        model.getCreatedAt(),
                        model.getLastModifiedAt()
                ).getValue();
        
        return entity;
    }
    
    public static final GenreModel toPersistence(Genre entity) {
        
        if(entity == null) return null;
        
        final GenreModel model = new GenreModel();
        
        model.setId(entity.id.toValue());
        model.setTitle(entity.getTitle() != null ? entity.getTitle().getValue() : null);
        model.setTitleInPersian(entity.getTitle() != null ? entity.getTitleInPersian().getValue() : null);
        model.setParentGenre(GenreMapper.toPersistence(entity.getParent()));
        model.setCreatedAt(entity.getCreatedAt().getValue());
        model.setLastModifiedAt(entity.getLastModifiedAt().getValue());
        
        return model;
    }
    
    public static final GenreModel mergeForPersistence(Genre entity, GenreModel model) {
        
        model.setId(entity.id.toValue());
        model.setTitle(entity.getTitle().getValue());
        model.setTitleInPersian(entity.getTitleInPersian().getValue());
        model.setParentGenre(GenreMapper.mergeForPersistence(entity.getParent(), model.getParentGenre()));
        model.setCreatedAt(entity.getCreatedAt().getValue());
        model.setLastModifiedAt(entity.getLastModifiedAt().getValue());
        
        return model;
    }

    
}
