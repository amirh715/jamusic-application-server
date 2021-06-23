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

/**
 *
 * @author dada
 */
public class AudioStream extends FilterInputStream {
    
    private static final long MAX_ALLOWED_SIZE = 20000000;
    private static final Duration MAX_ALLOWED_DURATION = Duration.ofMinutes(12);
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
        this.in = in;
        this.size = size;
        this.duration = duration;
        this.format = format;
    }
    
    public AudioStream compress() {
        return this;
    }
    
    public AudioStream decompress() {
        return this;
    }
    
    public InputStream getStream() {
        return this.in;
    }
    
    public static final Result<AudioStream> createAndValidate(InputStream stream) {
        
        try {

            // stream is not an audio stream
            if(!tika.isAudio(stream))
                return Result.fail(new ValidationError("فایل آهنگ باید فایل صوتی باشد"));
            
            // audio format is not supported
            final String subType = tika.getSubtype(stream);
            final Result<MusicFileFormat> formatOrError = MusicFileFormat.create(subType);
//            if(formatOrError.isFailure) return formatOrError;
            final MusicFileFormat format = formatOrError.getValue();
            
            final Duration duration = getDuration(stream);
            if(duration.minus(MAX_ALLOWED_DURATION).isNegative())
                return Result.fail(new ValidationError("فایل صوتی پشتیبانی نشده است"));
            
            final long size = 0;
            
            return Result.ok(new AudioStream(stream, size, duration, format));
        } catch(IOException e) {
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
    
    private static Duration getDuration(InputStream stream) {
        
        return Duration.ZERO;
    }
    
    
    
}
