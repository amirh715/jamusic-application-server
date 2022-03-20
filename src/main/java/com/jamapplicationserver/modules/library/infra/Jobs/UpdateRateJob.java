/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.Jobs;

import javax.persistence.*;
import org.quartz.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author dada
 */
public class UpdateRateJob implements Job {
    
    @Override
    public void execute(JobExecutionContext context) {
        
        final EntityManager em =
                EntityManagerFactoryHelper.getInstance().createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            tnx.begin();

            // update tracks rate
            {

                final String query =
                        "UPDATE jamschema.library_entities SET rate = results.rate "
                        + "FROM "
                        + "("
                        + "SELECT track_rates_in_each_genre.track_id, AVG(track_rates_in_each_genre.rate) rate "
                        + "FROM ("
                        + "SELECT pt.played_track_id track_id, rank() over (partition by eg.genre_id order by COUNT(*)) * 5 / number_of_tracks rate "
                        + "FROM jamschema.played_tracks pt, jamschema.entity_genres eg, "
                        + "(SELECT g.id genre_id, COUNT(*) number_of_tracks FROM jamschema.library_entities le, jamschema.entity_genres eg, jamschema.genres g "
                        + "WHERE le.entity_type = 'T' AND le.id = eg.entity_id AND g.id = eg.genre_id GROUP BY g.id) total_number_of_tracks_per_genre "
                        + "WHERE pt.played_track_id = eg.entity_id AND total_number_of_tracks_per_genre.genre_id = eg.genre_id "
                        + "AND pt.played_at BETWEEN NOW() - interval '20 day' AND NOW() "
                        + "GROUP BY (track_id, eg.genre_id, total_number_of_tracks_per_genre.number_of_tracks)"
                        + ") track_rates_in_each_genre GROUP BY (track_id)"
                        + ") results "
                        + "WHERE id = results.track_id";
                em.createNativeQuery(query)
                        .executeUpdate();

            } // update tracks rate ends

            // update albums rate
            {

                final String query =
                        "UPDATE jamschema.library_entities SET rate = results.rate "
                        + "FROM "
                        + "("
                        + "SELECT album_id, AVG(rate) rate "
                        + "FROM ("
                        + "SELECT tracks.album_id, SUM(total_played_count_of_tracks.total_played_count) total_played_count, "
                        + "rank() over (partition by genre_id order by COUNT(*)) * 5 / number_of_albums rate "
                        + "FROM jamschema.library_entities tracks, "
                        + "(SELECT pt.played_track_id track_id, COUNT(*) total_played_count FROM jamschema.played_tracks pt "
                        + "WHERE pt.played_at BETWEEN NOW() - interval '20 day' AND NOW() GROUP BY pt.played_track_id) total_played_count_of_tracks, "
                        + "(SELECT g.id genre_id, COUNT(*) number_of_albums FROM jamschema.library_entities le, jamschema.entity_genres eg, jamschema.genres g "
                        + "WHERE le.entity_type = 'A' AND le.id = eg.entity_id AND g.id = eg.genre_id GROUP BY g.id) total_number_of_albums_per_genre "
                        + "WHERE tracks.id = total_played_count_of_tracks.track_id AND "
                        + "tracks.album_id IS NOT NULL GROUP BY (tracks.album_id, genre_id, number_of_albums)"
                        + ") subQuery GROUP BY (subQuery.album_id)"
                        + ") results "
                        + "WHERE id = results.album_id";
                em.createNativeQuery(query)
                        .executeUpdate();

            } // update albums rate ends
            
            // update artists rate
            {
            
                final String query =
                        "UPDATE jamschema.library_entities SET rate = subQuery.rate "
                        + "FROM "
                        + "("
                        + "SELECT le.artist_id artist_id, AVG(le.rate) rate "
                        + "FROM jamschema.library_entities le, jamschema.played_tracks pt "
                        + "WHERE le.id = pt.played_track_id "
                        + "AND pt.played_at BETWEEN NOW() - interval '20 day' AND NOW() "
                        + "GROUP BY artist_id"
                        + ") subQuery "
                        + "WHERE id = subQuery.artist_id";
                em.createNativeQuery(query)
                        .executeUpdate();
                
            }
            
            tnx.commit();
            
        } catch(Exception e) {
            LogService.getInstance().warn(e);
            tnx.rollback();
        } finally {
            em.close();
        }
        
    }
    
}
