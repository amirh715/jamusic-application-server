/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import java.util.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.stream.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.library.domain.Player.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.modules.library.domain.Playlist.Playlist;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class PlayerRepository implements IPlayerRepository {
    
    private final EntityManagerFactoryHelper emfh;
    private final ILibraryEntityRepository libraryRepository;
    private final IPlaylistRepository playlistRepository;
    
    private PlayerRepository(
            EntityManagerFactoryHelper emfh,
            ILibraryEntityRepository libraryRepository,
            IPlaylistRepository playlistRepository
    ) {
        this.emfh = emfh;
        this.libraryRepository = libraryRepository;
        this.playlistRepository = playlistRepository;
    }
    
    @Override
    public Player fetchById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {

            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<UserModel> cq = cb.createQuery(UserModel.class);
            final Root root = cq.from(UserModel.class);
            
            final List<Predicate> predicates = new ArrayList<>();
            
            predicates.add(cb.equal(root.get("id"), id.toValue()));
            
            cq.where(
                    cb.and(
                            predicates.toArray(
                                    new Predicate[predicates.size()]
                            )
                    )
            );
            
            final UserModel result = em.createQuery(cq).getSingleResult();
            
            return toDomain(result, em);
            
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    
    @Override
    public boolean exists(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final UserModel model = em.find(UserModel.class, id.toValue());
            
            return model != null;
            
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public Set<Player> fetchByFilters(PlayerFilters filters) {
        
        try {
            
            
            
            
        } catch(Exception e) {
            
        }
        
        return null;
        
    }
    
    @Override
    public void save(Player player) {
        
        final EntityManager em = emfh.createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            tnx.begin();
            
            final UserModel model = toPersistence(player, em);
            
            em.merge(model);
            
            tnx.commit();
            
        } catch(Exception e) {
            e.printStackTrace();
            tnx.rollback();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    public Player toDomain(UserModel model, EntityManager em) {
        
        final Set<PlayedTrack> playedTracks = new HashSet<>();
                
        if(!model.getPlayedTracks().isEmpty()) {
            model.getPlayedTracks()
                    .stream()
                    .map(p -> {
                        final Track playedTrack =
                                (Track) libraryRepository.toDomain((TrackModel) p.getPlayedTrack(), em);
                        final DateTime playedAt =
                                DateTime.createWithoutValidation(p.getPlayedAt());
                        return new PlayedTrack(playedTrack, playedAt);
                    })
                    .collect(Collectors.toSet())
                    .forEach(p -> playedTracks.add(p));
        }
        
        final Set<Playlist> playlists = new HashSet<>();
        
        if(!model.getPlaylists().isEmpty()) {
            model.getPlaylists()
                    .stream()
                    .map(p -> {
                        final UniqueEntityId ownerId =
                                UniqueEntityId.createFromUUID(model.getId()).getValue();
                        final UniqueEntityId playlistId =
                                UniqueEntityId.createFromUUID(p.getId()).getValue();
                        return playlistRepository.fetchById(ownerId, playlistId);
                    })
                    .collect(Collectors.toSet())
                    .forEach(p -> playlists.add(p));
        }

        final Player entity =
                Player.reconstitute(
                        model.getId(),
                        model.getName(),
                        playedTracks,
                        playlists,
                        model.getRole().toString(),
                        model.getCreatedAt(),
                        model.getLastModifiedAt()
                ).getValue();
        
        return entity;
    }
    
    public UserModel toPersistence(Player entity, EntityManager em) {
        
        final UserModel model = em.getReference(UserModel.class, entity.getId().toValue());
        
        entity.getPlayedTracks()
                .forEach(played ->
                        model.addPlayedTrack(
                            (TrackModel) libraryRepository.toPersistence(played.track, em),
                            played.playedAt.getValue()
                        )
                );
        
        entity.getPlaylists()
                .forEach(playlist ->
                    model.addPlaylist(
                            playlistRepository.toPersistence(playlist)
                    )
                );
        
        return model;
    }
    
    public static PlayerRepository getInstance() {
        return PlayerRepositoryHolder.INSTANCE;
    }
    
    private static class PlayerRepositoryHolder {

        final static EntityManagerFactoryHelper emfh =
                EntityManagerFactoryHelper.getInstance();
        
        private static final PlayerRepository INSTANCE =
                new PlayerRepository(
                        emfh,
                        LibraryEntityRepository.getInstance(),
                        PlaylistRepository.getInstance()
                );
    }
}
