/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Services.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import ua_parser.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;
import java.util.*;
import com.jamapplicationserver.core.domain.UniqueEntityId;
import com.jamapplicationserver.core.domain.UserRole;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author amirhossein
 */
public class JWTUtils {
    
    private static final String SECRET = "SECRET";
    private static final Algorithm ALGO = Algorithm.HMAC256(SECRET);

    public static final String generateToken(
            UniqueEntityId subject,
            UserRole role,
            OS os
    ) {
        try {
            return JWT.create()
                    .withSubject(subject.toValue().toString())
                    .withExpiresAt(determineExpiryDate(role, os))
                    .withClaim("role", determineUserRole(role, os).getValue())
                    .sign(ALGO);
        } catch(JWTCreationException e) {
            throw e;
        }
    }
    
    public static final DecodedJWT verifyAndDecode(String token) {
        try {
            return JWT.decode(token);
        } catch(JWTDecodeException e) {
            LogService.getInstance().error(e);
            throw e;
        }
    }
    
    // DOMAIN LOGIC LEAKAGE
    private static Date determineExpiryDate(UserRole role, OS os) {
        if(os.family.equals("iOS") || os.family.equals("Android"))
            return Date.from(Instant.now().plus(30, ChronoUnit.DAYS));
        else
            return Date.from(Instant.now().plus(2, ChronoUnit.HOURS));

    }
    
    // DOMAIN LOGIC LEAKAGE
    private static UserRole determineUserRole(UserRole role, OS os) {
        // for mobile clients subscriber-only permissions are granted (regardless of user's role)
        if(os.family.equals("iOS") || os.family.equals("Android"))
            return UserRole.SUBSCRIBER;
        else
            return role;
    }

}
