/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver;

import static spark.Spark.*;
import javax.servlet.*;
import com.jamapplicationserver.modules.user.infra.http.UserRoutes;
import com.jamapplicationserver.modules.library.infra.http.LibraryRoutes;
import com.jamapplicationserver.modules.reports.infra.http.ReportRoutes;
import com.jamapplicationserver.modules.showcase.infra.http.ShowcaseRoutes;
import com.jamapplicationserver.modules.notification.infra.http.NotificationRoutes;
import com.jamapplicationserver.infra.Services.JobManager;
import com.jamapplicationserver.modules.reports.infra.Jobs.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import com.jamapplicationserver.infra.Services.JWT.JWTUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jamapplicationserver.core.domain.UserRole;

// Tempo
import java.util.*;
import javax.persistence.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.modules.library.repositories.mappers.GenreMapper;
import com.jamapplicationserver.modules.library.domain.core.Genre;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.utils.TikaUtils;
import javax.servlet.http.Part;
import java.io.*;
import org.apache.tika.metadata.Metadata;  
import org.apache.tika.parser.ParseContext;  
import org.apache.tika.parser.mp3.Mp3Parser;  
import org.apache.tika.sax.BodyContentHandler;  
import com.jamapplicationserver.modules.library.domain.core.AudioStream;
import ua_parser.Device;
import org.apache.logging.log4j.*;

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
        
        before("/*", (req, res) -> req.raw().setAttribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp")));
                
        post("/test", (req, res) -> {

            final EntityManager em = EntityManagerFactoryHelper.getInstance().getEntityManager();
            
            
            
            return "";
        });
        
        
        // set up job scheduler
        final JobManager jobManager = JobManager.getInstance();
//        jobManager
//                .addJob(
//                        RemoveUnverifiedUsersJob.class,
//                        jobManager.getEverySecondsTrigger(10) // 30 mins
//                )
//                .addJob(
//                        RemoveUnverifiedEmailsJob.class,
//                        jobManager.getEverySecondsTrigger(10) // 30 mins
//                )
//                .addJob(
//                        AssignReportsToProcessorsJob.class,
//                        jobManager.getEverySecondsTrigger(10) // every week day at 8
//                )
//                .addJob(
//                        ArchiveReportsJob.class,
//                        jobManager.getEverySecondsTrigger(10) // every year
//                )
//                .addJob(
//                        RateLibraryEntitiesJob.class,
//                        jobManager.getEverySecondsTrigger(10) // every 2 hours
//                )
//                .addJob(
//                        CountLibraryEntitiesPlayedJob.class,
//                        jobManager.getEverySecondsTrigger(10) // every 2 hours
//                )
//                .startScheduler();
        
        before((request, response) -> response.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE"));
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
        before((request, response) -> response.header("Access-Control-Allow-Headers", "x-client-version"));
        
        // API ROUTES (v1)
        path("/api/v1", () -> {
            
            // configure multipart/formData
            before("/*", (req, res) -> req.raw().setAttribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp")));

            // decode token and set its data
            before("/*", (req, res) -> {
                
                if(
                        req.pathInfo().equals("/api/v1/user/login") ||
                        req.pathInfo().equals("/api/v1/user/")
                ) return;
                
                final String token = req.headers("Authorization").replace("Bearer ", "");
                DecodedJWT decoded = JWTUtils.verifyAndDecode(token);
                
                req.session().attribute("subjectId", decoded.getSubject());
                req.session().attribute("role", decoded.getClaim("role").asString());
                
                System.out.println(
                        "Subject ID : " +
                        req.session().attribute("subjectId")
                );
                System.out.println(
                        "Role : " +
                        req.session().attribute("role")
                );
                
            });
            
            // '/user' - user routes
            path("/user", UserRoutes.getInstance());
            
            // '/library' - library routes
            path("/library", LibraryRoutes.getInstance());
            
            // '/notification' - notification routes
            path("/notification", NotificationRoutes.getInstance());
            
            // '/reports' - reports routes
            path("/report", ReportRoutes.getInstance());
            
            // '/showcase' - showcase routes
            path("/showcase", ShowcaseRoutes.getInstance());
            
            // '/stats' - stats routes
            
            
        }); // API ROUTES ends
        
        // SERVE SUBSCRIBER CLIENT
        
        // SERVE ADMIN CLIENT
        
    }
    
}
