/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.services;

import java.util.*;
import java.util.stream.*;
import javax.persistence.*;
import java.time.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.infra.Services.LogService.LogService;
import com.jamapplicationserver.modules.library.domain.Player.*;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.LibraryEntityIdAndTitle;
import com.jamapplicationserver.modules.library.repositories.*;

/**
 *
 * @author dada
 */
public class RecommendationService implements IRecommendationService {
    
    private final EntityManagerFactoryHelper emfh;
    private final IPlayerRepository playerRepository;
    
    private RecommendationService(
            EntityManagerFactoryHelper emfh,
            IPlayerRepository playerRepository
    ) {
        this.emfh = emfh;
        this.playerRepository = playerRepository;
    }
    
    @Override
    public Set<RecommendedCollection<LibraryEntityIdAndTitle>> getCollections(
            UniqueEntityId playerId
    ) {
        
        final EntityManager em = emfh.createEntityManager();
        
        final Set<RecommendedCollection<LibraryEntityIdAndTitle>> collections = new HashSet<>();
        
        try {
            
            final Player player = playerRepository.fetchById(playerId);
            if(player == null) return collections;

            if(player.getPlayedTracks().size() < 10) return collections;

            // recommended artworks based on being repetitively played
            {
                final String title = "قفلی زدی";
                final String query = "SELECT le "
                        + "FROM jamschema.library_entities le, jamschema.played_tracks pt "
                        + "WHERE le.id = pt.played_track_id "
                        + "AND pt.player_id = :playerId "
                        + "AND pt.played_at BETWEEN now() - interval '5 day' AND now() "
                        + "AND le.published IS TRUE "
                        + "GROUP BY (le.id) "
                        + "HAVING COUNT(*) > 15";
                final Set<LibraryEntityIdAndTitle> items =
                        (Set<LibraryEntityIdAndTitle>) em.createNativeQuery(query, LibraryEntityModel.class)
                        .setParameter("playerId", playerId.toValue())
                        .setMaxResults(15)
                        .getResultStream()
                        .map(item -> LibraryEntityIdAndTitle.create((LibraryEntityModel) item))
                        .collect(Collectors.toSet());
                if(items.size() > 0)
                    collections.add(new RecommendedCollection(title, items, 3));
            }

            // recommended artworks based on what others are listening to
            {
                final String title = "دیگران گوش می دهند";
                final String query = "SELECT le "
                        + "FROM jamschema.library_entities le, jamschema.played_tracks pt "
                        + "WHERE le.id = pt.played_track_id "
                        + "AND pt.played_at BETWEEN now() - interval '7 day' AND now() "
                        + "AND le.published IS TRUE "
                        + "GROUP BY (le.id) "
                        + "ORDER BY COUNT(*) DESC";
                final Set<LibraryEntityIdAndTitle> items =
                        (Set<LibraryEntityIdAndTitle>) em.createNativeQuery(query, LibraryEntityModel.class)
                        .setMaxResults(15)
                        .getResultStream()
                        .map(item -> LibraryEntityIdAndTitle.create((LibraryEntityModel) item))
                        .collect(Collectors.toSet());
                if(items.size() > 0)
                    collections.add(new RecommendedCollection(title, items, 2));
            }

            // recommended artworks based on their release year
            {
                final String title = "آلبوم ها و آهنگ های جدید";
                final String query = "SELECT artworks FROM ArtworkModel artworks "
                        + "WHERE artworks.releaseDate = :thisYearMonth AND artworks.published IS TRUE";
                final YearMonth thisYearMonth = YearMonth.now();
                final Set<LibraryEntityIdAndTitle> items =
                        em.createQuery(query, ArtworkModel.class)
                        .setParameter("thisYearMonth", thisYearMonth)
                        .setMaxResults(15)
                        .getResultStream()
                        .map(artwork -> LibraryEntityIdAndTitle.create(artwork))
                        .collect(Collectors.toSet());
                if(items.size() > 0)
                    collections.add(new RecommendedCollection(title, items, 1));
            }

            return collections;
            
        } catch(Exception e) {
            LogService.getInstance().fatal(e);
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    public static RecommendationService getInstance() {
        return RecommendationServiceHolder.INSTANCE;
    }
    
    private static class RecommendationServiceHolder {

        private static final RecommendationService INSTANCE =
                new RecommendationService(
                        EntityManagerFactoryHelper.getInstance(),
                        PlayerRepository.getInstance()
                );
    }
}
