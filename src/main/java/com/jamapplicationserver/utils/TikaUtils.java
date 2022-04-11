/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.utils;

import java.io.*;
import org.apache.tika.mime.MediaType;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.*;
import org.apache.tika.detect.*;
import org.apache.tika.metadata.*;
import org.xml.sax.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author dada
 */
public class TikaUtils {
    
    private final TikaConfig config;
    private final Detector detector;
    
    private TikaUtils() throws TikaException, IOException, SAXException {
        final InputStream resource = getClass().getClassLoader().getResourceAsStream("Config/tika-config.xml");
        this.config = new TikaConfig(resource);
        this.detector = config.getDetector();
    }
    
    public final boolean isImage(InputStream stream) throws IOException {

        if(stream == null) return false;
        final String type = this.getType(stream);
        
        return type.equals("image");
        
    }
    
    public final boolean isAudio(InputStream stream) throws IOException {
        
        final String type = this.getType(stream);
                
        return type.equals("audio");
        
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
        
        return mediaType.getSubtype();
        
    }
    
    public static TikaUtils getInstance() {
        
        try {

            return new TikaUtils();
            
        } catch(TikaException | IOException | SAXException e) {
            LogService.getInstance().error(e);
            return null;
        }
        
    }
    
}
