/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.ProcessReport;

import java.util.*;
import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.modules.reports.infra.DTOs.ProcessReportRequestDTO;

/**
 *
 * @author dada
 */
public class ProcessReportController extends BaseController {
    
    private final IUsecase usecase;
    
    private ProcessReportController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
            
            final ProcessReportRequestDTO dto =
                    new ProcessReportRequestDTO(
                            fields.get("id"),
                            fields.get("processorId"),
                            fields.get("processorNote")
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
    
    public static ProcessReportController getInstance() {
        return ProcessReportControllerHolder.INSTANCE;
    }
    
    private static class ProcessReportControllerHolder {

        private static final ProcessReportController INSTANCE =
                new ProcessReportController(ProcessReportUseCase.getInstance());
    }
}
