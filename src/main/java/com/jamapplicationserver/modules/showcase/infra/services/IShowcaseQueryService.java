/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.infra.services;

import java.util.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.showcase.infra.DTOs.queries.ShowcaseDetails;

/**
 *
 * @author dada
 */
public interface IShowcaseQueryService {
    
    public Set<ShowcaseDetails> getAllShowcases();
    
    public ShowcaseDetails getShowcaseById(UniqueEntityId id);
    
}
