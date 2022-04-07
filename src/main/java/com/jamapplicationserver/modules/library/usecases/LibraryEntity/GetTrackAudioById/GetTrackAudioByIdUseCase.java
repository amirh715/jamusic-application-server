/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.LibraryEntity.GetTrackAudioById;

import java.io.InputStream;
import java.nio.file.Path;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.infra.Persistence.filesystem.*;
import com.jamapplicationserver.modules.library.repositories.*;
import com.jamapplicationserver.modules.library.domain.Track.Track;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import com.jamapplicationserver.modules.library.infra.DTOs.commands.GetTrackAudioByIdRequestDTO;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author dada
 */
public class GetTrackAudioByIdUseCase implements IUsecase<GetTrackAudioByIdRequestDTO, InputStream> {
    
    private final ILibraryEntityRepository repository;
    private final IFilePersistenceManager persistence;
    
    private GetTrackAudioByIdUseCase(
            ILibraryEntityRepository repository,
            IFilePersistenceManager persistence
    ) {
        this.repository = repository;
        this.persistence = persistence;
    }
    
    @Override
    public Result<InputStream> execute(GetTrackAudioByIdRequestDTO request) throws GenericAppException {
                
        try {
            
            final Result<UniqueEntityId> idOrError =
                    UniqueEntityId.createFromString(request.id);
            if(idOrError.isFailure) return Result.fail(idOrError.getError());
            
            final UniqueEntityId id = idOrError.getValue();
                        
            final Track track =
                    repository
                            .fetchTrackById(id)
                            .includeUnpublished()
                            .getSingleResult();
            if(track == null)
                return Result.fail(new TrackDoesNotExistError());
                        
            final Path audioPath = track.getAudioPath();
            final InputStream audio = persistence.read(audioPath.toFile());
            if(audio == null) throw new Exception("File not found"); // LOG
                        
            return Result.ok(audio);
            
        } catch(Exception e) {
            LogService.getInstance().error(e);
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetTrackAudioByIdUseCase getInstance() {
        return GetTrackAudioByIdUseCaseHolder.INSTANCE;
    }
    
    private static class GetTrackAudioByIdUseCaseHolder {

        private static final GetTrackAudioByIdUseCase INSTANCE =
                new GetTrackAudioByIdUseCase(
                        LibraryEntityRepository.getInstance(),
                        FilePersistenceManager.getInstance()
                );
    }
}
