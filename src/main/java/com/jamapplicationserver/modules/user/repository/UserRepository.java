/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import org.hibernate.exception.ConstraintViolationException;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.core.logic.Result;
import com.jamapplicationserver.modules.user.repository.exceptions.*;

/**
 *
 * @author amirhossein
 */
public class UserRepository implements IUserRepository {
    
    private final EntityManagerFactoryHelper emfh;
    
    private static final int MAX_ALLOWED_USERS = 30;
    
    private UserRepository(EntityManagerFactoryHelper emfh) {
        this.emfh = emfh;
    }
    
    @Override
    public User fetchById(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final UserModel model =
                    em.find(UserModel.class, id.toValue());
            if(model == null) return null;
            
            return toDomain(model);
            
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public User fetchByMobile(MobileNo mobile) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<UserModel> cq =
                    builder
                    .createQuery(UserModel.class);
            Root<UserModel> root = cq.from(UserModel.class);

            cq.where(builder.equal(root.get("mobile"), mobile.getValue()));

            TypedQuery<UserModel> query = em.createQuery(cq);

            return toDomain(query.getSingleResult());
        } catch(NoResultException e) {
            return null;
        } catch(EntityNotFoundException e) {
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public User fetchByEmail(Email email) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
        
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<UserModel> cq =
                    builder
                    .createQuery(UserModel.class);
            Root<UserModel> root = cq.from(UserModel.class);

            cq.where(builder.equal(root.get("email"), email.getValue()));

            TypedQuery<UserModel> query = em.createQuery(cq);

            return toDomain(query.getSingleResult());
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }

    @Override
    public Set<User> fetchByFilters(UsersFilters filters) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            CriteriaQuery<UserModel> cq = buildCriteriaQuery(filters);
            
            TypedQuery<UserModel> query = em.createQuery(cq);
            
            return query
                    .getResultStream()
                    .map(model -> toDomain(model))
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public User fetchByEmailVerificationToken(UUID token) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final String query = "SELECT users FROM UserModel users WHERE users.emailVerification.token = ?1";
            
            final UserModel user =
                    em.createQuery(query, UserModel.class)
                    .setParameter(1, token)
                    .getSingleResult();
            
            return toDomain(user);
            
        } catch(NoResultException e) {
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public void save(User user) throws
            ConstraintViolationException,
            MaxAllowedUserLimitReachedException,
            OnlyOneAdminCanExistException,
            EntityNotFoundException,
            Exception
    {
        
        final EntityManager em = emfh.createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {

            UserModel model;
            
            tnx.begin();
            
            if(this.exists(user.id)) { // update existing user
                
                model = em.find(UserModel.class, user.id.toValue());
                
                model = mergeForPersistence(model, user);

                // someone else has updated the user
                UserModel updater;
                if(!user.getUpdaterId().equals(user.id))
                    updater = em.getReference(UserModel.class, user.getUpdaterId().toValue());
                else // user updated by herself
                    updater = model;
                
                model.setUpdater(updater);
                
                // save user
                em.merge(model);
                
            } else { // insert new user
                
                // only one admin can exist
                if(user.isAdmin()) {
                    
                    final UsersFilters adminOnlyFilter =
                            new UsersFilters();
                    adminOnlyFilter.role = UserRoleEnum.ADMIN;
                    
                    final CriteriaQuery<UserModel> cq = buildCriteriaQuery(adminOnlyFilter);
                    
                    final UserModel admin = em.createQuery(cq).getSingleResult();
                    
                    // an admin already exists
                    if(admin != null)
                        throw new OnlyOneAdminCanExistException();
                    
                }
                
                // max allowed users limit reached
                final long usersCount =
                        (long) em.createQuery("SELECT COUNT(*) FROM UserModel").getSingleResult();
                if(usersCount >= MAX_ALLOWED_USERS) throw new MaxAllowedUserLimitReachedException();
                
                final long adminCount =
                        (long) em.createQuery("SELECT COUNT(*) FROM UserModel WHERE role = 'ADMIN'")
                        .getSingleResult();
                if(adminCount > 1) throw new OnlyOneAdminCanExistException();
                
                model = toPersistence(user);
                
                UserModel creator;
                // someone else has created the user
                if(!user.getCreatorId().equals(user.id))
                    creator = em.getReference(UserModel.class, user.getCreatorId().toValue());
                else // user created by himself
                    creator = model;

                model.setCreator(creator);
                model.setUpdater(creator);
                
                // save user
                em.persist(model);
                
            }
                
            em.getTransaction().commit();
            
        } catch(RollbackException e) {

            if(e.getCause() != null && e.getCause().getCause() != null) {
                
                final ConstraintViolationException exception = (ConstraintViolationException)e.getCause().getCause();
                
                final String constraintName = exception.getConstraintName();
                String message = "Unknown constraint violation";
                if(constraintName.equals("mobile_unique_key"))
                    message = "Mobile No. " + user.getMobile().getValue() + " is already present in the database";
                if(constraintName.equals("email_unique_key"))
                    message = "Email address " + user.getEmail().getValue() + " is already present in the database";
                if(constraintName.equals("creator_id_fk_key"))
                    message = "Creator user " + user.getCreatorId() + " does not exist";
                if(constraintName.equals("updater_id_fk_key"))
                    message = "Updater user " + user.getUpdaterId() + " does not exist";
                throw new ConstraintViolationException(message, exception.getSQLException(), constraintName);
                
            }
            
            tnx.rollback();
        } finally {
            em.close();
        }

    }
    
    /**
     * NOTICE: This method is broken. DO NOT USE!
     * @param user
     * @throws RemovingUserIsNotActiveException
     * @throws RemovingUserIsNotAdminException 
     */
    @Override
    public void remove(User user)
            throws RemovingUserIsNotActiveException,
            RemovingUserIsNotAdminException
            {
        
        final EntityManager em = emfh.createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {
                
            tnx.begin();
            
            final UserModel model = em.find(UserModel.class, user.id.toValue());
            
            UserModel updater;
            if(user.getId().equals(user.getUpdaterId()))
                updater = model;
            else
                updater = em.find(UserModel.class, user.getUpdaterId().toValue());
            
            if(!updater.isActive())
                throw new RemovingUserIsNotActiveException();
            
            if(!model.equals(updater) && !updater.isAdmin())
                throw new RemovingUserIsNotAdminException();
            
            em.remove(model);
            
            tnx.commit();
            
        } catch(Exception e) {
            e.printStackTrace();
            tnx.rollback();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public void remove(User user, UniqueEntityId removerId)
            throws RemovingUserIsNotActiveException,
            RemovingUserIsNotAdminException,
            RemovingUserDoesNotExistException {
        
        final EntityManager em = emfh.createEntityManager();
        final EntityTransaction tnx = em.getTransaction();
        
        try {
            
            tnx.begin();
            
            System.out.println("Remover id: " + removerId.toValue().toString());
            
            final UserModel model = em.find(UserModel.class, user.getId().toValue());
            
            UserModel updater;
            if(user.getId().equals(removerId)) {
                updater = model;
            } else {
                updater = em.find(UserModel.class, removerId.toValue());
            }
            
            if(updater == null)
                throw new RemovingUserDoesNotExistException();
            
            if(!updater.isActive())
                throw new RemovingUserIsNotActiveException();
            
            if(!updater.equals(model) && !updater.isAdmin())
                throw new RemovingUserIsNotAdminException();
            
            em.remove(model);
            
            tnx.commit();
            
        } catch(Exception e) {
            tnx.rollback();
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    @Override
    public boolean exists(UniqueEntityId id) {
        
        final EntityManager em = emfh.createEntityManager();
        
        try {
            
            final UserModel model =
                    em.find(UserModel.class, id.toValue());
            
            return model != null;
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }

    private static User toDomain(UserModel model) {

        UserVerification verification = null;
        EmailVerification emailVerification = null;
        PasswordResetCode passwordResetCode = null;
        
        if(model.getVerification() != null) {
            final UserVerificationModel vm = model.getVerification();
            final DateTime issuedAt = DateTime.create(vm.getIssuedAt()).getValue();
            final int code = vm.getCode();
            verification = UserVerification.create(issuedAt, code);
        }
        
        if(model.getEmailVerification() != null) {
            final UserEmailVerificationModel vm = model.getEmailVerification();
            final DateTime issuedAt = DateTime.create(vm.getIssuedAt()).getValue();
            emailVerification =
                EmailVerification.create(vm.getToken(), issuedAt).getValue();
        }
        
        if(model.getPasswordResetVerification() != null) {
            final UserPasswordResetVerificationModel pvm = model.getPasswordResetVerification();
            final DateTime issuedAt = DateTime.create(pvm.getIssuedAt()).getValue();
            final int code = pvm.getCode();
            passwordResetCode = PasswordResetCode.create(code, issuedAt).getValue();
        }
        
        final Result<User> entity = User.reconstitute(
                model.getId(),
                model.getName(),
                model.getMobile(),
                model.getPassword(),
                model.getRole().toString(),
                model.getState().toString(),
                model.getEmail(),
                model.isEmailVerified(),
                model.getImagePath() != null ? Path.of(model.getImagePath()) : null,
                model.getFCMToken(),
                model.getCreatedAt(),
                model.getLastModifiedAt(),
                model.getCreator().getId(),
                model.getUpdater().getId(),
                verification,
                emailVerification,
                passwordResetCode
        );

        return entity.getValue();
    }
    
    private static UserModel toPersistence(User entity) {
        
        UserModel model = new UserModel();
        
        if(entity.getUserVerification() != null) {
            final int code = entity.getUserVerification().getCode();
            final DateTime issuedAt = entity.getUserVerification().getIssuedAt();
            final UserVerificationModel vm = new UserVerificationModel();
            vm.setCode(code);
            vm.setIssuedAt(issuedAt.getValue());
            model.setVerification(vm);
        } else {
            model.setVerification(null);
        }
        
        if(entity.getEmailVerification() != null) {
            final UUID token = entity.getEmailVerification().getToken();
            final DateTime issuedAt = entity.getEmailVerification().getIssuedAt();
            final UserEmailVerificationModel evm = new UserEmailVerificationModel();
            evm.setToken(token);
            evm.setIssuedAt(issuedAt.getValue());
            model.setEmailVerification(evm);
        } else {
            model.setEmailVerification(null);
        }
        
        if(entity.getPasswordResetCode() != null) {
            final int code = entity.getPasswordResetCode().getCode();
            final DateTime issuedAt = entity.getPasswordResetCode().getIssuedAt();
            final UserPasswordResetVerificationModel prvm = new UserPasswordResetVerificationModel();
            prvm.setCode(code);
            prvm.setIssuedAt(issuedAt.getValue());
            model.setPasswordResetVerification(prvm);
        } else {
            model.setPasswordResetVerification(null);
        }
        
        model.setId(entity.id.toValue());
        model.setName(entity.getName().getValue());
        model.setMobile(entity.getMobile().getValue());
        model.setPassword(entity.getPassword().getValue());
        model.setEmail(
                entity.getEmail() != null ?
                        entity.getEmail().getValue() : null
        );
        model.setEmailVerified(entity.isEmailVerified());
        model.setState(UserStateEnum.valueOf(entity.getState().getValue()));
        model.setRole(UserRoleEnum.valueOf(entity.getRole().getValue()));
        model.setImagePath(entity.getImagePath() != null ? entity.getImagePath().toAbsolutePath().toString() : null);
        model.setCreatedAt(entity.getCreatedAt().getValue());
        model.setLastModifiedAt(entity.getLastModifiedAt().getValue());
        model.setFCMToken(
                entity.getFCMToken() != null ?
                        entity.getFCMToken().getValue() : null
        );
        
        return model;
    }
    
    public static final UserModel mergeForPersistence(UserModel model, User entity) {

        if(entity.getUserVerification() != null) {
            final UserVerification verification = entity.getUserVerification();
            final UserVerificationModel verificationModel = model.getVerification();
            if(model.getVerification() != null) {
                verificationModel.setCode(verification.getCode());
                verificationModel.setIssuedAt(verification.getIssuedAt().getValue());
            } else {
                final int code = verification.getCode();
                final DateTime issuedAt = verification.getIssuedAt();
                final UserVerificationModel vm = new UserVerificationModel();
                vm.setCode(code);
                vm.setIssuedAt(issuedAt.getValue());
                model.setVerification(vm);
            }
        }
        
        if(entity.getEmailVerification() != null) {
            final EmailVerification verification = entity.getEmailVerification();
            final UserEmailVerificationModel verificationModel = model.getEmailVerification();
            if(model.getEmailVerification() != null) {
                verificationModel.setToken(verification.getToken());
                verificationModel.setIssuedAt(verification.getIssuedAt().getValue());
            } else {
                final UUID token = entity.getEmailVerification().getToken();
                final DateTime issuedAt = entity.getEmailVerification().getIssuedAt();
                final UserEmailVerificationModel evm = new UserEmailVerificationModel();
                evm.setToken(token);
                evm.setIssuedAt(issuedAt.getValue());
                model.setEmailVerification(evm);
            }
        }
        
        if(entity.getPasswordResetCode() != null) {
            final PasswordResetCode verification = entity.getPasswordResetCode();
            final UserPasswordResetVerificationModel verificationModel = model.getPasswordResetVerification();
            if(model.getPasswordResetVerification() != null) {
                verificationModel.setCode(verification.getCode());
                verificationModel.setIssuedAt(verification.getIssuedAt().getValue());
            } else {
                final int code = entity.getPasswordResetCode().getCode();
                final DateTime issuedAt = entity.getPasswordResetCode().getIssuedAt();
                final UserPasswordResetVerificationModel prvm = new UserPasswordResetVerificationModel();
                prvm.setCode(code);
                prvm.setIssuedAt(issuedAt.getValue());
                model.setPasswordResetVerification(prvm);
            }
        }
        
        model.setName(entity.getName().getValue());
        model.setMobile(entity.getMobile().getValue());
        model.setPassword(entity.getPassword().getValue());
        model.setEmail(
                entity.getEmail() != null ?
                        entity.getEmail().getValue() : null
        );
        model.setEmailVerified(entity.isEmailVerified());
        model.setState(UserStateEnum.valueOf(entity.getState().getValue()));
        model.setRole(UserRoleEnum.valueOf(entity.getRole().getValue()));
        model.setImagePath(entity.getImagePath() != null ? entity.getImagePath().toAbsolutePath().toString() : null);
        model.setCreatedAt(entity.getCreatedAt().getValue());
        model.setLastModifiedAt(entity.getLastModifiedAt().getValue());
        model.setFCMToken(
                entity.getFCMToken() != null ?
                        entity.getFCMToken().getValue() : null
        );
        
        return model;
    }
    
    private static String toSearchPattern(String term) {
        return "%" + term + "%";
    }
    
    private CriteriaQuery<UserModel> buildCriteriaQuery(UsersFilters filters) {
        
        final EntityManager em = emfh.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery cq = builder.createQuery();
        
        try {
            
            Root<UserModel> root = cq.from(UserModel.class);
        
            cq.select(root);

            cq.orderBy();

            List<Predicate> predicates = new ArrayList<>();

            if(filters != null) {

                // searchTerm
                if(filters.searchTerm != null) {
                    predicates.add(
                            builder.or(
                                    builder.like(root.get("name"), toSearchPattern(filters.searchTerm)),
                                    builder.like(root.get("mobile"), toSearchPattern(filters.searchTerm)),
                                    builder.like(root.get("email"), toSearchPattern(filters.searchTerm))
                            )
                    );
                }

                // state
                if(filters.state != null) {
                    predicates.add(
                            builder.equal(root.get("state"), filters.state)
                    );
                }

                // role
                if(filters.role != null) {
                    predicates.add(
                            builder.equal(root.get("role"), filters.role)
                    );
                }

                // hasImage
                if(filters.hasImage != null && filters.hasImage)
                    predicates.add(builder.isNotNull(root.get("imagePath")));
                else if(filters.hasImage != null && !filters.hasImage)
                    predicates.add(builder.isNull(root.get("imagePath")));

                // hasEmail
                if(filters.hasEmail != null && filters.hasEmail)
                    predicates.add(builder.isNotNull(root.get("email")));
                else if(filters.hasEmail != null && !filters.hasEmail)
                    predicates.add(builder.isNull(root.get("email")));

                // createdAt from till
                if(
                        filters.createdAtFrom != null ||
                        filters.createdAtTill != null
                ) {
                    final LocalDateTime from = filters.createdAtFrom != null ?
                            filters.createdAtFrom : LocalDateTime.of(2020, 1, 1, 0, 0);
                    final LocalDateTime till = filters.createdAtTill != null ?
                            filters.createdAtTill : LocalDateTime.now();
                    predicates.add(
                            builder.between(
                                    root.get("createdAt"),
                                    from,
                                    till
                            )
                    );
                }

                // lastModifiedAt from till
                if(
                        filters.lastModifiedAtFrom != null ||
                        filters.lastModifiedAtTill != null
                ) {
                    final LocalDateTime from = filters.lastModifiedAtFrom != null ?
                            filters.lastModifiedAtFrom : LocalDateTime.of(2020, 1, 1, 0, 0);
                    final LocalDateTime till = filters.lastModifiedAtTill != null ?
                            filters.lastModifiedAtTill : LocalDateTime.now();
                    predicates.add(
                            builder.between(
                                    root.get("lastModifiedAt"),
                                    from,
                                    till
                            )
                    );
                }

                // creatorId

                // updaterId

                cq.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
            } // if filters != null ENDS

            return cq;
            
        } catch(Exception e) {
            throw e;
        } finally {
            em.close();
        }
        
    }
    
    public static UserRepository getInstance() {
        return UserRepositoryHolder.INSTANCE;
    }
    
    private static class UserRepositoryHolder {
        
        final static EntityManagerFactoryHelper emfh =
                EntityManagerFactoryHelper.getInstance();
        
        private static final UserRepository INSTANCE =
                new UserRepository(emfh);
    }
}
