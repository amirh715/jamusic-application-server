/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.filesystem;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 *
 * @author amirhossein
 */
public class FilePersistenceManager implements IFilePersistenceManager {
    
    private static boolean isFileStructureSetup = false;
    private static final String BASE_PATH = "/";
    
    private FilePersistenceManager() {
    }
    
    @Override
    public Path write(InputStream stream, Path path) throws IOException {
        
        setupFileStructure();
        
        
        
        return path;
    }
    
    @Override
    public BufferedInputStream read(File file) throws IOException {
        
        try {
            
            final FileInputStream fis = new FileInputStream(file);
            
            final BufferedInputStream bis = new BufferedInputStream(fis);
            
            return bis;
            
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    @Override
    public BufferedInputStream read(File file, int len, int offset) throws IOException {
        
        return read(file);
        
    }
    
    @Override
    public Path delete(File file) throws IOException {
        
        final Path path = file.toPath();
        
        Files.deleteIfExists(path);
        
        return path;
    }
    
    @Override
    public final List<File> walk(Path path) {
        
        
        return List.of();
    }
    
    @Override
    public final Path buildPath(Class clazz) {
        
        final StringBuilder builder = new StringBuilder();
        
        String prefix;
        
        switch (clazz.getSimpleName()) {
            case "User":
                prefix = "USER";
                break;
            case "Artist":
                prefix = "ARTIST";
                break;
            case "Album":
                prefix = "ALBUM";
                break;
            case "Track":
                prefix = "TRACK";
                break;
            default:
                prefix = "NA";
                break;
        }
        
        final String fileName = builder
                .append(prefix)
                .append(UUID.randomUUID().toString())
                .toString();
        
        return Path.of(BASE_PATH, fileName);
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
