/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.RemoveLibraryEntity;

import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.modules.library.domain.Album.Album;
import com.jamapplicationserver.modules.library.domain.core.*;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.infra.DTOs.usecases.*;

/**
 *
 * @author dada
 */
public class RemoveLibraryEntityUseCase implements IUsecase<RemoveLibraryEntityRequestDTO, String> {
    
    private final ILibraryEntityRepository repository;
    
    private RemoveLibraryEntityUseCase(ILibraryEntityRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(RemoveLibraryEntityRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(request.id);
            final Result<UniqueEntityId> updaterIdOrError = UniqueEntityId.createFromString(request.updaterId);
            
            final Result[] combinedProps = {
                idOrError,
                updaterIdOrError
            };
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure) return combinedPropsResult;
            
            final UniqueEntityId id = idOrError.getValue();
            final UniqueEntityId updaterId = updaterIdOrError.getValue();
            
            final LibraryEntity entity =
                    this.repository.fetchById(id)
                            .includeUnpublished()
                            .getSingleResult();
            
            if(entity == null) return Result.fail(new LibraryEntityDoesNotExistError());
            
            Result result;

            if(!(entity instanceof Artist)) { // remove album/track
                
                UniqueEntityId artistId;
                
                if(entity instanceof Album)
                    artistId = ((Album) entity).getArtist().id;
                else if(entity instanceof Track)
                    artistId = ((Track) entity).getArtist().id;
                else
                    return Result.fail(new ValidationError(""));
                
                final Artist artist = this.repository
                        .fetchArtistById(artistId)
                        .includeUnpublished()
                        .getSingleResult();
                
                if(entity instanceof Album) {

                    result = artist.removeAlbum((Album) entity, updaterId);

                    if(result.isFailure) return result;

                } else if(entity instanceof Track) {

                    result = artist.removeTrack((Track) entity, updaterId);

                    if(result.isFailure) return result;

                }
                
                this.repository.save(artist);
                
            }
            
            this.repository.remove(entity);
            
            return Result.ok();
            
        } catch(Exception e) {
            e.printStackTrace();
            throw new GenericAppException(e);
        }
        
    }
    
    public static RemoveLibraryEntityUseCase getInstance() {
        return RemoveLibraryEntityUseCaseHolder.INSTANCE;
    }
    
    private static class RemoveLibraryEntityUseCaseHolder {

        private static final RemoveLibraryEntityUseCase INSTANCE =
                new RemoveLibraryEntityUseCase(LibraryEntityRepository.getInstance());
    }
}
