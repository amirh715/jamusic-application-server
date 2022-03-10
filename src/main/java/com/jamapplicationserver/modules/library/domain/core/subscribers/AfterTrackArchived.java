/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core.subscribers;

import java.util.*;
import javax.persistence.*;
import com.jamapplicationserver.core.domain.events.*;
import com.jamapplicationserver.modules.library.domain.core.events.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;

/**
 *
 * @author dada
 */
public class AfterTrackArchived implements IDomainEventHandler<TrackArchived> {
    
    @Override
    public Class<TrackArchived> subscribedToEventType() {
        return TrackArchived.class;
    }
    
    @Override
    public void handleEvent(TrackArchived event) throws Exception {
        
        final EntityManager em =
                EntityManagerFactoryHelper.getInstance().createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        tnx.begin();
        
        try {
            
            // PREFER REPOSITORY OVER DIRECT DATABASE ACCESS (DDD) ?
            // remove archived track from all playlists
            
            final String query = "";
            final int num = em.createQuery(query).executeUpdate();
            System.out.println("Number of removed tracks from playlists: " + num);
            
            tnx.commit();
            
        } catch(Exception e) {
            tnx.rollback();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
}
