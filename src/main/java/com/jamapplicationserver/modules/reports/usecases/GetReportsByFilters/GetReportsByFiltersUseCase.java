/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.GetReportsByFilters;

import java.util.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.reports.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.reports.infra.DTOs.commands.GetReportsByFiltersRequestDTO;
import com.jamapplicationserver.modules.reports.repository.*;
import com.jamapplicationserver.modules.reports.infra.services.*;
import com.jamapplicationserver.modules.reports.infra.DTOs.queries.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author dada
 */
public class GetReportsByFiltersUseCase implements IUsecase<GetReportsByFiltersRequestDTO, Set<ReportDetails>> {
    
    private final IReportQueryService queryService;
    
    private GetReportsByFiltersUseCase(IReportQueryService queryService) {
        this.queryService = queryService;
    }
    
    @Override
    public Result execute(GetReportsByFiltersRequestDTO request) throws GenericAppException {
        
        try {
                        
            final ArrayList<Result> combinedProps = new ArrayList<>();

            final Result<ReportStatus> statusOrError =
                    ReportStatus.create(request.status);
            final Result<UniqueEntityId> reporterIdOrError =
                    UniqueEntityId.createFromString(request.reporterId);
            final Result<UniqueEntityId> processorIdOrError =
                    UniqueEntityId.createFromString(request.processorId);
            final Result<UniqueEntityId> reportedEntityIdOrError =
                    UniqueEntityId.createFromString(request.reportedEntityId);
            final Result<ReportType> typeOrError =
                    ReportType.create(request.type);
            final Result<DateTime> assignedAtFromOrError =
                    DateTime.create(request.assignedAtFrom);
            final Result<DateTime> assignedAtTillOrError =
                    DateTime.create(request.assignedAtTill);
            final Result<DateTime> processedAtFromOrError =
                    DateTime.create(request.processedAtFrom);
            final Result<DateTime> processedAtTillOrError =
                    DateTime.create(request.processedAtTill);
            final Result<DateTime> archivedAtFromOrError =
                    DateTime.create(request.archivedAtFrom);
            final Result<DateTime> archivedAtTillOrError =
                    DateTime.create(request.archivedAtTill);
            final Result<DateTime> createdAtFromOrError =
                    DateTime.create(request.createdAtFrom);
            final Result<DateTime> createdAtTillOrError =
                    DateTime.create(request.createdAtTill);
            final Result<DateTime> lastModifiedAtFromOrError =
                    DateTime.create(request.lastModifiedAtFrom);
            final Result<DateTime> lastModifiedAtTillOrError =
                    DateTime.create(request.lastModifiedAtTill);
            
            if(request.status != null)
                combinedProps.add(statusOrError);
            if(request.reporterId != null)
                combinedProps.add(reporterIdOrError);
            if(request.processorId != null)
                combinedProps.add(processorIdOrError);
            if(request.reportedEntityId != null)
                combinedProps.add(reportedEntityIdOrError);
            if(request.type != null)
                combinedProps.add(typeOrError);
            if(request.assignedAtFrom != null)
                combinedProps.add(assignedAtFromOrError);
            if(request.assignedAtTill != null)
                combinedProps.add(assignedAtTillOrError);
            if(request.processedAtFrom != null)
                combinedProps.add(processedAtFromOrError);
            if(request.processedAtTill != null)
                combinedProps.add(processedAtTillOrError);
            if(request.archivedAtFrom != null)
                combinedProps.add(archivedAtFromOrError);
            if(request.archivedAtTill != null)
                combinedProps.add(archivedAtTillOrError);
            if(request.createdAtFrom != null)
                combinedProps.add(createdAtFromOrError);
            if(request.createdAtTill != null)
                combinedProps.add(createdAtTillOrError);
            if(request.lastModifiedAtFrom != null)
                combinedProps.add(lastModifiedAtFromOrError);
            if(request.lastModifiedAtTill != null)
                combinedProps.add(lastModifiedAtTillOrError);
            
            final Result combinedPropsResult = Result.combine(combinedProps);
            if(combinedPropsResult.isFailure)
                return combinedPropsResult;
            
            final ReportStatus status =
                    request.status != null ?
                    statusOrError.getValue() : null;
            final UniqueEntityId reporterId =
                    request.reporterId != null ?
                    reporterIdOrError.getValue()
                    : null;
            final UniqueEntityId processorId =
                    request.processorId != null ?
                    processorIdOrError.getValue()
                    : null;
            final UniqueEntityId reportedEntityId =
                    request.reportedEntityId != null ?
                    reportedEntityIdOrError.getValue()
                    : null;
            final ReportType type =
                    request.type != null ?
                    typeOrError.getValue() : null;
            final DateTime assignedAtFrom =
                    request.assignedAtFrom != null ?
                    assignedAtFromOrError.getValue()
                    : null;
            final DateTime assignedAtTill =
                    request.assignedAtTill != null ?
                    assignedAtTillOrError.getValue()
                    : null;
            final DateTime processedAtFrom =
                    request.processedAtFrom != null ?
                    processedAtFromOrError.getValue()
                    : null;
            final DateTime processedAtTill =
                    request.processedAtTill != null ?
                    processedAtTillOrError.getValue()
                    : null;
            final DateTime archivedAtFrom =
                    request.archivedAtFrom != null ?
                    archivedAtFromOrError.getValue()
                    : null;
            final DateTime archivedAtTill =
                    request.archivedAtTill != null ?
                    archivedAtTillOrError.getValue()
                    : null;
            final DateTime createdAtFrom =
                    request.createdAtFrom != null ?
                    createdAtFromOrError.getValue()
                    : null;
            final DateTime createdAtTill =
                    request.createdAtTill != null ?
                    createdAtTillOrError.getValue()
                    : null;
            final DateTime lastModifiedAtFrom =
                    request.lastModifiedAtFrom != null ?
                    lastModifiedAtFromOrError.getValue()
                    : null;
            final DateTime lastModifiedAtTill =
                    request.lastModifiedAtTill != null ?
                    lastModifiedAtTillOrError.getValue()
                    : null;
            
            final ReportFilters filters =
                    new ReportFilters(
                            status,
                            reporterId,
                            processorId,
                            reportedEntityId,
                            type,
                            assignedAtFrom,
                            assignedAtTill,
                            processedAtFrom,
                            processedAtTill,
                            archivedAtFrom,
                            archivedAtTill,
                            createdAtFrom,
                            createdAtTill,
                            lastModifiedAtFrom,
                            lastModifiedAtTill,
                            request.isContentReport,
                            request.isTechnicalReport,
                            request.isLibraryEntityReport,
                            request.limit,
                            request.offset
                    );
            
//            final Set<ReportDetails> reports = queryService.getReportsByFilters(filters);
            final Set<ReportDetails> reports =
                    queryService.getAllReports(request.subjectId, request.subjectRole, request.limit, request.offset);
            
            return Result.ok(reports);
        } catch(Exception e) {
            LogService.getInstance().error(e);
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetReportsByFiltersUseCase getInstance() {
        return GetReportByFiltersUseCaseHolder.INSTANCE;
    }
    
    private static class GetReportByFiltersUseCaseHolder {

        private static final GetReportsByFiltersUseCase INSTANCE =
                new GetReportsByFiltersUseCase(ReportQueryService.getInstance());
    }
}
