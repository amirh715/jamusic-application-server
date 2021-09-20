/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.usecases.GetReportsByFilters;

import java.util.*;
import java.util.stream.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.reports.domain.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.modules.reports.infra.DTOs.GetReportsByFiltersRequestDTO;
import com.jamapplicationserver.modules.reports.repository.*;

/**
 *
 * @author dada
 */
public class GetReportsByFiltersUseCase implements IUsecase<GetReportsByFiltersRequestDTO, Set<Report>> {
    
    private final IReportRepository repository;
    
    private GetReportsByFiltersUseCase(IReportRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Result execute(GetReportsByFiltersRequestDTO request) throws GenericAppException {
        
        try {
            
            System.out.println("GetReportsByFiltersUseCase");
            
            final ArrayList<Result> combinedProps = new ArrayList<>();

            Set<ReportStatus> statuses = null;
            if(request.statuses != null && !request.statuses.isEmpty()) {
                
                final long erroneousStatusCount = request.statuses
                        .stream()
                        .map(status -> ReportStatus.create(status))
                        .filter(statusOrError -> statusOrError.isFailure)
                        .count();
                if(erroneousStatusCount > 0)
                    return Result.fail(new ValidationError("Report status is invalid"));
                statuses =
                        request.statuses
                        .stream()
                        .map(status -> ReportStatus.create(status).getValue())
                        .collect(Collectors.toSet());

            }
            
            final Result<UniqueEntityId> reporterIdOrError =
                    UniqueEntityId.createFromString(request.reporterId);
            final Result<UniqueEntityId> processorIdOrError =
                    UniqueEntityId.createFromString(request.processorId);
            final Result<UniqueEntityId> reportedEntityIdOrError =
                    UniqueEntityId.createFromString(request.reportedEntityId);
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
            final Boolean isContentReport =
                    Boolean.valueOf(request.isContentReport);
            
            if(request.reporterId != null)
                combinedProps.add(reporterIdOrError);
            if(request.processorId != null)
                combinedProps.add(processorIdOrError);
            if(request.reportedEntityId != null)
                combinedProps.add(reportedEntityIdOrError);
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
                            statuses,
                            reporterId,
                            processorId,
                            reportedEntityId,
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
                            isContentReport
                    );
            
            final Set<Report> reports = this.repository.fetchByFilters(filters);
            
            return Result.ok(reports);
        } catch(Exception e) {
            e.printStackTrace();
            throw new GenericAppException(e);
        }
        
    }
    
    public static GetReportsByFiltersUseCase getInstance() {
        return GetReportByFiltersUseCaseHolder.INSTANCE;
    }
    
    private static class GetReportByFiltersUseCaseHolder {

        private static final GetReportsByFiltersUseCase INSTANCE =
                new GetReportsByFiltersUseCase(ReportRepository.getInstance());
    }
}
