/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.infra.services;

import java.util.*;
import java.util.stream.*;
import java.time.*;
import javax.persistence.*;
import javax.persistence.criteria.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.modules.user.infra.DTOs.queries.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.user.repository.UsersFilters;

/**
 *
 * @author dada
 */
public class UserQueryService implements IUserQueryService {
    
    private final EntityManagerFactoryHelper emfh;
    
    private UserQueryService(EntityManagerFactoryHelper emfh) {
        this.emfh = emfh;
    }
    
    @Override
    public Set<UserDetails> getUsersByFilters(UsersFilters filters) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final CriteriaBuilder builder = em.getCriteriaBuilder();
            final CriteriaQuery<UserModel> query = builder.createQuery(UserModel.class);
            final Root<UserModel> root = query.from(UserModel.class);
            
            
            
            final Set<Predicate> predicates = new HashSet<>();
            
            if(filters != null) {
                
                if(filters.searchTerm != null) {
                    final Predicate predicate =
                            builder.or(
                                    builder.like(root.get("name"), toSearchPattern(filters.searchTerm)),
                                    builder.like(root.get("mobile"), toSearchPattern(filters.searchTerm)),
                                    builder.like(root.get("email"), toSearchPattern(filters.searchTerm))
                            );
                    predicates.add(predicate);
                }
                
                if(filters.createdAtFrom != null || filters.createdAtTill != null) {
                    final LocalDateTime from =
                            filters.createdAtFrom != null ?
                            filters.createdAtFrom :
                            LocalDateTime.of(2021, 1, 1, 0, 0);
                    final LocalDateTime till =
                            filters.createdAtTill != null ?
                            filters.createdAtTill :
                            LocalDateTime.now();
                    final Predicate predicate = builder.between(root.get("createdAt"), from, till);
                    predicates.add(predicate);
                }
                
                if(filters.lastModifiedAtFrom != null || filters.lastModifiedAtTill != null) {
                    final LocalDateTime from =
                            filters.lastModifiedAtFrom != null ?
                            filters.lastModifiedAtFrom :
                            LocalDateTime.of(2021, 1, 1, 0, 0);
                    final LocalDateTime till =
                            filters.lastModifiedAtTill != null ?
                            filters.lastModifiedAtTill :
                            LocalDateTime.now();
                    final Predicate predicate = builder.between(root.get("lastModifiedAt"), from, till);
                    predicates.add(predicate);
                }
                
                if(filters.hasImage != null) {
                    final Predicate predicate =
                            filters.hasImage ?
                            builder.isNotNull(root.get("imagePath")) :
                            builder.isNull(root.get("imagePath"));
                    predicates.add(predicate);
                }
                
                if(filters.hasEmail != null) {
                    final Predicate predicate =
                            filters.hasEmail ?
                            builder.isNotNull(root.get("email")) :
                            builder.isNull(root.get("email"));
                    predicates.add(predicate);
                }
                
                if(filters.state != null) {
                    final Predicate predicate = builder.equal(root.get("state"), filters.state);
                    predicates.add(predicate);
                }
                
                if(filters.role != null) {
                    final Predicate predicate = builder.equal(root.get("role"), filters.role);
                    predicates.add(predicate);
                }
                
                if(filters.creatorId != null) {
                    final Predicate predicate = builder.equal(root.get("creator").get("id"), filters.creatorId);
                    predicates.add(predicate);
                }
                
                if(filters.updaterId != null) {
                    final Predicate predicate = builder.equal(root.get("updater").get("id"), filters.updaterId);
                    predicates.add(predicate);
                }
                
            }
            
            query.where(
                    builder.and(predicates.toArray(new Predicate[predicates.size()]))
            );

            return em.createQuery(query)
//                    .setFirstResult(filters != null ? filters.offset : 0)
                    .setFirstResult(0)
//                    .setMaxResults(filters != null ? filters.limit : 50)
                    .setMaxResults(50)
                    .getResultStream()
                    .map(user -> {
                        final long totalReportsCount =
                                user.getReports()
                                .stream()
                                .count();
                        final long totalNotificationsCount =
                                user.getNotifications()
                                .stream()
                                .count();
                        final long totalLoginCount =
                                user.getLogins()
                                .stream()
                                .count();
                        final long totalPlayedTracksCount =
                                user.getPlayedTracks()
                                .stream()
                                .count();
                        return new UserDetails(
                                user.getId(),
                                user.getName(),
                                user.getMobile(),
                                user.getEmail(),
                                user.isEmailVerified(),
                                user.getState(),
                                user.getRole(),
                                user.getCreatedAt(),
                                user.getLastModifiedAt(),
                                user.getCreator().getId(),
                                user.getCreator().getName(),
                                user.getUpdater().getId(),
                                user.getUpdater().getName(),
                                totalReportsCount,
                                totalNotificationsCount,
                                totalLoginCount,
                                totalPlayedTracksCount
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
    public UserDetails getUserById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final UserModel user = em.find(UserModel.class, id.toValue());
            if(user == null) return null;
            
            final long totalReportsCount =
                    user.getReports()
                    .stream()
                    .count();
            final long totalNotificationsCount =
                    user.getNotifications()
                    .stream()
                    .count();
            final long totalLoginCount =
                    user.getLogins()
                    .stream()
                    .count();
            final long totalPlayedTracksCount =
                    user.getPlayedTracks()
                    .stream()
                    .count();
            
            return new UserDetails(
                    user.getId(),
                    user.getName(),
                    user.getMobile(),
                    user.getEmail(),
                    user.isEmailVerified(),
                    user.getState(),
                    user.getRole(),
                    user.getCreatedAt(),
                    user.getLastModifiedAt(),
                    user.getCreator().getId(),
                    user.getCreator().getName(),
                    user.getUpdater().getId(),
                    user.getUpdater().getName(),
                    totalReportsCount,
                    totalNotificationsCount,
                    totalLoginCount,
                    totalPlayedTracksCount
            );
            
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public UsersSummary getUsersSummary() {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final String query = "SELECT users FROM UserModel users";
            
            final List<UserModel> users =
                    em.createQuery(query, UserModel.class)
                    .getResultList();
            
            final UsersSummary summary = new UsersSummary();
            
            summary.totalUsersCount =
                    users.stream()
                    .count();
            summary.activeUsersCount =
                    users.stream()
                    .filter(user -> user.isActive())
                    .count();
            summary.blockedUsersCount =
                    users.stream()
                    .filter(user -> user.isBlocked())
                    .count();
            
            
            return summary;
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public Set<LoginDetails> getLoginsOfUser(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final UserModel user = em.find(UserModel.class, id.toValue());
            if(user == null) return null;
            
            return user.getLogins()
                    .stream()
                    .map(login -> new LoginDetails(
                            user.getId(),
                            user.getName(),
                            user.getMobile(),
                            login.getIpAddress(),
                            login.wasSuccessful(),
                            login.getFailureReason(),
                            login.getAttemptedAt(),
                            login.getPlatform(),
                            login.getOs()
                        )
                    )
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public Set<LoginDetails> getAllLogins(Integer limit, Integer offset) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final String query = "SELECT logins FROM LoginAuditModel logins ORDER BY logins.attemptedAt DESC";
            
            return em.createQuery(query, LoginAuditModel.class)
                    .getResultStream()
                    .map(login -> {
                        return new LoginDetails(
                                login.getUser().getId(),
                                login.getUser().getName(),
                                login.getUser().getMobile(),
                                login.getIpAddress(),
                                login.wasSuccessful(),
                                login.getFailureReason(),
                                login.getAttemptedAt(),
                                login.getPlatform(),
                                login.getOs()
                        );
                    })
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    private static String toSearchPattern(String term) {
        return "%" + term + "%";
    }
    
    public static UserQueryService getInstance() {
        return UserQueryServiceHolder.INSTANCE;
    }
    
    private static class UserQueryServiceHolder {

        private static final UserQueryService INSTANCE =
                new UserQueryService(EntityManagerFactoryHelper.getInstance());
    }
}
