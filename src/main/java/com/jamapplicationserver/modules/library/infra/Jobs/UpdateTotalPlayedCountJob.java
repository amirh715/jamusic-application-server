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
        
        System.out.println("Job: UpdateTotalPlayedCountJob");
        
        final EntityManager em =
                EntityManagerFactoryHelper.getInstance()
                .createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            tnx.begin();
            
            // update tracks total played count
            {
            
                final String query =
                        "UPDATE jamschema.library_entities tracks "
                        + "SET total_played_count = subQuery.total_played_count "
                        + "FROM "
                        + "(SELECT tracks.id track_id, tracks.title, COUNT(pt.played_track_id) total_played_count "
                        + "FROM jamschema.library_entities tracks, jamschema.played_tracks pt "
                        + "WHERE tracks.id = pt.played_track_id "
                        + "GROUP BY (tracks.id)) subQuery "
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
                        + "SELECT SUM(album_tracks.total_played_count) total_played_count, "
                        + "album_tracks.album_id album_id "
                        + "FROM jamschema.library_entities album_tracks "
                        + "WHERE album_tracks.album_id IS NOT NULL "
                        + "GROUP BY (album_tracks.album_id)"
                        + ") subQuery "
                        + "WHERE albums.id = subQuery.album_id";
                em.createNativeQuery(query)
                        .executeUpdate();
            
            }
            
            // update artists total played count
            {
            
                final String query =
                        "UPDATE jamschema.library_entities artists "
                        + "SET total_played_count = subQuery.total_played_count "
                        + "FROM "
                        + "(SELECT artists.id artist_id, tracks.id, COUNT(pt.played_track_id) total_played_count "
                        + "FROM jamschema.library_entities artists, jamschema.library_entities tracks, jamschema.played_tracks pt "
                        + "WHERE artists.id = tracks.artist_id "
                        + "AND tracks.id = pt.played_track_id "
                        + "AND tracks.entity_type = 'T' "
                        + "GROUP BY (artists.id, tracks.id)) subQuery "
                        + "WHERE artists.id = subQuery.artist_id";
                em.createNativeQuery(query)
                        .executeUpdate();
                
            }
            
            tnx.commit();
            
        } catch(Exception e) {
            tnx.rollback();
        } finally {
            em.close();
        }
        
    }
    
}
