/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import java.util.*;
import com.jamapplicationserver.core.domain.DateTime;
import com.jamapplicationserver.modules.library.domain.core.*;

/**
 *
 * @author dada
 */
public class PlayerFilters {
    
    public DateTime withActivityFrom;
    public DateTime withActivityTill;
    
    public DateTime withoutActivityFrom;
    public DateTime withoutActivityTill;

    public Set<Genre> withFavoriteGenres;
    
    public PlayerFilters(
            
    ) {
        
    }
    
    
}
