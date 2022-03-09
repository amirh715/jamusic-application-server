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
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.*;

/**
 *
 * @author dada
 */
public class TikaUtils {
    
    private final TikaConfig tikaConfig;
    private final DefaultDetector detector;
    
    private TikaUtils() throws TikaException, IOException, SAXException {
        this.tikaConfig = new TikaConfig("./src/main/resources/Config/tika-config.xml");
        this.detector = new DefaultDetector();
    }
    
    public final boolean isImage(InputStream stream) throws IOException {
        
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
    
    public String test(InputStream stream) throws IOException, SAXException, TikaException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        Mp3Parser p = new Mp3Parser();
        
        p.parse(stream, handler, metadata, context);
        
        return metadata.toString();
    }
    
    public static TikaUtils getInstance() {
        
        try {

            return new TikaUtils();
            
        } catch(TikaException | IOException | SAXException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
//    public static TikaUtils getInstance() {
//        
//        return TikaUtilsHolder.INSTANCE;
//    }
//    
//    private static class TikaUtilsHolder {
//
//        private static final TikaUtils INSTANCE = new TikaUtils();
//    }
}
