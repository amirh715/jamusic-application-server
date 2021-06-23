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

import java.util.*;
import com.jamapplicationserver.core.domain.UniqueEntityID;
import com.jamapplicationserver.modules.user.domain.UserRole;
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
            UniqueEntityID subject,
            UserRole role,
            Device device
    ) {
        try {
            return JWT.create()
                    .withSubject(subject.toValue().toString())
                    .withExpiresAt(determineExpiryDate(role, device))
                    .withClaim("role", determineUserRole(role, device).getValue())
                    .sign(ALGO);
        } catch(JWTCreationException e) {
            throw e;
        }
    }
    
    public static final DecodedJWT verifyAndDecode(String token) {
        try {
            return JWT.decode(token);
        } catch(JWTDecodeException e) {
            throw e;
        }
    }
    
    private static Date determineExpiryDate(UserRole role, Device device) {
        
        if(device.family.equals(""))
            return Date.from(Instant.now().plus(30, ChronoUnit.DAYS));
        else
            return Date.from(Instant.now().plus(2, ChronoUnit.HOURS));

    }
    
    private static UserRole determineUserRole(UserRole role, Device device) {
        // for mobile clients subscriber-only permissions are granted (regardless of user's role)
        if(device.family.equals(""))
            return UserRole.SUBSCRIBER;
        else
            return role;
    }

}
