/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.domain.core;

import java.io.*;
import java.time.Duration;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.TikaUtils;
import org.apache.tika.metadata.*;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;
import com.jamapplicationserver.modules.library.domain.core.errors.*;
import javax.sound.sampled.*;

/**
 *
 * @author dada
 */
public class AudioStream extends FilterInputStream {
    
    private static final long MAX_ALLOWED_SIZE = 20000000;
    private static final Duration MAX_ALLOWED_DURATION = Duration.ofMinutes(15);
    private static final TikaUtils tika = TikaUtils.getInstance();
    
    public final long size;
    public final Duration duration;
    public final MusicFileFormat format;
    
    private AudioStream(
            InputStream in,
            long size,
            Duration duration,
            MusicFileFormat format
    ) {
        super(in);
        this.size = size;
        this.duration = duration;
        this.format = format;
    }
    
    public InputStream getStream() {
        return this.in;
    }
    
    public static final Result<AudioStream> createAndValidate(InputStream stream) {
        
        try {
            
            if(stream == null)
                return Result.fail(new ValidationError("فایل صوتی ضروری ست"));

            // stream is not an audio stream
            if(!tika.isAudio(stream))
                return Result.fail(new ValidationError("فایل آهنگ باید فایل صوتی باشد"));
            
            // audio format is not supported
            final String subType = tika.getSubtype(stream);
            final Result<MusicFileFormat> formatOrError = MusicFileFormat.create(subType);
            if(formatOrError.isFailure) return Result.fail(formatOrError.getError());
            final MusicFileFormat format = formatOrError.getValue();
            
//            final Duration duration = getDuration(stream);
//            if(!duration.minus(MAX_ALLOWED_DURATION).isNegative())
//                return Result.fail(new AudioMaxAllowedDurationExceededError());
            
//            final int size = getSize(stream);
//            if(size > MAX_ALLOWED_SIZE)
//                return Result.fail(new MaxAllowedAudioSizeExceededError());
            
            return Result.ok(new AudioStream(stream, 0, Duration.ZERO, format));
            
//        } catch(UnsupportedAudioFileException e) {
//            e.printStackTrace();
//            return Result.fail(new AudioNotSupportedError());
        } catch(IOException e) {
            e.printStackTrace();
            return Result.fail(new ValidationError(e.getMessage()));
        } catch(Exception e) {
            e.printStackTrace();
            return Result.fail(new ValidationError(e.getMessage()));
        }
        
    }
    
    public static final AudioStream create(
            InputStream stream,
            long size,
            Duration duration,
            String format
    ) {
        return new AudioStream(stream, size, duration, MusicFileFormat.create(format).getValue());
    }
    
    private static Duration getDuration(InputStream stream)
            throws UnsupportedAudioFileException,
            IOException, SAXException, TikaException {
        
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        Mp3Parser p = new Mp3Parser();
        
        p.parse(stream, handler, metadata, context);

        final long duration =
                Math.round(
                        Float.parseFloat(
                                metadata.get("xmpDM:duration")
                        ) / 1000
                );
        
        return Duration.ofSeconds(duration);
    }
    
    private static int getSize(InputStream stream) throws IOException {

        final ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        stream.transferTo(byteArray);
        
        return byteArray.size();
    
    }
    
}
