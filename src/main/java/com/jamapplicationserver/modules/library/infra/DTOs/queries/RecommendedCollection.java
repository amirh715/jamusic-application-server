/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.DTOs.queries;

import java.util.*;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 * @param <T>
 */
public class RecommendedCollection<T> implements IDTO {
    
    public final String title;
    public final Set<T> items;
    
    public RecommendedCollection(
            String title,
            Set<T> items
    ) {
        this.title = title;
        this.items = items;
    }
    
}
