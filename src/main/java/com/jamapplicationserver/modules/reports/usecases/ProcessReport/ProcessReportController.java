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
import com.jamapplicationserver.modules.reports.infra.DTOs.commands.ProcessReportRequestDTO;

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
            
            final Map<String, String> fields = MultipartFormDataUtil.toMap(req.raw());
            
            final ProcessReportRequestDTO dto =
                    new ProcessReportRequestDTO(
                            fields.get("id"),
                            fields.get("processorNote"),
                            subjectId,
                            subjectRole
                    );
            
            final Result result = usecase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    notFound(error);
                if(error instanceof ConflictError)
                    conflict(error);
                if(error instanceof ClientErrorError)
                    clientError(error);
                
                return;
            }
            
            noContent();
            
        } catch(Exception e) {
            fail(e);
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
