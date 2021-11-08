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
import com.jamapplicationserver.infra.Persistence.database.Models.ArtworkModel;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.library.domain.Player.*;
import com.jamapplicationserver.modules.library.infra.DTOs.queries.LibraryEntityIdAndTitle;
import com.jamapplicationserver.modules.library.repositories.*;

/**
 *
 * @author dada
 */
public class RecommendationService implements IRecommendationService {
    
    private final EntityManagerFactoryHelper emfh;
    private final IGenreRepository genreRepository;
    private final IPlayerRepository playerRepository;
    
    private RecommendationService(
            EntityManagerFactoryHelper emfh,
            IGenreRepository genreRepository,
            IPlayerRepository playerRepository
    ) {
        this.emfh = emfh;
        this.genreRepository = genreRepository;
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

            if(player.getPlayedTracks().size() < 20) return collections;

//            // recommended artworks based on favorite genres
//            {
//                final String title = "شاید از اینا هم خوشت بیاد";
//                final Map<GenreModel, Long> favoriteGenres =
//                    player.getPlayedTracks().stream()
//                    // filter played tracks within the past week
//                    .filter(played -> {
//                        final LocalDateTime playedAt =
//                                played.getPlayedAt().getValue();
//                        final LocalDateTime thePastWeek =
//                                LocalDateTime.now()
//                                .minusDays(5);
//                        return playedAt.isAfter(thePastWeek);
//                    })
//                    // extract genres from played tracks genre lists
//                    .flatMap(played -> played.getTrack().getGenres().getValue().stream())
//                    .map(genre -> genreRepository.toPersistence(genre))
//                    // group played genres and count them
//                    .collect(
//                            Collectors.groupingBy(e -> e, Collectors.counting())
//                    );
//                final Set<UUID> playedTracksIds =
//                        player.getPlayedTracks().stream()
//                        .map(played -> played.track.id.toValue())
//                        .collect(Collectors.toSet());
//                final String query = "SELECT artworks FROM ArtworkModel artworks JOIN artworks.genres genres "
//                        + "WHERE genres.id IN (:favoriteGenres) AND "
//                        + "artworks.id NOT IN (:playedTracksIds)";
//                final Set<ArtworkIdAndTitle> items =
//                        em.createQuery(query, ArtworkModel.class)
//                        .setParameter("favoriteGenres",
//                                favoriteGenres.keySet().stream().map(g -> g.getId()).collect(Collectors.toSet())
//                        )
//                        .setParameter("playedTracksIds", playedTracksIds)
//                        .setMaxResults(15)
//                        .getResultStream()
//                        .map(artwork -> ArtworkIdAndTitle.create(artwork))
//                        .collect(Collectors.toSet());
//                collections.add(new RecommendedCollection(title, items));
//            }

            // recommended artworks based on being repetitively played
            {
                final String title = "قفلی زدی";
                final LocalDateTime from = LocalDateTime.now().minusDays(5);
    //            final Set<UUID> repetitivelyPlayedTracksIds =
    //                    player.getPlayedTracks()
    //                    .stream()
    //                    .filter(played -> {
    //                        final LocalDateTime from = LocalDateTime.now().minusDays(5);
    //                        return played.playedAt.getValue().isAfter(from);
    //                    })
    //                    .collect(Collectors.toSet());

    //            if(repetitivelyPlayedTracksIds.isEmpty()) return collections;

                final String query = "SELECT artworks FROM ArtworkModel artworks "
                        + "WHERE artworks.id IN (:repetitivelyPlayedTracksIds)";
                final Set<LibraryEntityIdAndTitle> items =
                        em.createQuery(query, ArtworkModel.class)
                        .setParameter("repetitivelyPlayedTracksIds", null)
                        .setMaxResults(10)
                        .getResultStream()
                        .map(artwork -> LibraryEntityIdAndTitle.create(artwork))
                        .collect(Collectors.toSet());
                collections.add(new RecommendedCollection(title, items));
            }

            // recommended artworks based on what others are listening to
            {
                final String title = "دیگران گوش می دهند";

                final String query = "SELECT artworks "
                        + "FROM ArtworkModel artworks, PlayedModel played "
                        + "WHERE artworks.id = played.playedTrack.id AND "
                        + "played.playedAt BETWEEN :from AND :till AND "
                        + "played.player.id <> :playerId ";
                final LocalDateTime from = LocalDateTime.now().minusDays(5);
                final LocalDateTime till = LocalDateTime.now();
                final Set<LibraryEntityIdAndTitle> items =
                        em.createQuery(query, ArtworkModel.class)
                        .setParameter("from", from)
                        .setParameter("till", till)
                        .setParameter("playerId", playerId.toValue())
                        .setMaxResults(30)
                        .getResultStream()
                        .map(artwork -> LibraryEntityIdAndTitle.create(artwork))
                        .collect(Collectors.toSet());
                collections.add(new RecommendedCollection(title, items));
            }

            // recommended artworks based on their release year
            {
                final String title = "آلبوم ها و آهنگ های جدید";
                final String query = "SELECT artworks FROM ArtworkModel artworks "
                        + "WHERE artworks.releaseDate = :thisYearMonth";
                final YearMonth thisYearMonth = YearMonth.now();
                final Set<LibraryEntityIdAndTitle> items =
                        em.createQuery(query, ArtworkModel.class)
                        .setParameter("thisYearMonth", thisYearMonth)
                        .setMaxResults(20)
                        .getResultStream()
                        .map(artwork -> LibraryEntityIdAndTitle.create(artwork))
                        .collect(Collectors.toSet());
                collections.add(new RecommendedCollection(title, items));
            }

            return collections;
            
        } catch(Exception e) {
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
                        GenreRepository.getInstance(),
                        PlayerRepository.getInstance()
                );
    }
}
