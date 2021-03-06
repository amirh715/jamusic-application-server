/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.GetReportsByFilters;

import spark.QueryParamsMap;
import java.util.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.reports.infra.DTOs.queries.ReportDetails;
import com.jamapplicationserver.modules.reports.infra.DTOs.commands.GetReportsByFiltersRequestDTO;

/**
 *
 * @author dada
 */
public class GetReportsByFiltersController extends BaseController {
    
    private final IUsecase<GetReportsByFiltersRequestDTO, Set<ReportDetails>> usecase;
    
    private GetReportsByFiltersController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        
        try {
                        
            final QueryParamsMap fields = req.queryMap();
            
            final GetReportsByFiltersRequestDTO dto =
                    new GetReportsByFiltersRequestDTO(
                            fields.get("status").value(),
                            fields.get("reporterId").value(),
                            fields.get("processorId").value(),
                            fields.get("reportedEntityId").value(),
                            fields.get("type").value(),
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
                            fields.get("isContentReport").value(),
                            fields.get("isTechnicalReport").value(),
                            fields.get("isLibraryEntityReport").value(),
                            subjectId,
                            subjectRole,
                            fields.get("limit").integerValue(),
                            fields.get("offset").integerValue()
                    );
            
            final Result<Set<ReportDetails>> result = usecase.execute(dto);
            
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
            
            noStore();
            ok(result.getValue());
            
        } catch(Exception e) {
            fail(e);
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
