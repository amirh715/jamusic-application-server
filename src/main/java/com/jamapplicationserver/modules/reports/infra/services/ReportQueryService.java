/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.infra.services;

import java.util.*;
import java.util.stream.*;
import java.time.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.modules.reports.repository.ReportFilters;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.reports.infra.DTOs.queries.*;
import com.jamapplicationserver.modules.reports.domain.*;

/**
 *
 * @author dada
 */
public class ReportQueryService implements IReportQueryService {
    
    private EntityManagerFactoryHelper emfh;
    
    private ReportQueryService(EntityManagerFactoryHelper emfh) {
        this.emfh = emfh;
    }
    
    @Override
    public Set<ReportDetails> getReportsByFilters(ReportFilters filters) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final CriteriaBuilder builder = em.getCriteriaBuilder();
            final CriteriaQuery<ReportModel> query = builder.createQuery(ReportModel.class);
            final Root<ReportModel> root = query.from(ReportModel.class);
            
            final Set<Predicate> predicates = new HashSet<>();
            
            if(filters != null) {
                
               if(filters.status != null) {
                   ReportStatusEnum status;
                   if(filters.status.isArchived())
                       status = ReportStatusEnum.ARCHIVED;
                   else if(filters.status.isAssigned())
                       status = ReportStatusEnum.ASSIGNED;
                   else if(filters.status.isProcessed())
                       status = ReportStatusEnum.PROCESSED;
                   else
                       status = ReportStatusEnum.PENDING_ASSIGNMENT;
                   final Predicate predicate = builder.equal(root.get("status"), status);
                   predicates.add(predicate);
               }
               
               if(filters.processorId != null) {
                   final Predicate predicate =
                           builder.equal(root.get("processor").get("id"), filters.processorId.toValue());
                   predicates.add(predicate);
               }
               
               if(filters.reportedEntityId != null) {
                   final Predicate predicate =
                           builder.equal(root.get("reportedEntity").get("id"), filters.reportedEntityId.toValue());
                   predicates.add(predicate);
               }
               
               if(filters.assignedAtFrom != null || filters.assignedAtTill != null) {
                   final LocalDateTime from =
                           filters.assignedAtFrom != null ?
                           filters.assignedAtFrom.getValue() :
                           LocalDateTime.of(20201, Month.JANUARY, 0, 0, 0);
                   final LocalDateTime till =
                           filters.assignedAtTill != null ?
                           filters.assignedAtTill.getValue() :
                           LocalDateTime.now();
                    final Predicate predicate =
                            builder.between(root.get("assignedAt"), from, till);
                    predicates.add(predicate);
               }
               
               if(filters.processedAtFrom != null || filters.processedAtTill != null) {
                    final LocalDateTime from =
                           filters.processedAtFrom != null ?
                           filters.processedAtFrom.getValue() :
                           LocalDateTime.of(20201, Month.JANUARY, 0, 0, 0);
                    final LocalDateTime till =
                           filters.processedAtTill != null ?
                           filters.processedAtTill.getValue() :
                           LocalDateTime.now();
                    final Predicate predicate =
                            builder.between(root.get("processedAt"), from, till);
                    predicates.add(predicate);
               }
               
               if(filters.archivedAtFrom != null || filters.archivedAtTill != null) {
                    final LocalDateTime from =
                           filters.archivedAtFrom != null ?
                           filters.archivedAtFrom.getValue() :
                           LocalDateTime.of(20201, Month.JANUARY, 0, 0, 0);
                    final LocalDateTime till =
                           filters.archivedAtTill != null ?
                           filters.archivedAtTill.getValue() :
                           LocalDateTime.now();
                    final Predicate predicate =
                            builder.between(root.get("archivedAt"), from, till);
                    predicates.add(predicate);
               }
               
               if(filters.createdAtFrom != null || filters.createdAtTill != null) {
                    final LocalDateTime from =
                           filters.createdAtFrom != null ?
                           filters.createdAtFrom.getValue() :
                           LocalDateTime.of(20201, Month.JANUARY, 0, 0, 0);
                    final LocalDateTime till =
                           filters.createdAtTill != null ?
                           filters.createdAtTill.getValue() :
                           LocalDateTime.now();
                    final Predicate predicate =
                            builder.between(root.get("createdAt"), from, till);
                    predicates.add(predicate);
               }
               
               if(filters.lastModifiedAtFrom != null || filters.lastModifiedAtTill != null) {
                    final LocalDateTime from =
                           filters.lastModifiedAtFrom != null ?
                           filters.lastModifiedAtFrom.getValue() :
                           LocalDateTime.of(20201, Month.JANUARY, 0, 0, 0);
                    final LocalDateTime till =
                           filters.lastModifiedAtTill != null ?
                           filters.lastModifiedAtTill.getValue() :
                           LocalDateTime.now();
                    final Predicate predicate =
                            builder.between(root.get("lastModifiedAt"), from, till);
                    predicates.add(predicate);
               }
               
               if(filters.isContentReport != null) {
                   final Predicate predicate =
                           filters.isContentReport ?
                           builder.equal(root.get("type"), ReportTypeEnum.CONTENT) :
                           builder.notEqual(root.get("type"), ReportTypeEnum.CONTENT);
                   predicates.add(predicate);
               }
               
               if(filters.isTechnicalReport != null) {
                   final Predicate predicate =
                           filters.isTechnicalReport ?
                           builder.equal(root.get("type"), ReportTypeEnum.TECHNICAL) :
                           builder.notEqual(root.get("type"), ReportTypeEnum.TECHNICAL);
                   predicates.add(predicate);
               }
               
               if(filters.isLibraryEntityReport) {
                   final Predicate predicate =
                           filters.isLibraryEntityReport ?
                           builder.isNotNull(root.get("reportedEntity")) :
                           builder.isNull(root.get("reportedEntity"));
                   predicates.add(predicate);
               }

            }
            
            query.where(
                    builder.and(
                            predicates.toArray(new Predicate[predicates.size()])
                    )
            );
            
            return em.createQuery(query)
                    .getResultStream()
                    .map(report -> {
                        return new ReportDetails(
                                report.getId(),
                                report.getMessage(),
                                report.getStatus(),
                                report.getReporter().getId(),
                                report.getReporter().getName(),
                                report.getReportedEntity() != null ?
                                        report.getReportedEntity().getId() : null,
                                report.getReportedEntity() != null ?
                                        report.getReportedEntity().getTitle() : null,
                                report.isAssigned() ?
                                        report.getProcessor().getId() : null,
                                report.isAssigned() ?
                                        report.getProcessor().getName() : null,
                                report.getProcessorNote(),
                                report.getAssignedAt(),
                                report.getProcessedAt(),
                                report.getArchivedAt(),
                                report.getCreatedAt(),
                                report.getLastModifiedAt()
                        );
                    })
                    .collect(Collectors.toSet()); 
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public ReportDetails getReportById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final ReportModel report = em.find(ReportModel.class, id.toValue());
            
            return new ReportDetails(
                    report.getId(),
                    report.getMessage(),
                    report.getStatus(),
                    report.getReporter().getId(),
                    report.getReporter().getName(),
                    report.isLibraryEntityReport() ?
                            report.getReportedEntity().getId() : null,
                    report.isLibraryEntityReport() ?
                            report.getReportedEntity().getTitle() : null,
                    report.isAssigned() ?
                            report.getProcessor().getId() : null,
                    report.isAssigned() ?
                            report.getProcessor().getName() : null,
                    report.getProcessorNote(),
                    report.getAssignedAt(),
                    report.getProcessedAt(),
                    report.getArchivedAt(),
                    report.getCreatedAt(),
                    report.getLastModifiedAt()
            );
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public ReporterDetails getReporterById(UniqueEntityId reporterId) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final String query = "SELECT reporters FROM UserModel reporters WHERE "
                    + "reporters.reports IS NOT EMPTY";
            final UserModel reporter =
                    em.createQuery(query, UserModel.class)
                    .getSingleResult();
            
            final Set<ReportDetails> reports =
                    reporter
                    .getReports()
                    .stream()
                    .map(report -> {
                        return new ReportDetails(
                                report.getId(),
                                report.getMessage(),
                                report.getStatus(),
                                report.getReporter().getId(),
                                report.getReporter().getName(),
                                report.isLibraryEntityReport() ?
                                        report.getReportedEntity().getId() : null,
                                report.isLibraryEntityReport() ?
                                        report.getReportedEntity().getTitle() : null,
                                report.isAssigned() ?
                                        report.getProcessor().getId() : null,
                                report.isAssigned() ?
                                        report.getProcessor().getName() : null,
                                report.getProcessorNote(),
                                report.getAssignedAt(),
                                report.getProcessedAt(),
                                report.getArchivedAt(),
                                report.getCreatedAt(),
                                report.getLastModifiedAt()
                        );
                    })
                    .collect(Collectors.toSet());
            
            final long totalReportsCount =
                    reporter.getReports()
                    .stream()
                    .count();
            final long processedReportsCount =
                    reporter.getReports()
                    .stream()
                    .filter(report -> report.isProcessed())
                    .count();
            
            return new ReporterDetails(
                    reporter.getId(),
                    reporter.getName(),
                    reports,
                    totalReportsCount,
                    processedReportsCount
            );
            
        } catch(NoResultException e) {
            return null;
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public ReportsSummary getReportsSummary() {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final String query = "SELECT reports FROM ReportModel reports "
                    + "WHERE reports.createdAt BETWEEN NOW() AND NOW() - interval 30 'day'";
            
            final Stream<ReportModel> reports =
                    em.createNativeQuery(query)
                    .getResultStream();
            
            final ReportsSummary summary = new ReportsSummary();
            
            final long totalReportsCount =
                    reports.count();
            final long assignedReportsCount =
                    reports.filter(report -> report.isAssigned())
                    .count();
            final long processedReportsCount =
                    reports.filter(report -> report.isProcessed())
                    .count();
            final long technicalReportsCount =
                    reports.filter(report -> report.isTechnical())
                    .count();
            final long contentReportsCount =
                    reports.filter(report -> report.isContentRelated())
                    .count();
            final long libraryEntityReportsCount =
                    reports.filter(report -> report.isLibraryEntityReport())
                    .count();
                    
            final Set<ReportedLibraryEntityIdAndTitle> mostReportedLibraryEntities =
                    reports.filter(report -> report.isLibraryEntityReport())
                    .map(report -> {
                        final LibraryEntityModel reportedEntity = report.getReportedEntity();
                        return new ReportedLibraryEntityIdAndTitle(
                                reportedEntity.getId().toString(),
                                reportedEntity.getTitle()
                        );
                    })
                    .collect(Collectors.toSet());
//                    .collect(Collectors.groupingBy(ReportedLibraryEntityIdAndTitle::getId));
            
            summary.totalReportsCount = totalReportsCount;
            summary.assignedReportsCount = assignedReportsCount;
            summary.processedReportsCount = processedReportsCount;
            summary.technicalReportsCount = technicalReportsCount;
            summary.contentReportsCount = contentReportsCount;
            summary.libraryEntityReportsCount = libraryEntityReportsCount;
            summary.mostReportedLibraryEntities = mostReportedLibraryEntities;
            
            return summary;
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    public static ReportQueryService getInstance() {
        return ReportQueryServiceHolder.INSTANCE;
    }
    
    private static class ReportQueryServiceHolder {

        private static final ReportQueryService INSTANCE =
                new ReportQueryService(EntityManagerFactoryHelper.getInstance());
    }
}
