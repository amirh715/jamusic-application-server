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
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.library.domain.Player.*;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.modules.library.domain.Playlist.Playlist;
import com.jamapplicationserver.core.domain.*;
import java.time.LocalDateTime;

/**
 *
 * @author dada
 */
public class PlayerRepository implements IPlayerRepository {
    
    private final EntityManagerFactoryHelper emfh;
    private final ILibraryEntityRepository libraryRepository;
    
    private PlayerRepository(
            EntityManagerFactoryHelper emfh,
            ILibraryEntityRepository libraryRepository
    ) {
        this.emfh = emfh;
        this.libraryRepository = libraryRepository;
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
            
            return toDomain(result);
            
        } catch(NoResultException e) {
            return null;
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
            return null;
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public void save(Player player) {
        
        final EntityManager em = emfh.createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            tnx.begin();
            
            final UserModel model = toPersistence(player, em);
            
//            em.merge(model);
            
            tnx.commit();
            
        } catch(Exception e) {
            e.printStackTrace();
            tnx.rollback();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    private Playlist toDomain(PlaylistModel model) {
        
        return Playlist.reconstitute(
                model.getId(),
                model.getTitle(),
                model.getCreatedAt(),
                model.getLastModifiedAt(),
                model.getTracks()
                    .stream()
                    .map(track -> track.getId())
                    .collect(Collectors.toSet())
        ).getValue();
    }
    
    private PlaylistModel toPersistence(Playlist entity, EntityManager em) {
        
        final PlaylistModel model = new PlaylistModel();
        model.setId(entity.getId().toValue());
        model.setTitle(entity.getTitle().getValue());
        model.setCreatedAt(entity.getCreatedAt().getValue());
        model.setLastModifiedAt(entity.getLastModifiedAt().getValue());
        final Set<TrackModel> tracks =
                entity.getTracksIds()
                .stream()
                .map(trackId -> em.getReference(TrackModel.class, trackId.toValue()))
                .collect(Collectors.toSet());
        model.replaceTracks(tracks);
        
        return model;
    }
        
    private Player toDomain(UserModel model) {
        
        final Set<PlayedTrack> playedTracks = new HashSet<>();
        if(!model.getPlayedTracks().isEmpty()) {
            model.getPlayedTracks()
                    .stream()
                    .map(pt -> new PlayedTrack(
                            UniqueEntityId.createFromUUID(pt.getPlayedTrack().getId()).getValue(),
                            DateTime.createWithoutValidation(pt.getPlayedAt()))
                    )
                    .forEach(pt -> playedTracks.add(pt));
        }
        
        final Set<Playlist> playlists = new HashSet<>();
        if(!model.getPlaylists().isEmpty()) {
            model.getPlaylists()
                .stream()
                .map(playlist -> toDomain(playlist))
                .forEach(playlist -> playlists.add(playlist));
        }
        
        return Player.reconstitute(
                model.getId(),
                model.getName(),
                playedTracks,
                playlists,
                model.getRole().toString(),
                model.getCreatedAt(),
                model.getLastModifiedAt()
        ).getValue();
    }
    
    private UserModel toPersistence(Player entity, EntityManager em) {

        final UserModel model = em.getReference(UserModel.class, entity.getId().toValue());

        final Set<PlayedModel> newPlayedTracks = entity.getPlayedTracks()
                .stream()
                .map(playedTrack -> {
                    final UserModel player = model;
                    final TrackModel track = em.getReference(TrackModel.class, playedTrack.getPlayedTrackId().toValue());
                    final LocalDateTime playedAt = playedTrack.getPlayedAt().getValue();
                    final PlayedModel pt = new PlayedModel();
                    pt.setPlayer(player);
                    pt.setPlayedTrack(track);
                    pt.setPlayedAt(playedAt);
                    return pt;
                })
                .filter(playedTrack -> !(model.getPlayedTracks().contains(playedTrack)))
                .collect(Collectors.toSet());
        newPlayedTracks.forEach(pt -> {
            model.addPlayedTrack(pt);
        });

        final Set<PlaylistModel> playlists =
                entity.getPlaylists()
                .stream()
                .map(playlist -> toPersistence(playlist, em))
                .collect(Collectors.toSet());
        model.replacePlaylists(playlists);
        
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
                        LibraryEntityRepository.getInstance()
                );
    }
}
