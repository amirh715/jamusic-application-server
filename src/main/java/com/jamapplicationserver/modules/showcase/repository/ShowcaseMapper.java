/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.repository;

import com.jamapplicationserver.modules.showcase.domain.Showcase;
import com.jamapplicationserver.infra.Persistence.database.Models.ShowcaseModel;

/**
 *
 * @author dada
 */
public class ShowcaseMapper {
    
    public static Showcase toDomain(ShowcaseModel model) {
        
        final Showcase entity =
                Showcase.reconstitute(
                        model.getId(),
                        model.getIndex(),
                        model.getTitle(),
                        model.getDescription(),
                        model.getRoute(),
                        model.getImagePath(),
                        model.getInteractionCount(),
                        model.getCreatedAt(),
                        model.getLastModifiedAt(),
                        model.getCreator().getId()
                )
                .getValue();
        
        return entity;
    }
    
    
    public static ShowcaseModel toPersistence(Showcase entity) {
        
        final ShowcaseModel model = new ShowcaseModel();
        
        model.setId(entity.id.toValue());
        model.setIndex(entity.getIndex());
        model.setTitle(entity.getTitle());
        model.setDescription(entity.getDescription());
        model.setImagePath(entity.getImagePath());
        model.setRoute(entity.getRoute());
        model.setInteractionCount(entity.getInteractionCount());
        model.setCreatedAt(entity.getCreatedAt().getValue());
        model.setLastModifiedAt(entity.getLastModifiedAt().getValue());
        
        return model;
    }
    
}
