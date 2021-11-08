/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.notification.domain;

import java.util.*;
import java.time.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.core.domain.*;
import com.jamapplicationserver.modules.notification.domain.errors.*;

/**
 *
 * @author amirhossein
 */
public class Recipient extends Entity {
    
    private final String name;
    private final String mobile;
    private final String email;
    private final boolean emailVerified;
    private final String fcmToken;
    
    private Recipient(
            UniqueEntityId id,
            String name,
            String mobile,
            String email,
            boolean emailVerified,
            String fcmToken,
            DateTime createdAt,
            DateTime lastModifiedAt
    ) {
        super(id, createdAt, lastModifiedAt);
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.emailVerified = emailVerified;
        this.fcmToken = fcmToken;
    }
    
    public static final Result<Recipient> reconstitute(
            UUID id,
            String name,
            String mobile,
            String email,
            boolean isEmailVerified,
            String fcmToken,
            LocalDateTime createdAt,
            LocalDateTime lastModifiedAt
    ) {
        
        final Result<UniqueEntityId> idOrError =
                UniqueEntityId.createFromUUID(id);
        if(idOrError.isFailure) return Result.fail(idOrError.getError());
        
        // at least one contact item must be present (mobile, email, or fcmToken)
        if(mobile == null && email == null && fcmToken == null)
            return Result.fail(new RecipientMustHaveAtLeastOneContactItem());
        
        return Result.ok(
                new Recipient(
                        idOrError.getValue(),
                        name,
                        mobile,
                        email,
                        isEmailVerified,
                        fcmToken,
                        DateTime.createWithoutValidation(createdAt),
                        DateTime.createWithoutValidation(lastModifiedAt)
                )
        );
    }

    public String getName() {
        return this.name;
    }
    
    public String getMobile() {
        return this.mobile;
    }
    
    public boolean hasMobileNo() {
        return this.mobile != null;
    }
    
    public String getEmailAddress() {
        return this.email;
    }
    
    public boolean hasEmailAddress() {
        return this.email != null;
    }
    
    public boolean isEmailVerified() {
        if(email == null) return false;
        return this.emailVerified;
    }
    
    public String getFCMToken() {
        return this.fcmToken;
    }
    
    public boolean hasFCMToken() {
        return this.fcmToken != null;
    }
    
}
