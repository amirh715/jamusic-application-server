/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.CreateReport;

import java.util.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.modules.reports.infra.DTOs.CreateReportRequestDTO;

/**
 *
 * @author dada
 */
public class CreateReportController extends BaseController {
    
    private final IUsecase usecase;
    
    private CreateReportController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
        
            final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
            
            System.out.println(fields.get("message") + " - " + fields.get("reporterId"));
            
            final CreateReportRequestDTO dto =
                    new CreateReportRequestDTO(
                            fields.get("message"),
                            fields.get("reportedEntityId"),
                            subjectId,
                            subjectRole
                    );
            
            final Result result = this.usecase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    this.notFound(error);
                if(error instanceof ConflictError)
                    this.conflict(error);
                if(error instanceof ClientErrorError)
                    this.clientError(error);
                
                return;
            }
            
            this.ok(result.getValue());
            
        } catch(Exception e) {
            this.fail(e);
        }
        
    }
    
    public static CreateReportController getInstance() {
        return CreateReportControllerHolder.INSTANCE;
    }
    
    private static class CreateReportControllerHolder {

        private static final CreateReportController INSTANCE =
                new CreateReportController(CreateReportUseCase.getInstance());
    }
}
