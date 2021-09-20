/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories;

import java.util.*;
import java.util.stream.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.library.domain.Playlist.Playlist;
import com.jamapplicationserver.modules.library.domain.Track.Track;

/**
 *
 * @author dada
 */
public class PlaylistRepository implements IPlaylistRepository {
    
    private final EntityManager em;
    private final ILibraryEntityRepository libraryRepository;
    
    private PlaylistRepository(
            EntityManager em,
            ILibraryEntityRepository libraryRepository
    ) {
        this.em = em;
        this.libraryRepository = libraryRepository;
    }
    
    @Override
    public Playlist fetchById(UniqueEntityId id) {
        
        try {
            
            final PlaylistModel result = this.em.find(PlaylistModel.class, id.toValue());
            
            return toDomain(result);
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public Set<Playlist> fetchByPlayerId(UniqueEntityId id) {
        
        try {
            
            final CriteriaBuilder cb = this.em.getCriteriaBuilder();
            final CriteriaQuery<PlaylistModel> cq = cb.createQuery(PlaylistModel.class);
            final Root<PlaylistModel> root = cq.from(PlaylistModel.class);
            
            cq.where(cb.equal(root.get("playerId"), id.toValue()));
            
            cq.orderBy();
            
            return this.em.createQuery(cq)
                    .getResultList()
                    .stream()
                    .map(model -> toDomain(model))
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public void save(Playlist playlist) {
        
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            final boolean exists = this.exists(playlist.id);
            
            tnx.begin();
            
            if(exists) { // update existing playlist
                
                this.em.merge(toPersistence(playlist));
                
            } else { // insert new playlist
                
                this.em.persist(toPersistence(playlist));
                
            }
            
            tnx.commit();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void remove(Playlist playlist) {
        
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            tnx.begin();
            
            final PlaylistModel model =
                    em.find(PlaylistModel.class, playlist.id.toValue());
            
            em.remove(model);
            
            tnx.commit();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public boolean exists(UniqueEntityId id) {
        
        try {
            
            final PlaylistModel model = em.find(PlaylistModel.class, id.toValue());
            
            return model != null;
            
        } catch(Exception e) {
            throw e;
        }
        
    }
    
    @Override
    public Playlist toDomain(PlaylistModel model) {
        
        final Set<Track> tracks =
                model.getTracks()
                .stream()
                .map(track -> (Track) libraryRepository.toDomain(track))
                .collect(Collectors.toSet());
        
        Playlist instance =
                Playlist.reconstitute(
                        model.getId(),
                        model.getTitle(),
                        model.getCreatedAt(),
                        model.getLastModifiedAt(),
                        tracks
                ).getValue();
        
        return instance;
    }
    
    @Override
    public PlaylistModel toPersistence(Playlist entity) {
        
        final PlaylistModel model = new PlaylistModel();
        
        model.setId(entity.getId().toValue());
        model.setTitle(entity.getTitle().getValue());
        model.setCreatedAt(entity.getCreatedAt().getValue());
        model.setLastModifiedAt(entity.getLastModifiedAt().getValue());
        
        entity.getTracks()
                .forEach(track -> {
                    final TrackModel t =
                            (TrackModel) libraryRepository.toPersistence(track);
                    model.addTrack(t);
                });
        
        return model;
    }
    
    public static PlaylistRepository getInstance() {
        return PlaylistRepositoryHolder.INSTANCE;
    }
    
    private static class PlaylistRepositoryHolder {

        final static EntityManager em =
                EntityManagerFactoryHelper.getInstance()
                .getFactory()
                .createEntityManager();
        
        private static final PlaylistRepository INSTANCE =
                new PlaylistRepository(
                        em,
                        LibraryEntityRepository.getInstance()
                );
    }
}
