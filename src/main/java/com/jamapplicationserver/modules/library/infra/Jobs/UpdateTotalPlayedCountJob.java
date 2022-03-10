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
public class UpdateTotalPlayedCountJob implements Job {
    
    @Override
    public void execute(JobExecutionContext context) {
                
        final EntityManager em =
                EntityManagerFactoryHelper.getInstance()
                .createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            tnx.begin();
            
            System.out.println("Job is running");
            
            // update tracks total played count
            {
            
                final String query =
                        "UPDATE jamschema.library_entities tracks "
                        + "SET total_played_count = subQuery.total_played_count "
                        + "FROM "
                        + "("
                        + "SELECT pt.played_track_id track_id, COUNT(*) total_played_count "
                        + "FROM jamschema.played_tracks pt "
                        + "GROUP BY (pt.played_track_id)"
                        + ") subQuery "
                        + "WHERE tracks.id = subQuery.track_id";
                em.createNativeQuery(query)
                        .executeUpdate();
            
            }
            
            // update albums total played count
            {
            
                final String query =
                        "UPDATE jamschema.library_entities albums "
                        + "SET total_played_count = subQuery.total_played_count "
                        + "FROM "
                        + "("
                        + "SELECT tracks.album_id, SUM(total_played_count_of_tracks.total_played_count) total_played_count "
                        + "FROM jamschema.library_entities tracks, "
                        + "("
                        + "SELECT pt.played_track_id track_id, COUNT(*) total_played_count "
                        + "FROM jamschema.played_tracks pt "
                        + "GROUP BY (pt.played_track_id)"
                        + ") total_played_count_of_tracks "
                        + "WHERE tracks.id = total_played_count_of_tracks.track_id "
                        + "AND tracks.album_id IS NOT NULL "
                        + "GROUP BY (tracks.album_id)"
                        + ") subQuery "
                        + "WHERE albums.id = subQuery.album_id";
                em.createNativeQuery(query)
                        .executeUpdate();
            
            }
            
            // update artists total played count
            {
            
                final String query =
                        "UPDATE jamschema.library_entities le "
                        + "SET total_played_count = subQuery.total_played_count "
                        + "FROM "
                        + "("
                        + "SELECT le.artist_id, SUM(total_played_count_of_tracks.total_played_count) total_played_count "
                        + "FROM jamschema.library_entities le, "
                        + "("
                        + "SELECT pt.played_track_id track_id, COUNT(*) total_played_count "
                        + "FROM jamschema.played_tracks pt "
                        + "GROUP BY (pt.played_track_id)"
                        + ") total_played_count_of_tracks "
                        + "WHERE le.id = total_played_count_of_tracks.track_id "
                        + "GROUP BY (le.artist_id)"
                        + ") subQuery "
                        + "WHERE le.id = subQuery.artist_id";
                em.createNativeQuery(query)
                        .executeUpdate();
                
            }
            
            tnx.commit();
            
        } catch(Exception e) {
            tnx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        
    }
    
}
