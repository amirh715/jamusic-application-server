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
import com.jamapplicationserver.modules.user.domain.errors.UserProfileImageDoesNotExistError;

/**
 *
 * @author dada
 */
public class GetMyProfileImageUseCase implements IUsecase<UniqueEntityId, String> {
    
    private final IFilePersistenceManager persistence;
    
    private GetMyProfileImageUseCase(IFilePersistenceManager persistence) {
        this.persistence = persistence;
    }
    
    @Override
    public Result execute(UniqueEntityId subjectId) throws GenericAppException {
        
        try {
            
            final Path imagePath = persistence.buildPath(User.class);
            final InputStream image = persistence.read(imagePath.toFile());
            
            if(image == null)
                return Result.fail(new UserProfileImageDoesNotExistError());
            
            return Result.ok(image);
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetMyProfileImageUseCase getInstance() {
        return GetMyProfileImageUseCaseHolder.INSTANCE;
    }
    
    private static class GetMyProfileImageUseCaseHolder {

        private static final GetMyProfileImageUseCase INSTANCE =
                new GetMyProfileImageUseCase(FilePersistenceManager.getInstance());
    }
}
