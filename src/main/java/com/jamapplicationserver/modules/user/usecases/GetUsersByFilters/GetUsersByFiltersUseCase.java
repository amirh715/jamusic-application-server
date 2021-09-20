/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.GetUsersByFilters;

import com.jamapplicationserver.core.domain.UserRole;
import com.jamapplicationserver.core.domain.DateTime;
import java.util.*;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.modules.user.repository.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 */
public class GetUsersByFiltersUseCase implements IUsecase<GetUsersByFiltersRequestDTO, GetUsersByFiltersResponseDTO> {
    
    private final IUserRepository repository;
    
    private GetUsersByFiltersUseCase(IUserRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result<GetUsersByFiltersResponseDTO> execute(GetUsersByFiltersRequestDTO request) throws GenericAppException {
        
        try {
            
            final Result<DateTime> createdAtFromOrError = DateTime.create(request.createdAtFrom);
            final Result<DateTime> createdAtTillOrError = DateTime.create(request.createdAtTill);
            final Result<DateTime> lastModifiedAtFromOrError = DateTime.create(request.lastModifiedAtFrom);
            final Result<DateTime> lastModifiedAtTillOrError = DateTime.create(request.lastModifiedAtTill);
            final Result<UserState> stateOrError = UserState.create(request.state);
            final Result<UserRole> roleOrError = UserRole.create(request.role);
            final Result<UniqueEntityId> creatorIdOrError = UniqueEntityId.createFromString(request.creatorId);
            final Result<UniqueEntityId> updaterIdOrError = UniqueEntityId.createFromString(request.updaterId);
            
            final List<Result> combinedProps = new ArrayList<>();
            
            if(request.createdAtFrom != null)
                combinedProps.add(createdAtFromOrError);
            
            if(request.createdAtTill != null)
                combinedProps.add(createdAtTillOrError);
            
            if(request.lastModifiedAtFrom != null)
                combinedProps.add(lastModifiedAtFromOrError);
            
            if(request.lastModifiedAtTill != null)
                combinedProps.add(lastModifiedAtTillOrError);
            
            if(request.state != null)
                combinedProps.add(stateOrError);
            
            if(request.role != null) 
                combinedProps.add(roleOrError);
            
            if(request.creatorId != null)
                combinedProps.add(creatorIdOrError);
            
            if(request.updaterId != null)
                combinedProps.add(updaterIdOrError);
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            
            if(combinedPropsResult.isFailure)
                return combinedPropsResult;
            
            final UsersFilters filters =
                new UsersFilters(
                        request.searchTerm,
                        request.createdAtFrom != null ? createdAtFromOrError.getValue() : null,
                        request.createdAtTill != null ? createdAtTillOrError.getValue() : null,
                        request.lastModifiedAtFrom != null ? lastModifiedAtFromOrError.getValue() : null,
                        request.lastModifiedAtTill != null ? lastModifiedAtTillOrError.getValue() : null,
                        request.hasImage,
                        request.hasEmail,
                        request.state != null ? stateOrError.getValue() : null,
                        request.role != null ? roleOrError.getValue() : null,
                        null,
                        null
//                        request.creatorId != null ? creatorIdOrError.getValue() : null,
//                        request.updaterId != null ? updaterIdOrError.getValue() : null
                );
            
            final Set<User> users = this.repository.fetchByFilters(filters);

            final GetUsersByFiltersResponseDTO response =
                    new GetUsersByFiltersResponseDTO(users);
            
            return Result.ok(response);
            
        } catch(IllegalStateException e) {
            throw new GenericAppException();
        }
        
    }
    
    public static GetUsersByFiltersUseCase getInstance() {
        return GetUsersByFiltersUseCaseHolder.INSTANCE;
    }
    
    private static class GetUsersByFiltersUseCaseHolder {

        private static final GetUsersByFiltersUseCase INSTANCE =
                new GetUsersByFiltersUseCase(UserRepository.getInstance());
    }
}
