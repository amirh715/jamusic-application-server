/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.utils;

import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author amirhossein
 */
public class MultipartFormDataUtil {
    
    public static Map<String, String> toMap(Set<Part> request) {
        Map<String, String> map;
        
        try {

            map = request
                    .stream()
                    .collect(
                            Collectors.toMap(
                                    p -> p.getName(),
                                    p -> toUTF8(p)
                            )
                    );
            return map;
            
        } catch(Exception e) {
            return new HashMap<>();
        }
    }
    
    public static final Map<String, String> toMap(Collection<Part> parts) {
        Map<String, String> map;
        
        try {
            
            map = parts
                    .stream()
                    .collect(
                            Collectors.toMap(
                                    p -> p.getName(),
                                    p -> toUTF8(p)
                            )
                    );
            return map;
            
        } catch(Exception e) {
            return new HashMap<>();
        }
    }
    
    public static final Map<String, String> toMap(HttpServletRequest req) {
        
        try {
            final Collection<Part> parts = req.getParts();
            return toMap(parts);
        } catch(ServletException | IOException e) {
            return new HashMap<>();
        }
        
    }
    
    public static final String toUTF8(Part part) {
        try {
            return IOUtils.toString(part.getInputStream(), StandardCharsets.UTF_8.name());
        } catch(IOException e) {
            return "";
        }
    }
    
    public static final InputStream toInputStream(Part part) {
        try {
            return part != null ? part.getInputStream() : null;
        } catch(IOException e) {
            LogService.getInstance().error(e);
            return null;
        }
    }
    
    public static final Map<String, InputStream> toInputStream(Collection<Part> parts) {
        final HashMap<String, InputStream> map = new HashMap<>();
        try {
            for(Part part : parts) {
                final String key = part.getSubmittedFileName();
                final InputStream value = part.getInputStream();
                map.put(key, value);
            }
            return map;
        } catch(IOException e) {
            LogService.getInstance().error(e);
            return Map.of();
        }
    }
    
}
