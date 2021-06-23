/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.utils;

import java.io.*;
import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.*;
import org.apache.tika.detect.*;
import org.apache.tika.metadata.*;
import org.xml.sax.SAXException;

/**
 *
 * @author dada
 */
public class TikaUtils {
    
    private final TikaConfig tikaConfig;
    private final DefaultDetector detector;
    
    private TikaUtils() {
        try {
            this.tikaConfig = new TikaConfig("./src/main/resources/Config/tika-config.xml");
            this.detector = new DefaultDetector();
        } catch(TikaException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }
    
    public final boolean isImage(InputStream stream) throws IOException {
        
        final String type = this.getType(stream);
        
        return type.equals("image");
        
    }
    
    public final boolean isAudio(InputStream stream) throws IOException {
        
        final String subType = this.getSubtype(stream);
        
        return subType.equals("audio");
        
    }
    
    public final String getContentType(InputStream stream) throws IOException {
                
        final Metadata metadata = new Metadata();
        final MediaType mediaType = detector.detect(stream, metadata);
        
        final String type = mediaType.getType();
        final String subType = mediaType.getSubtype();
        
        return type + "/" + subType;
    }
    
    public String getType(InputStream stream) throws IOException {
        
        final Metadata metadata = new Metadata();
        final MediaType mediaType = detector.detect(stream, metadata);
        
        return mediaType.getType();
        
    }
    
    public String getSubtype(InputStream stream) throws IOException {
        
        final Metadata metadata = new Metadata();
        final MediaType mediaType = detector.detect(stream, metadata);
        
        return mediaType.getType();
        
    }
    
    public static TikaUtils getInstance() {
        
        return TikaUtilsHolder.INSTANCE;
    }
    
    private static class TikaUtilsHolder {

        private static final TikaUtils INSTANCE = new TikaUtils();
    }
}
