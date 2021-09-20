/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.repository;

import java.util.*;
import java.util.stream.*;
import java.time.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.reports.domain.*;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class ReportRepository implements IReportRepository {
    
    private final EntityManager em;
    
    private ReportRepository(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public Report fetchById(UniqueEntityId id) {
        
        try {
            
            final ReportModel model = em.find(ReportModel.class, id.toValue());
            
            if(model == null) return null;
            
            return ReportMapper.toDomain(model);
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public Set<Report> fetchByFilters(ReportFilters filters) {
        
        try {
            
            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<ReportModel> cq = cb.createQuery(ReportModel.class);
            final Root<ReportModel> root = cq.from(ReportModel.class);
            
            ArrayList<Predicate> predicates =
                    buildCriteriaPredicates(
                            cb,
                            cq,
                            root,
                            filters
                    );
            
            if(!predicates.isEmpty()) {
                cq.where(
                        cb.and(
                                predicates.toArray(
                                        new Predicate[predicates.size()]
                                )
                        )
                );
            }
            
            System.out.println("Predicates Size : " + predicates.size());
            
            final List<ReportModel> results =
                    em.createQuery(cq).getResultList();
            
            return (Set<Report>) results
                    .stream()
                    .map(model -> ReportMapper.toDomain(model))
                    .collect(Collectors.toSet());
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public Reporter fetchReporterById(UniqueEntityId id) {
                
        try {
            
            final UserModel model = em.find(UserModel.class, id.toValue());
            
            if(model == null) return null;
            
            return ReportMapper.toReporter(model);
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public Processor fetchProcessorById(UniqueEntityId id) {
                
        try {
            
            final String q = "SELECT processor FROM UserModel processor WHERE processor.role IN (?1) AND processor.id = ?2";
            final Query query = em.createQuery(q);
            List<UserRoleEnum> processorRoles = Arrays.asList(UserRoleEnum.ADMIN, UserRoleEnum.LIBRARY_MANAGER);
            query.setParameter(1, processorRoles);
            query.setParameter(2, id.toValue());
            final UserModel model = (UserModel) query.getSingleResult();
            
            return ReportMapper.toProcessor(model);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public Processor fetchAdminProcessor() {
                
        try {
            
            final String q = "SELECT processor FROM UserModel processor WHERE processor.role = ?1";
            final Query query = em.createQuery(q);
            query.setParameter(1, UserRoleEnum.ADMIN);
            final UserModel model = (UserModel) query.getSingleResult();
            
            return ReportMapper.toProcessor(model);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public ReportedEntity fetchEntityById(UniqueEntityId id) {
        
        try {
            
            final LibraryEntityModel model =
                    em.find(LibraryEntityModel.class, id.toValue());
            
            System.out.println("IS LIBRARY ENTITY NULL" + (model == null));
            
            return ReportMapper.toReportedEntity(model);
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public void save(Report entity) {
        
        final EntityTransaction tnx = em.getTransaction();
        
        ReportModel model;
        
        try {
            
            final boolean exists = this.exists(entity.id);
            
            tnx.begin();
            
            model = ReportMapper.toPersistence(entity);

            final UserModel reporter =
                    em.getReference(UserModel.class, entity.getReporter().getId().toValue());
            model.setReporter(reporter);
            
            if(entity.getReportedEntity() != null) {
                
                final LibraryEntityModel reportedEntity =
                    em.getReference(LibraryEntityModel.class, entity.getReportedEntity().getId().toValue());
                model.setReportedEntity(reportedEntity);
                
            }
            
            if(exists) { // update existing entity
                
                final UserModel processor =
                    em.getReference(UserModel.class, entity.getProcessor().getId().toValue());
                model.setProcessor(processor);
                
                em.merge(model);
                
            } else { // insert new entity
                
                em.persist(model);
                
            }
            
            tnx.commit();
            
        } catch(Exception e) {
            e.printStackTrace();
            tnx.rollback();
        }
        
    }
    
    @Override
    public boolean exists(UniqueEntityId id) {
        
        try {
            
            final ReportModel model = em.find(ReportModel.class, id.toValue());
            
            return model != null;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    private static ArrayList<Predicate> buildCriteriaPredicates(
            CriteriaBuilder cb,
            CriteriaQuery<ReportModel> cq,
            Root<ReportModel> root,
            ReportFilters filters
    ) {
        
        cq.select(root);
        
        cq.orderBy();
        
        ArrayList<Predicate> predicates = new ArrayList<>();
        
        if(filters != null) {
            
            // statuses
            if(filters.statuses != null && !filters.statuses.isEmpty()) {
                
                final Set<ReportStatusEnum> statuses = filters.statuses
                        .stream()
                        .map(status -> ReportStatusEnum.valueOf(status.getValue()))
                        .collect(Collectors.toSet());
                
                predicates.add(
                        root.get("status").in(statuses)
                );
                
            }
            
            // reporterId
            if(filters.reporterId != null) {
                final Root<UserModel> reporterRoot = cq.from(UserModel.class);
                predicates.add(cb.equal(reporterRoot.get("id"), filters.reporterId.toValue()));
            }
            
            // processorId
            if(filters.processorId != null) {
                final Root<UserModel> processorRoot = cq.from(UserModel.class);
                predicates.add(cb.equal(processorRoot.get("id"), filters.processorId.toValue()));
            }
            
            // reportedEntityId
            if(filters.reportedEntityId != null) {
                final Root<LibraryEntityModel> reportedEntityRoot = cq.from(LibraryEntityModel.class);
                predicates.add(cb.equal(reportedEntityRoot.get("id"), filters.reportedEntityId.toValue()));
            }
            
            // assignedAt from till
            if(filters.assignedAtFrom != null || filters.assignedAtTill != null) {
                final LocalDateTime from =
                        filters.assignedAtFrom != null ?
                        filters.assignedAtFrom.getValue() :
                        LocalDateTime.of(2020, 1, 1, 0, 0);
                final LocalDateTime till =
                        filters.assignedAtTill != null ?
                        filters.assignedAtTill.getValue() :
                        LocalDateTime.now();
                predicates.add(cb.between(root.get("assignedAt"), from, till));
            }
            
            // processedAt from till
            if(filters.processedAtFrom != null || filters.processedAtTill != null) {
                final LocalDateTime from =
                        filters.processedAtFrom != null ?
                        filters.processedAtFrom.getValue() :
                        LocalDateTime.of(2020, 1, 1, 0, 0);
                final LocalDateTime till =
                        filters.processedAtTill != null ?
                        filters.processedAtTill.getValue() :
                        LocalDateTime.now();
                predicates.add(cb.between(root.get("processedAt"), from, till));
            }
            
            // archivedAt from till
            if(filters.archivedAtFrom != null || filters.archivedAtTill != null) {
                final LocalDateTime from =
                        filters.archivedAtFrom != null ?
                        filters.archivedAtFrom.getValue() :
                        LocalDateTime.of(2020, 1, 1, 0, 0);
                final LocalDateTime till =
                        filters.archivedAtTill != null ?
                        filters.archivedAtTill.getValue() : LocalDateTime.now();
                predicates.add(cb.between(root.get("archivedAt"), from, till));
            }
            
            // createdAt from till
            if(filters.createdAtFrom != null || filters.createdAtTill != null) {
                final LocalDateTime from =
                        filters.createdAtFrom != null ?
                        filters.createdAtFrom.getValue() :
                        LocalDateTime.of(2020, 1, 1, 0, 0);
                final LocalDateTime till =
                        filters.createdAtTill != null ?
                        filters.createdAtTill.getValue() :
                        LocalDateTime.now();
                predicates.add(cb.between(root.get("createdAt"), from, till));
            }
            
            // lastModifiedAt from till
            if(filters.lastModifiedAtFrom != null || filters.lastModifiedAtTill != null) {
                final LocalDateTime from =
                        filters.lastModifiedAtFrom != null ?
                        filters.lastModifiedAtFrom.getValue() :
                        LocalDateTime.of(2020, 1, 1, 0, 0);
                final LocalDateTime till =
                        filters.lastModifiedAtTill != null ?
                        filters.lastModifiedAtTill.getValue() :
                        LocalDateTime.now();
                predicates.add(cb.between(root.get("lastModifiedAt"), from, till));
            }
            
            // isContentReport
            if(filters.isContentReport != null) {
                if(filters.isContentReport)
                    predicates.add(cb.isNotNull(root.get("reportedEntity")));
                else
                    predicates.add(cb.isNull(root.get("reportedEntity")));
            }

        }
        
        return predicates;
    }
    
    public static ReportRepository getInstance() {
        return ReportRepositoryHolder.INSTANCE;
    }
    
    private static class ReportRepositoryHolder {

        final static EntityManager em =
                EntityManagerFactoryHelper.getInstance()
                .getFactory()
                .createEntityManager();
        
        private static final ReportRepository INSTANCE =
                new ReportRepository(em);
    }
}
