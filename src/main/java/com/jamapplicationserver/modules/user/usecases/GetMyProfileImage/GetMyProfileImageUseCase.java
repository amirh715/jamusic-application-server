/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetMyProfileImage;

import java.io.InputStream;
import java.nio.file.Path;
import com.jamapplicationserver.modules.user.domain.User;
import com.jamapplicationserver.infra.Persistence.filesystem.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.user.repository.*;
import com.jamapplicationserver.modules.user.domain.errors.*;

/**
 *
 * @author dada
 */
public class GetMyProfileImageUseCase implements IUsecase<UniqueEntityId, InputStream> {
    
    private final IUserRepository repository;
    private final IFilePersistenceManager persistence;
    
    private GetMyProfileImageUseCase(IFilePersistenceManager persistence, IUserRepository repository) {
        this.persistence = persistence;
        this.repository = repository;
    }
    
    @Override
    public Result execute(UniqueEntityId subjectId) throws GenericAppException {
        
        try {
                        
            final User user = repository.fetchById(subjectId);
            
            if(user == null)
                return Result.fail(new UserDoesNotExistError());
                        
            if(!user.hasProfileImage())
                return Result.fail(new UserProfileImageDoesNotExistError());
            
            final InputStream image = persistence.read(user.getImagePath().toFile());
            
            if(image == null)
                return Result.fail(new UserProfileImageDoesNotExistError());
            
            return Result.ok(image);
            
        } catch(Exception e) {
            e.printStackTrace();
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetMyProfileImageUseCase getInstance() {
        return GetMyProfileImageUseCaseHolder.INSTANCE;
    }
    
    private static class GetMyProfileImageUseCaseHolder {

        private static final GetMyProfileImageUseCase INSTANCE =
                new GetMyProfileImageUseCase(
                        FilePersistenceManager.getInstance(),
                        UserRepository.getInstance()
                );
    }
}
