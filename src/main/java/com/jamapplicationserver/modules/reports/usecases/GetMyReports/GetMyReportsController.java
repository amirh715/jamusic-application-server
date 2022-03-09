/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.GetMyReports;

import spark.QueryParamsMap;
import java.util.*;
import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.reports.infra.DTOs.queries.ReportDetails;
import com.jamapplicationserver.modules.reports.infra.DTOs.commands.GetMyReportsRequestDTO;

/**
 *
 * @author dada
 */
public class GetMyReportsController extends BaseController {
    
    private final IUsecase<GetMyReportsRequestDTO, Set<ReportDetails>> usecase;
    
    private GetMyReportsController(IUsecase usecase) {
        this.usecase = usecase;
    }
    
    @Override
    public void executeImpl() {
        try {
            
            System.out.println("GetMyReportsController");
            
            final QueryParamsMap fields = req.queryMap();
            final GetMyReportsRequestDTO dto =
                    new GetMyReportsRequestDTO(
                            subjectId,
                            subjectRole,
                            fields.get("limit") != null ? fields.get("limit").integerValue() : null,
                            fields.get("offset") != null ? fields.get("offset").integerValue() : null
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
    
    public static GetMyReportsController getInstance() {
        return GetMyReportsControllerHolder.INSTANCE;
    }
    
    private static class GetMyReportsControllerHolder {

        private static final GetMyReportsController INSTANCE =
                new GetMyReportsController(GetMyReportsUseCase.getInstance());
    }
}
