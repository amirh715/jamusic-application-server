/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

import java.io.*;
import java.util.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.TikaUtils;

/**
 *
 * @author dada
 */
public class ImageStream extends FilterInputStream {
    
    private static final int MAX_ALLOWED_SIZE = 5000000;
    
    public final long size;
    public final ImageFileFormat format;
    
    private static final TikaUtils tika = TikaUtils.getInstance();
    
    private ImageStream(
            InputStream in,
            long size,
            ImageFileFormat format
    ) {
        super(in);
        this.size = size;
        this.format = format;
    }
    
    public final InputStream getStream() {
        return this.in;
    }
    
    public static final Result<ImageStream> createAndValidate(InputStream stream) throws GenericAppException {
        
        if(stream == null) return Result.fail(new ValidationError("فایل تصویر ضروری است"));
        
        try {
            
            if(!tika.isImage(stream)) return Result.fail(new ValidationError("فایل باید فایل تصویری باشد"));
            
            final String subType = tika.getSubtype(stream);
            final Result<ImageFileFormat> formatOrError = ImageFileFormat.create(subType);
            if(formatOrError.isFailure) return Result.fail(formatOrError.getError());
            final ImageFileFormat format = formatOrError.getValue();
            
//            final long size = byteArray.size();
//            if(size > MAX_ALLOWED_SIZE)
//                return Result.fail(new ValidationError("Image size is over the limit"));
            
            return Result.ok(new ImageStream(stream, 0, format));
            
        } catch(IOException e) {
            return Result.fail(new ValidationError("صفات فایل عکس قابل شناسایی نیست"));
        }
        
    }
    
    public static final Result<Optional<ImageStream>> createNullableAndValidate(InputStream stream) throws GenericAppException {
        
        try {
            
            if(!tika.isImage(stream)) return Result.fail(new ValidationError("فایل باید فایل تصویری باشد"));
            
            final String subType = tika.getSubtype(stream);
            final Result<ImageFileFormat> formatOrError = ImageFileFormat.create(subType);
            if(formatOrError.isFailure) return Result.fail(formatOrError.getError());
            final ImageFileFormat format = formatOrError.getValue();
            
//            final long size = byteArray.size();
//            if(size > MAX_ALLOWED_SIZE)
//                return Result.fail(new ValidationError("Image size is over the limit"));
            
            return Result.ok(Optional.of(new ImageStream(stream, 0, format)));
            
        } catch(IOException e) {
            throw new GenericAppException(e);
        }
        
    }
    
    public static final ImageStream create(
            InputStream stream,
            long size,
            ImageFileFormat format
    ) {
        return new ImageStream(stream, size, format);
    }
    
}
