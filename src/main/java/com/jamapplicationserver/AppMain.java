/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver;

import static spark.Spark.*;
import javax.servlet.*;
import java.util.Set;
import com.jamapplicationserver.modules.user.infra.http.UserRoutes;
import com.jamapplicationserver.modules.library.infra.http.LibraryRoutes;
import com.jamapplicationserver.modules.reports.infra.http.ReportRoutes;
import com.jamapplicationserver.modules.showcase.infra.http.ShowcaseRoutes;
import com.jamapplicationserver.modules.notification.infra.http.NotificationRoutes;
import com.jamapplicationserver.infra.Services.JobManager;
import com.jamapplicationserver.infra.Services.JWT.JWTUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jamapplicationserver.core.domain.events.*;
import com.jamapplicationserver.modules.library.domain.core.subscribers.*;
import com.jamapplicationserver.modules.notification.domain.subscribers.*;
import com.jamapplicationserver.modules.library.infra.Jobs.*;
import com.jamapplicationserver.modules.notification.infra.Jobs.*;
import com.jamapplicationserver.modules.reports.infra.Jobs.*;
import com.jamapplicationserver.infra.Jobs.DeleteUnlinkedFilesJob;
import com.jamapplicationserver.infra.Services.LogService.LogService;

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
        
        System.out.println("SERVER STARTED");
        
        before("/*", (req, res) -> req.raw().setAttribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp")));
        
        before((req, res) -> {
            res.header("Access-Control-Allow-Methods", "GET,POST,OPTIONS,DELETE,PUT,HEAD");
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Headers", "Authorization");
        });

        options("/*", (request, response) -> {
            final Set<String> allowedOrigins = Set.of("https://jamusicapp.ir", "https://admin.jamusicapp.ir");
            
            String accessControlRequestHeaders = request
                    .headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                if(allowedOrigins.contains(accessControlRequestHeaders)) return halt(400);
                response.header("Access-Control-Allow-Headers",
                        accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request
                    .headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods",
                        accessControlRequestMethod);
            }

            return "OK";
        });
        
//      SET UP JOB SCHEDUELER
        final JobManager jobManager = JobManager.getInstance();
        jobManager
                .addJob(DeleteUnlinkedFilesJob.class, jobManager.getDailyTrigger(0, 0))
                .addJob(SendScheduledNotificationsJob.class, jobManager.getEveryXMinutesTrigger(1))
                .addJob(AssignReportsToProcessorsJob.class, jobManager.getEveryXMinutesTrigger(15))
                .addJob(UpdateTotalPlayedCountJob.class, jobManager.getEveryXHoursTrigger(1))
                .addJob(UpdateRateJob.class, jobManager.getEveryXHoursTrigger(2))
                .startScheduler();
        
        // REGISTER DOMAIN EVENT HANDLERS
        DomainEvents.register(new AfterArtistPublished());
        DomainEvents.register(new AfterArtistArchived());
        DomainEvents.register(new AfterArtistEdited());
        DomainEvents.register(new AfterPlaylistCreated());
        DomainEvents.register(new AfterUserBlocked());
        DomainEvents.register(new AfterTrackArchived());
        
        // API ROUTES (v1)
        path("api/v1", () -> {
            
            // configure multipart/formData
            before("/*", (req, res) -> req.raw().setAttribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp")));
            
            // decode token and set its data
            before("/*", (req, res) -> {
                
                try {
                    
                    if(
                            req.pathInfo().equals("api/v1/user/login") ||
                            req.pathInfo().equals("api/v1/user/request-account-verification") ||
                            req.pathInfo().equals("api/v1/user/verify-account") ||
                            req.pathInfo().equals("api/v1/user/request-password-reset") ||
                            req.pathInfo().equals("api/v1/user/reset-password") ||
                            req.pathInfo().equals("api/v1/user/verify-email")
                    ) {

                    } else {

                        if(!req.requestMethod().equals("OPTIONS")) {

                            final String token = req.headers("Authorization").replace("Bearer ", "");
                            DecodedJWT decoded = JWTUtils.verifyAndDecode(token);

                            req.session().attribute("subjectId", decoded.getSubject());
                            req.session().attribute("subjectRole", decoded.getClaim("role").asString());
                            
                        }

                    }
                    
                } catch(Exception e) {
                    LogService.getInstance().error(e);
                }
                
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
            
        }); // API ROUTES ends
        
    }
    
}
