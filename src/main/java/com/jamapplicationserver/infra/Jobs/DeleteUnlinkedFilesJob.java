/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Jobs;

import java.util.*;
import java.util.stream.*;
import org.quartz.*;
import javax.persistence.*;
import java.nio.file.*;
import java.io.File;
import com.jamapplicationserver.infra.Persistence.database.EntityManagerFactoryHelper;
import com.jamapplicationserver.infra.Persistence.filesystem.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 * A file is considered "unlinked" if no entity record with image path is referencing it.
 * Likewise, a file is considered "valid" if at least one entity record with image path is referencing it.
 * @author dada
 */
public class DeleteUnlinkedFilesJob implements Job {
    
    @Override
    public void execute(JobExecutionContext context) {
        
        final EntityManager em =
                EntityManagerFactoryHelper.getInstance()
                .createEntityManager();
        final IFilePersistenceManager persistence =
                FilePersistenceManager.getInstance();
        
        try {
            
            // all files
            final Path basePath = Path.of(FilePersistenceManager.BASE_PATH);
            final Stream<File> allFiles =
                        persistence.walk(basePath);
            
            // all valid file paths
            final Set<String> validPaths = new HashSet<>();
            
            // image paths of users profile image
            {
                final String query = "SELECT u.imagePath FROM UserModel u WHERE "
                        + "u.imagePath IS NOT NULL";
                final Set<String> paths =
                        em.createQuery(query, String.class)
                        .getResultStream()
                        .collect(Collectors.toSet());
                validPaths.addAll(paths);
            }
            
            // image paths of library entities image
            {
                final String query = "SELECT le.imagePath FROM LibraryEntityModel le "
                        + "WHERE le.imagePath IS NOT NULL";
                final Set<String> paths =
                        em.createQuery(query, String.class)
                        .getResultStream()
                        .collect(Collectors.toSet());
                validPaths.addAll(paths);
            }
            
            // tracks audio paths
            {
                final String query = "SELECT t.audioPath FROM TrackModel t "
                        + "WHERE t.audioPath IS NOT NULL";
                final Set<String> paths =
                        em.createQuery(query, String.class)
                        .getResultStream()
                        .collect(Collectors.toSet());
                validPaths.addAll(paths);
            }
            
            // image paths of showcase images
            {
                final String query = "SELECT s.imagePath FROM ShowcaseModel s "
                        + "WHERE s.imagePath IS NOT NULL";
                final Set<String> paths =
                        em.createQuery(query, String.class)
                        .getResultStream()
                        .collect(Collectors.toSet());
                validPaths.addAll(paths);
            }
            
            // remove unlinked files
            for(File file : allFiles.collect(Collectors.toSet())) {
                if(!validPaths.contains(file.getAbsolutePath()))
                    persistence.delete(file);
            }
            
        } catch(Exception e) {
            LogService.getInstance().warn(e);
        } finally {
            em.close();
        }
        
    }
    
}
