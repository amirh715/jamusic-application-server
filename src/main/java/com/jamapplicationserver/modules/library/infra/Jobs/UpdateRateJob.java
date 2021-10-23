/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.Jobs;

import javax.persistence.*;
import org.quartz.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;

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
                        "UPDATE jamschema.library_entities "
                        + "SET rate = ratings.rate "
                        + "FROM "
                        + "(SELECT res.track_id track_id, AVG(res.rate) rate "
                        + "FROM ( "
                        + "SELECT tracks.id track_id, "
                        + "eg.genre_id, "
                        + "entity_count_of_each_genre.total_count, "
                        + "((row_number () OVER (PARTITION BY eg.genre_id ORDER BY COUNT(pt.played_track_id)) * 5) / total_count) rate "
                        + "FROM "
                        + "jamschema.library_entities tracks, "
                        + "jamschema.entity_genres eg, "
                        + "jamschema.played_tracks pt, "
                        + "("
                        + "SELECT eg.genre_id genre_id, COUNT(eg.entity_id) total_count "
                        + "FROM "
                        + "jamschema.library_entities le, "
                        + "jamschema.entity_genres eg "
                        + "WHERE le.entity_type = 'T' AND eg.entity_id = le.id "
                        + "GROUP BY (eg.genre_id) "
                        + ") entity_count_of_each_genre "
                        + "WHERE tracks.id = eg.entity_id "
                        + "AND tracks.id = pt.played_track_id "
                        + "AND pt.played_at BETWEEN NOW() - INTERVAL '7 DAY' AND NOW() "
                        + "AND eg.genre_id = entity_count_of_each_genre.genre_id "
                        + "GROUP BY (tracks.id, eg.genre_id, entity_count_of_each_genre.total_count) "
                        + "ORDER BY (row_number () OVER (PARTITION BY eg.genre_id ORDER BY COUNT(pt.played_track_id))) DESC "
                        + ") res "
                        + "GROUP BY (res.track_id)"
                        + ") ratings "
                        + "WHERE id = ratings.track_id";
                em.createNativeQuery(query)
                        .executeUpdate();

            } // update tracks rate ends

            // update albums rate
            {

                final String query =
                        "UPDATE jamschema.library_entities "
                        + "SET rate = subQuery.averageTracksRate "
                        + "FROM (SELECT "
                        + "AVG(tracks.rate) averageTracksRate, "
                        + "tracks.album_id album_id "
                        + "FROM jamschema.library_entities tracks "
                        + "GROUP BY (tracks.album_id)) subQuery "
                        + "WHERE id = subQuery.album_id ";
                em.createNativeQuery(query)
                        .executeUpdate();

            } // update albums rate ends
            
            // update artists rate
            {
            
                final String query =
                        "UPDATE jamschema.library_entities "
                        + "SET rate = subQuery.averageArtworksRate "
                        + "FROM (SELECT "
                        + "AVG(artworks.rate) averageArtworksRate, "
                        + "artworks.artist_id artist_id "
                        + "FROM jamschema.library_entities artworks "
                        + "GROUP BY (artworks.artist_id)) subQuery "
                        + "WHERE id = subQuery.artist_id";
                em.createNativeQuery(query)
                        .executeUpdate();
                
            }
            
            tnx.commit();
            
        } catch(Exception e) {
            e.printStackTrace();
            tnx.rollback();
        } finally {
            em.close();
        }
        
    }
    
}
