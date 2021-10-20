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
import com.jamapplicationserver.infra.Services.JWT.JWTUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jamapplicationserver.core.domain.UserRole;
import com.jamapplicationserver.core.domain.events.*;
import com.jamapplicationserver.modules.library.domain.core.subscribers.*;
import com.jamapplicationserver.modules.library.infra.Jobs.*;
import com.jamapplicationserver.infra.Persistence.database.Models.*;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import java.time.*;
import javax.persistence.*;
import java.util.*;
import java.util.stream.*;
import javax.persistence.criteria.*;
import java.util.concurrent.ThreadLocalRandom;
import com.jamapplicationserver.modules.library.repositories.*;

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
            
//            final EntityManager em = EntityManagerFactoryHelper.getInstance().createEntityManager();
//            final EntityTransaction tnx = em.getTransaction();
//            
//            try {
//                
//                tnx.begin();
//                
//                final List<UserModel> players =
//                        em.createQuery("SELECT users FROM UserModel users", UserModel.class)
//                        .getResultList();
//                final List<TrackModel> tracks =
//                        em.createQuery("SELECT tracks FROM TrackModel tracks", TrackModel.class)
//                        .getResultList();
//                final LocalDateTime startDateTime = LocalDateTime.now().minusDays(7);
//
//                for(int i=0; i<10000; i++) {
//                    final UserModel player = players.get(1);
//                    Collections.shuffle(players);
//                    final TrackModel track = tracks.get(1);
//                    Collections.shuffle(tracks);
//                    final LocalDateTime playedAt = startDateTime.plusMinutes(i);
//                    final PlayedModel played = new PlayedModel(track, player, playedAt);
//                    em.persist(played);
//                }
//                
//                tnx.commit();
//                
//            } catch(Exception e) {
//                tnx.rollback();
//            } finally {
//                em.close();
//            }

            try {
                
                EntityManager em = EntityManagerFactoryHelper.getInstance().createEntityManager();
                
                final CriteriaBuilder builder = em.getCriteriaBuilder();
                final CriteriaQuery<LibraryEntityModel> query = builder.createQuery(LibraryEntityModel.class);
                
                Root root = query.from(LibraryEntityModel.class);
                query.select(root);

                System.out.println("Type: " + root.getJavaType().getSimpleName());
                System.out.println("Super Type: " + root.getJavaType().getSuperclass().getSimpleName());
                query.where(
                        builder.or(
                                builder.like(root.get("tags"), "%a")
                        )
                );
                
                em.createQuery(query)
                        .getResultList();
                
            } catch(Exception e) {
                e.printStackTrace();
                throw e;
            }
            
            return "";
        });
        
        before((request, response) -> response.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE"));
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
        before((request, response) -> response.header("Access-Control-Allow-Headers", "x-client-version"));
        
//      SET UP JOB SCHEDUELER
        final JobManager jobManager = JobManager.getInstance();
        
        jobManager
//                .addJob(UpdateTotalPlayedCountJob.class, jobManager.getEveryXSecondsTrigger(5))
//                .addJob(UpdateRateJob.class, jobManager.getEveryXSecondsTrigger(5))
                .startScheduler();
        
        // REGISTER DOMAIN EVENT HANDLERS
        DomainEvents.register(new AfterArtistPublished());
        DomainEvents.register(new AfterArtistArchived());
        DomainEvents.register(new AfterArtistEdited());
        
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
