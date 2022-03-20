/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.filesystem;

import java.io.*;
import java.util.stream.*;
import java.nio.file.*;
import java.util.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author amirhossein
 */
public class FilePersistenceManager implements IFilePersistenceManager {
    
    private static boolean isFileStructureSetup = false;
    public static final String BASE_PATH = "/home/api/JamFileStorage/";
    
    private FilePersistenceManager() {
    }
    
    @Override
    public Path write(InputStream stream, Path path) throws IOException {
        
        setupFileStructure();
        
        try {

            Files.copy(stream, path, StandardCopyOption.REPLACE_EXISTING);

            return path;
        } catch(Exception e) {
            LogService.getInstance().fatal(e);
            throw e;
        }

    }
    
    @Override
    public BufferedInputStream read(File file) throws IOException {
        
        try {

            final InputStream is = new FileInputStream(file);
            
            return new BufferedInputStream(is);
            
        } catch(FileNotFoundException e) {
            return null;
        } catch(Exception e) {
            LogService.getInstance().error(e);
            throw e;
        }
        
    }
    
    @Override
    public BufferedInputStream read(File file, int len, int offset) throws IOException {
        
        return read(file);
        
    }
    
    @Override
    public Path delete(File file) throws IOException {
        
        final Path path = file.toPath();
        
        Files.delete(path);
        
        return path;
    }
    
    @Override
    public final Stream<File> walk(Path path) {
        
        try {
            
            final Path basePath = Path.of(BASE_PATH);
            return Files.walk(basePath, FileVisitOption.FOLLOW_LINKS)
                    .filter(Files::isRegularFile)
                    .map(p -> p.toFile());
            
        } catch(IOException e) {
            return Stream.empty();
        }

    }
    
    @Override
    public final Path buildPath(Class clazz) {
        
        final StringBuilder builder = new StringBuilder();
        
        final String prefix = clazz.getSimpleName().toUpperCase();
        
        final String fileName = builder
                .append(prefix)
                .append("_")
                .append(UUID.randomUUID().toString())
                .toString();
        
        return Path.of(BASE_PATH, fileName);
    }
    
    @Override
    public final Path buildPath(Class clazz, String extension) {
        
        return Path.of(this.buildPath(clazz).toString().concat(".").concat(extension));
        
    }

    private static void setupFileStructure() throws IOException {
        
        if(isFileStructureSetup) return;
        
        
        isFileStructureSetup = true;
    }

    public static FilePersistenceManager getInstance() {
        
        return FilePersistenceServiceImplHolder.INSTANCE;
    }
    
    private static class FilePersistenceServiceImplHolder {
        
        private static final FilePersistenceManager INSTANCE =
                new FilePersistenceManager();
    }
    
}
