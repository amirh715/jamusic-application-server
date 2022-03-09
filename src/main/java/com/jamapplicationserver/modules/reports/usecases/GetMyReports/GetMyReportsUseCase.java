/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.GetMyReports;

import java.util.Set;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.reports.infra.services.*;
import com.jamapplicationserver.modules.reports.domain.errors.*;
import com.jamapplicationserver.modules.reports.infra.DTOs.queries.ReportDetails;
import com.jamapplicationserver.modules.reports.infra.DTOs.commands.GetMyReportsRequestDTO;
import com.jamapplicationserver.modules.reports.repository.ReportFilters;

/**
 *
 * @author dada
 */
public class GetMyReportsUseCase implements IUsecase<GetMyReportsRequestDTO, Set<ReportDetails>> {
    
    private final IReportQueryService queryService;
    
    private GetMyReportsUseCase(IReportQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result<Set<ReportDetails>> execute(GetMyReportsRequestDTO request) throws GenericAppException {
        try {
            
            return Result.ok(queryService.getMyReports(request.subjectId, request.limit, request.offset));
            
        } catch(Exception e) {
            throw new GenericAppException(e);
        }
    }
    
    public static GetMyReportsUseCase getInstance() {
        return GetMyReportsUsecaseHolder.INSTANCE;
    }
    
    private static class GetMyReportsUsecaseHolder {

        private static final GetMyReportsUseCase INSTANCE =
                new GetMyReportsUseCase(ReportQueryService.getInstance());
    }
}
