/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.Jobs;

import org.quartz.*;
import javax.persistence.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;

/**
 *
 * @author dada
 */
public class UpdateArtistDurationJob implements Job {
    
    @Override
    public void execute(JobExecutionContext context) {
        
        final EntityManager em =
                EntityManagerFactoryHelper.getInstance().createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            tnx.begin();
            
            final String query =
                    "UPDATE ArtistModel artists "
                    + "SET artists.duration = subQuery.total_artworks_duration "
                    + "FROM "
                    + "(SELECT tracks.artist_id, SUM(tracks.duration) total_artworks_duration "
                    + "FROM library_entities tracks "
                    + "WHERE tracks.entity_type = 'T' "
                    + "GROUP BY (tracks.artist_id) "
                    + "ORDER BY (total_artworks_duration) desc) subQuery "
                    + "WHERE artists.id = subQuery.artist_id";
            em.createNativeQuery(query)
                    .executeUpdate();
            
            tnx.commit();
            
        } catch(Exception e) {
            tnx.rollback();
        } finally {
            em.close();
        }
        
    }
    
}
