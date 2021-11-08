/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver;

import com.jamapplicationserver.infra.Persistence.database.Models.TrackModel;
import com.jamapplicationserver.infra.Persistence.database.Models.UserModel;
import com.jamapplicationserver.infra.Persistence.database.Models.PlayedModel;
import static spark.Spark.*;
import javax.servlet.*;
import com.jamapplicationserver.modules.reports.infra.Jobs.*;
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
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import java.time.*;
import javax.persistence.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import com.jamapplicationserver.modules.notification.infra.Jobs.SendScheduledNotificationsJob;

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

        before((request, response) -> response.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE"));
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
        before((request, response) -> response.header("Access-Control-Allow-Headers", "x-client-version"));
        
//      SET UP JOB SCHEDUELER
        final JobManager jobManager = JobManager.getInstance();
        
        jobManager
//                .addJob(UpdateTotalPlayedCountJob.class, jobManager.getEveryXSecondsTrigger(50))
//                .addJob(UpdateRateJob.class, jobManager.getEveryXSecondsTrigger(50))
                .addJob(SendScheduledNotificationsJob.class, jobManager.getEveryXMinutesTrigger(1))
                .addJob(AssignReportsToProcessorsJob.class, jobManager.getEveryXMinutesTrigger(1))
                .startScheduler();
        
        // REGISTER DOMAIN EVENT HANDLERS
        DomainEvents.register(new AfterArtistPublished());
        DomainEvents.register(new AfterArtistArchived());
        DomainEvents.register(new AfterArtistEdited());
        DomainEvents.register(new AfterPlaylistCreated());
        
        post(("/test"), (req, res) -> {
            
            final EntityManager em =
                        EntityManagerFactoryHelper
                                .getInstance()
                                .createEntityManager();
            final EntityTransaction tnx = em.getTransaction();
            
            try {
                
                tnx.begin();
                
                final List<TrackModel> tracks =
                        em.createQuery("SELECT tracks FROM TrackModel tracks")
                        .getResultList();
                final List<UserModel> users =
                        em.createQuery("SELECT users FROM UserModel users")
                        .getResultList();
                
                final Random random = new Random();
                final int[] forTracks = random.ints(1000, 0, tracks.size()).toArray();
                final int[] forUsers = random.ints(1000, 0, users.size()).toArray();
                final Instant playedAtFrom = Instant.now().minus(Duration.ofDays(7));
                final Instant playedAtTill = Instant.now();
                
                for(int i = 0; i < 1000; i++) {
                    
                    final TrackModel track = tracks.get(forTracks[i]);
                    final UserModel user = users.get(forUsers[i]);
                    final long randomEpochSec =
                            ThreadLocalRandom
                                    .current()
                                    .nextLong(
                                            playedAtFrom.getEpochSecond(),
                                            playedAtTill.getEpochSecond()
                                    );
                    final LocalDateTime playedAt =
                            LocalDateTime.ofEpochSecond(randomEpochSec, 0, ZoneOffset.UTC);
                    
                    final PlayedModel played =
                            new PlayedModel(track, user, playedAt);
                    em.persist(played);
                    
                }
                
                tnx.commit();
                
            } catch(Exception e) {
                e.printStackTrace();
                tnx.rollback();
            } finally {
                em.close();
                return "";
            }
            
        });
        
        // API ROUTES (v1)
        path("/api/v1", () -> {
            
            // configure multipart/formData
            before("/*", (req, res) -> req.raw().setAttribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp")));

            // decode token and set its data
            before("/*", (req, res) -> {
                
                if(
                        req.pathInfo().equals("/api/v1/user/login")
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
