/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.infra;

import java.io.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;
import java.security.*;
import org.apache.commons.codec.digest.*;

/**
 *
 * @author dada
 */
public class ETag {
    
    private final String value;
    
    private ETag(String value) {
        this.value = value;
    }
    
    public static final ETag create(Serializable obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            final String hash =
                    new DigestUtils(
                            MessageDigest.getInstance("SHA1")
                    ).digestAsHex(new ByteArrayInputStream(baos.toByteArray()));
            return new ETag(hash);
        } catch(Exception e) {
            return null;
        }
    }
    
    public static final ETag create(InputStream input) {
        try {
            final String hash =
                    new DigestUtils(MessageDigest.getInstance("SHA1")).digestAsHex(input);
            return new ETag(hash);
        } catch(NoSuchAlgorithmException | IOException e) {
            return null;
        }
    }

    public static final ETag reconstitute(String etag) {
        return new ETag(etag);
    }
    
    public final boolean same(ETag etag) {
        return this.value.equals(etag.value);
    }
    
    public String getValue() {
        return this.value;
    }
    
}
