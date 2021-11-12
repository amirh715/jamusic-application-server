/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.filesystem;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/**
 *
 * @author amirhossein
 */
public interface IFilePersistenceManager {

    Path write(InputStream stream, Path path) throws IOException;
    
    BufferedInputStream read(File file) throws IOException;
    
    BufferedInputStream read(File file, int len, int offset) throws IOException;
    
    Path delete(File file) throws IOException;
    
    Path buildPath(Class clazz);
    
    Path buildPath(Class clazz, String extension);
    
    Stream<File> walk(Path path);
    
}
