/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.usecases.GetAllShowcases;

import java.util.*;
import com.jamapplicationserver.modules.showcase.domain.Showcase;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.showcase.repository.*;

/**
 *
 * @author dada
 */
public class GetAllShowcasesUseCase implements IUsecase {
    
    private IShowcaseRepository repository;
    
    private GetAllShowcasesUseCase(IShowcaseRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(Object request) throws GenericAppException {
        
        try {
            
            final Set<Showcase> showcases =
                    repository.fetchAll();
            
            return Result.ok(showcases);
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetAllShowcasesUseCase getInstance() {
        return GetAllShowcasesUseCaseHolder.INSTANCE;
    }
    
    private static class GetAllShowcasesUseCaseHolder {

        private static final GetAllShowcasesUseCase INSTANCE =
                new GetAllShowcasesUseCase(ShowcaseRepository.getInstance());
    }
}
