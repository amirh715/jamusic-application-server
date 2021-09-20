/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.services;

import java.util.*;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.Player.*;

/**
 *
 * @author dada
 */
public interface IRecommendationStrategy {
    
    Set<Artwork> process(Set<PlayedTrack> playedTracks);
    
}
