/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.repository;

import com.jamapplicationserver.infra.Persistence.database.Models.UserRoleEnum;
import com.jamapplicationserver.infra.Persistence.database.Models.UserModel;
import com.jamapplicationserver.infra.Persistence.database.Models.UserVerificationModel;
import com.jamapplicationserver.infra.Persistence.database.Models.UserEmailVerificationModel;
import com.jamapplicationserver.infra.Persistence.database.Models.UserStateEnum;
import com.jamapplicationserver.infra.Persistence.database.Models.UserPasswordResetVerificationModel;
import javax.persistence.*;
import javax.persistence.criteria.*;
import org.hibernate.exception.ConstraintViolationException;
import java.util.*;
import java.nio.file.Path;
import java.net.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.user.domain.*;
import com.jamapplicationserver.core.domain.UniqueEntityID;
import java.util.stream.Collectors;
import com.jamapplicationserver.core.domain.DateTime;
import com.jamapplicationserver.core.logic.Result;
import java.time.LocalDateTime;
import com.jamapplicationserver.modules.user.repository.exceptions.*;

/**
 *
 * @author amirhossein
 */
public class UserRepository implements IUserRepository {
    
    private final EntityManagerFactory emf;
    
    private static final int MAX_ALLOWED_USERS = 30;
    
    private UserRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    @Override
    public User fetchById(UniqueEntityID id) {
        
        try {
            
            final EntityManager em = getEntityManager();
            
            final UserModel model =
                    em.find(UserModel.class, id.toValue());
            
            return toDomain(model);
            
        } catch(Exception e) {
            return null;
        }
        
    }
    
    @Override
    public User fetchByMobile(MobileNo mobile) {
        try {
            CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<UserModel> cq =
                    builder
                    .createQuery(UserModel.class);
            Root<UserModel> root = cq.from(UserModel.class);

            cq.where(builder.equal(root.get("mobile"), mobile.getValue()));

            TypedQuery<UserModel> query =
                    getEntityManager().createQuery(cq);

            return toDomain(query.getSingleResult());
        } catch(EntityNotFoundException e) {
            return null;
        } catch(Exception e) {
            throw e;
        }
    }
    
    @Override
    public User fetchByEmail(Email email) {
        
        final EntityManager em = getEntityManager();
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UserModel> cq =
                builder
                .createQuery(UserModel.class);
        Root<UserModel> root = cq.from(UserModel.class);
        
        cq.where(builder.equal(root.get("email"), email.getValue()));
        
        TypedQuery<UserModel> query =
                getEntityManager().createQuery(cq);
        
        return toDomain(query.getSingleResult());
    }

    @Override
    public Set<User> fetchByFilters(UsersFilters filters) {
        
        try {
            
            CriteriaQuery<UserModel> cq = buildCriteriaQuery(filters);
            
            TypedQuery<UserModel> query = getEntityManager().createQuery(cq);
            
            return query
                    .getResultStream()
                    .map(model -> toDomain(model))
                    .collect(Collectors.toSet());
            
        } catch(Exception e) {
            e.printStackTrace();
            return Collections.EMPTY_SET;
        }
        
    }
    
    @Override
    public User fetchByEmailVerificationLink(URL link) {
        
        try {
            
            final EntityManager em = getEntityManager();
            
            TypedQuery<UserModel> query =
                    em.createQuery("SELECT ev.user FROM UserEmailVerificationModel ev", UserModel.class);
            
            return toDomain(query.getSingleResult());
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void save(User user) throws
            ConstraintViolationException,
            MaxAllowedUserLimitReachedException,
            OnlyOneAdminCanExistException,
            EntityNotFoundException,
            Exception
    {
        
        try {
            
            final EntityManager em = getEntityManager();

            UserModel model;
            
            em.getTransaction().begin();
            
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
                            new UsersFilters(
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    UserRole.ADMIN,
                                    null,
                                    null
                            );
                    
                    final CriteriaQuery<UserModel> cq = buildCriteriaQuery(adminOnlyFilter);
                    
                    final UserModel admin = em.createQuery(cq).getSingleResult();
                    
                    // an admin already exists
                    if(admin != null)
                        throw new Exception();
                    
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
            
        }

    }
    
    @Override
    public void remove(User user) {
        
        try {
            
            final EntityManager em = getEntityManager();
            
            em.getTransaction().begin();
            
            final UserModel model = em.find(UserModel.class, user.id.toValue());
            
            em.remove(model);
            
            em.getTransaction().commit();
            
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
        
    }
    
    @Override
    public boolean exists(UniqueEntityID id) {
        try {
            final UserModel model =
                    getEntityManager().find(UserModel.class, id.toValue());
            return model != null;
        } catch(Exception e) {
            throw e;
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
            URL link;
            try {
                link = new URL(vm.getLink());
                emailVerification =
                    EmailVerification.create(link, issuedAt).getValue();
            } catch(MalformedURLException e) {
                System.err.println(e);
            }
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
            vm.setUser(model);
            model.setVerification(vm);
        }
        
        if(entity.getEmailVerification() != null) {
            final URL link = entity.getEmailVerification().getLink();
            final DateTime issuedAt = entity.getEmailVerification().getIssuedAt();
            final UserEmailVerificationModel evm = new UserEmailVerificationModel();
            evm.setLink(link.toString());
            evm.setIssuedAt(issuedAt.getValue());
            evm.setUser(model);
            model.setEmailVerification(evm);
        }
        
        if(entity.getPasswordResetCode() != null) {
            final int code = entity.getPasswordResetCode().getCode();
            final DateTime issuedAt = entity.getPasswordResetCode().getIssuedAt();
            final UserPasswordResetVerificationModel prvm = new UserPasswordResetVerificationModel();
            prvm.setCode(code);
            prvm.setIssuedAt(issuedAt.getValue());
            prvm.setUser(model);
            model.setPasswordResetVerification(prvm);
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
                vm.setUser(model);
                model.setVerification(vm);
            }
        }
        
        if(entity.getEmailVerification() != null) {
            final EmailVerification verification = entity.getEmailVerification();
            final UserEmailVerificationModel verificationModel = model.getEmailVerification();
            if(model.getEmailVerification() != null) {
                verificationModel.setLink(verification.getLink().toString());
                verificationModel.setIssuedAt(verification.getIssuedAt().getValue());
            } else {
                final URL link = entity.getEmailVerification().getLink();
                final DateTime issuedAt = entity.getEmailVerification().getIssuedAt();
                final UserEmailVerificationModel evm = new UserEmailVerificationModel();
                evm.setLink(link.toString());
                evm.setIssuedAt(issuedAt.getValue());
                evm.setUser(model);
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
                prvm.setUser(model);
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
        
        CriteriaBuilder builder = this.emf.getCriteriaBuilder();
        CriteriaQuery cq = builder.createQuery();
        
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
    }
    
    private EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }
    
    public static UserRepository getInstance() {
        return UserRepositoryHolder.INSTANCE;
    }
    
    private static class UserRepositoryHolder {
        
        final static EntityManagerFactory emf =
                EntityManagerFactoryHelper.getInstance()
                        .getFactory();
        
        private static final UserRepository INSTANCE =
                new UserRepository(emf);
    }
}
