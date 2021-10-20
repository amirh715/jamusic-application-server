/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.Jobs;

import java.util.*;
import java.util.stream.*;
import org.quartz.*;
import javax.persistence.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.infra.Persistence.database.Models.*;

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
                        "SELECT tracks, eg.genre_id "
                        + "FROM jamschema.library_entities tracks, "
                        + "jamschema.entity_genres eg, jamschema.played_tracks pt "
                        + "WHERE tracks.id = pt.played_track_id "
                        + "AND tracks.id = eg.entity_id "
                        + "AND tracks.id = pt.played_track_id "
                        + "AND pt.played_at between NOW() - INTERVAL '7 DAY' AND NOW() "
                        + "GROUP BY (tracks.id, eg.genre_id) "
                        + "ORDER BY (count(pt.played_track_id)) DESC";
//                final List<TrackModel> tracks =
//                        em.createNativeQuery(query, TrackModel.class)
//                        .setParameter(1, genre.getId())
//                        .getResultList();

//                final int tracksCount = tracks.size();
//                final int rateDivision = 11;
//                final int unitRange = tracksCount / rateDivision;
//
//                for(TrackModel track : tracks) {
//                    final int rowIndex = tracks.indexOf(track);
//                    for(int i = 1; i < rateDivision; i++) {
//                        if(rowIndex < i * unitRange) {
//                            final double rate = i / 2;
//                            track.setRate(rate);
//                            break;
//                        }
//                    }
//                    em.merge(track);
//                }



            } // update tracks rate ends

            // update albums rate
            {

                final String query =
                        "UPDATE jamschema.library_entities albums "
                        + "SET albums.rate = subQuery.averageTracksRate "
                        + "FROM (SELECT "
                        + "AVG(tracks.rate) averageTracksRate, "
                        + "tracks.album_id album_id "
                        + "FROM jamschema.library_entities tracks "
                        + "GROUP BY (tracks.album_id)) subQuery "
                        + "WHERE albums.id = subQuery.album_id";
                em.createNativeQuery(query)
                        .executeUpdate();

            } // update albums rate ends
            
            
            
            // update artists rate
            {
            
                final String query =
                        "UPDATE jamschema.library_entities artists "
                        + "SET artists.rate = subQuery.averageArtworksRate "
                        + "FROM (SELECT "
                        + "AVG(artworks.rate) averageArtworksRate, "
                        + "artworks.artist_id artist_id "
                        + "FROM jamschema.library_entities artworks "
                        + "GROUP BY (artworks.artist_id)) subQuery "
                        + "WHERE artworks.id = subQuery.artist_id";
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
