/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.services;

import java.util.*;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.RecommendedCollection;

/**
 *
 * @author dada
 */
public interface IRecommendationService {
    
    Set<RecommendedCollection<LibraryEntityIdAndTitle>> getCollections(UniqueEntityId playerId);
    
}
