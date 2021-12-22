/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.usecases.GetAllShowcases;

import java.util.*;
import com.jamapplicationserver.modules.showcase.infra.DTOs.queries.ShowcaseDetails;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.showcase.infra.services.*;

/**
 *
 * @author dada
 */
public class GetAllShowcasesUseCase implements IUsecase {
    
    private IShowcaseQueryService queryService;
    
    private GetAllShowcasesUseCase(IShowcaseQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result execute(Object request) throws GenericAppException {
        
        try {
            return Result.ok(queryService.getAllShowcases());
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetAllShowcasesUseCase getInstance() {
        return GetAllShowcasesUseCaseHolder.INSTANCE;
    }
    
    private static class GetAllShowcasesUseCaseHolder {

        private static final GetAllShowcasesUseCase INSTANCE =
                new GetAllShowcasesUseCase(ShowcaseQueryService.getInstance());
    }
}
