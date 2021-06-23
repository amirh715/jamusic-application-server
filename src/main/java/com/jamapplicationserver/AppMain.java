/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver;

import com.jamapplicationserver.core.domain.DateTime;
import static spark.Spark.*;

import com.jamapplicationserver.modules.user.infra.http.UserRoutes;
import com.jamapplicationserver.infra.ClientVerifier.ClientVerifier;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.infra.Persistence.filesystem.FilePersistenceManager;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import javax.servlet.*;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author amirhossein
 */
public class AppMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        System.out.println("SERVER STARTED AT ");
        
//        post("/test", (req, res) -> {
//            try {
////                req.raw().setAttribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
////                final InputStream sentImage = MultipartFormDataUtil
////                    .toInputStream(
////                            req.raw().getPart("sentImage") != null ?
////                            req.raw().getPart("sentImage") : null
////                    );
//                req.raw().setAttribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
//                final String fileName = MultipartFormDataUtil.toMap(req.raw()).get("image");
//                final FilePersistenceManager service = FilePersistenceManager.getInstance();
//                final InputStream storedImage = service.read(fileName);
//                return service.getContentType(storedImage);
////                return service.getContentType(sentImage);
//            } catch(Exception e) {
//                e.printStackTrace();
//                return "";
//            }
//        });
        
        // API ROUTES (v1)
        path("/api/v1", () -> {
            
            // configure multipart/formData
            before("/*", (req, res) -> req.raw().setAttribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp")));
            
            // verify client program
            before("/*", ClientVerifier.getInstance());
            
            // '/user' - user routes
            path("/user", UserRoutes.getInstance());
            
            // '/library' - library routes
            
            // '/notification' - notification routes
            
            
        }); // API ROUTES ends
        
        // SERVE SUBSCRIBER CLIENT
        
        // SERVE ADMIN CLIENT
        
    }
    
}
