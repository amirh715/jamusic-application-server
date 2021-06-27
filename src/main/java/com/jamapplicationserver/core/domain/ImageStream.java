/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

import java.io.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.TikaUtils;

/**
 *
 * @author dada
 */
public class ImageStream extends FilterInputStream {
    
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
    
    public static final Result<ImageStream> createAndValidate(InputStream stream) throws GenericAppException {
        
        
        try {
            
            if(!tika.isImage(stream)) return Result.fail(new ValidationError(""));
            
            final String subType = tika.getSubtype(stream);
            final Result<ImageFileFormat> formatOrError = ImageFileFormat.create(subType);
            if(formatOrError.isFailure) return Result.fail(formatOrError.getError());
            final ImageFileFormat format = formatOrError.getValue();
            
            final long size = 0;
            
            return Result.ok(new ImageStream(stream, size, format));
            
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
