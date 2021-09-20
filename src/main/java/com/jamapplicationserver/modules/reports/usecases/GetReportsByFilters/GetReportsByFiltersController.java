/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.GetReportsByFilters;

import spark.QueryParamsMap;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.reports.infra.DTOs.GetReportsByFiltersRequestDTO;

/**
 *
 * @author dada
 */
public class GetReportsByFiltersController extends BaseController {
    
    private final IUsecase usecase;
    
    private GetReportsByFiltersController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
            
            System.out.println("GetReportsByFiltersController");
            
            final QueryParamsMap fields = this.req.queryMap();
            
            final GetReportsByFiltersRequestDTO dto =
                    new GetReportsByFiltersRequestDTO(
                            fields.get("statuses").value(),
                            fields.get("reporterId").value(),
                            fields.get("processorId").value(),
                            fields.get("reportedEntityId").value(),
                            fields.get("assignedAtFrom").value(),
                            fields.get("assignedAtTill").value(),
                            fields.get("processedAtFrom").value(),
                            fields.get("processedAtTill").value(),
                            fields.get("archivedAtFrom").value(),
                            fields.get("archivedAtTill").value(),
                            fields.get("createdAtFrom").value(),
                            fields.get("createdAtTill").value(),
                            fields.get("lastModifiedAtFrom").value(),
                            fields.get("lastModifiedAtTill").value(),
                            fields.get("isContentReport").value()
                    );
            
            System.out.println(fields.get("reporterId").value());
            System.out.println(fields.get("processorId").value());
            System.out.println(fields.get("assignedAtFrom").value());
            System.out.println(fields.get("processedAtFrom").value());
            System.out.println(fields.get("archivedAtFrom").value());
            System.out.println(fields.get("lastModifiedAtFrom").value());
            
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
    
    public static GetReportsByFiltersController getInstance() {
        return GetReportsByFiltersControllerHolder.INSTANCE;
    }
    
    private static class GetReportsByFiltersControllerHolder {

        private static final GetReportsByFiltersController INSTANCE =
                new GetReportsByFiltersController(GetReportsByFiltersUseCase.getInstance());
    }
}
