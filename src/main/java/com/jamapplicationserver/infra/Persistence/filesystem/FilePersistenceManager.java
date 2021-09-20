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
    private static final String BASE_PATH = "/home/dada/Desktop/JamFS";
    
    private FilePersistenceManager() {
    }
    
    @Override
    public Path write(InputStream stream, Path path) throws IOException {
        
        setupFileStructure();
        
        try (
                final InputStream is = stream;
                final OutputStream os = new FileOutputStream(path.toString());
        ) {
            is.transferTo(os);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        final FileOutputStream outputStream = new FileOutputStream(path.toFile());
        
        stream.transferTo(outputStream);
        outputStream.write(stream.readAllBytes());
        
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
