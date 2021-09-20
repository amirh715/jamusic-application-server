/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetUserProfileImage;

import com.jamapplicationserver.infra.Persistence.filesystem.FilePersistenceManager;
import java.io.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.modules.user.domain.User;
import com.jamapplicationserver.modules.user.domain.errors.*;
import com.jamapplicationserver.modules.user.repository.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.infra.Persistence.filesystem.IFilePersistenceManager;

/**
 *
 * @author amirhossein
 */
public class GetUserProfileImageUseCase implements IUsecase<String, Result> {
    
    private final IUserRepository repository;
    private final IFilePersistenceManager persistence;
    
    private GetUserProfileImageUseCase(IUserRepository repository, IFilePersistenceManager persistence) {
        this.repository = repository;
        this.persistence = persistence;
    }
    
    @Override
    public Result execute(String idString) throws GenericAppException {
        
        try {
            
            final Result<UniqueEntityId> idOrError = UniqueEntityId.createFromString(idString);
            
            if(idOrError.isFailure) return idOrError;
            
            final UniqueEntityId id = idOrError.getValue();
            
            final User user = this.repository.fetchById(id);
            
            if(user == null) return Result.fail(new UserDoesNotExistError());
            
            if(!user.hasProfileImage()) return Result.fail(new UserProfileImageDoesNotExistError());
            
            final File file = user.getImagePath().toFile();
            
            final InputStream image = this.persistence.read(file);
            
            
            return Result.ok(image);

        } catch(Exception e) {
            e.printStackTrace();
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetUserProfileImageUseCase getInstance() {
        return GetUserProfileImageUseCaseHolder.INSTANCE;
    }
    
    private static class GetUserProfileImageUseCaseHolder {

        private static final GetUserProfileImageUseCase INSTANCE =
                new GetUserProfileImageUseCase(
                        UserRepository.getInstance(),
                        FilePersistenceManager.getInstance()
                );
    }
}
