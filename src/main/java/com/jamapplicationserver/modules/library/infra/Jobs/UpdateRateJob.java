/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.Jobs;

import java.util.*;
import org.quartz.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
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
        
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        
        try {
            
            tnx.begin();
            
            // update tracks rate
            {
                
                final String query =
                        "SELECT tracks.id, eg.genre_id, count(pt.played_track_id) "
                        + "FROM entity_genres eg, played_tracks pt "
                        + "WHERE tracks.id = pt.played_track_id "
                        + "AND eg.entity_id = tracks.id "
                        + "AND pt.played_at between NOW() AND NOW() "
                        + "GROUP BY () "
                        + "ORDER BY ()";
                final List<TrackModel> tracks =
                        em.createNativeQuery(query, TrackModel.class)
                        .getResultList();
                
                final int tracksCount = tracks.size();
                final int rateDivision = 11;
                final int unitRange = tracksCount / rateDivision;
                
                for(TrackModel track : tracks) {
                    final int rowIndex = tracks.indexOf(track);
                    for(int i = 1; i < rateDivision; i++) {
                        if(rowIndex < i * unitRange) {
                            final double rate = i / 2;
                            track.setRate(rate);
                            break;
                        }
                    }
                    em.merge(track);
                }
                
            }
            
            // update albums rate
            {
                
                final String query = "";
                final List<AlbumModel> albums =
                        em.createNativeQuery(query, AlbumModel.class)
                        .getResultList();

                final int albumsCount = albums.size();
                final int rateDivision = 11;
                final int unitRange = albumsCount / rateDivision;

                for(AlbumModel album : albums) {
                    final int rowIndex = albums.indexOf(album);
                    for(int i = 0; i < rateDivision; i++) {
                        if(rowIndex < i * unitRange) {
                            final double rate = i / 2;
                            album.setRate(rate);
                            break;
                        }
                    }
                    em.merge(album);
                }
                
            }
            
            // update artists rate
            {
            
                final String query = "";
                final List<ArtistModel> artists =
                        em.createNativeQuery(query, ArtistModel.class)
                        .getResultList();
                
                final int artistsCount = artists.size();
                final int rateDivision = 11;
                final int unitRange = artistsCount / rateDivision;
                
                for(ArtistModel artist : artists) {
                    final int rowIndex = artists.indexOf(artist);
                    for(int i = 0; i< rateDivision; i++) {
                        if(rowIndex < i * unitRange) {
                            final double rate = i / 2;
                            artist.setRate(rate);
                            break;
                        }
                    }
                }
                
            }
            
            tnx.commit();
            
        } catch(Exception e) {
            tnx.rollback();
        } finally {
            em.close();
        }
        
    }
    
}
