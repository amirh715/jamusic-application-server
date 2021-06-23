/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.logic;

/**
 *
 * @author dada
 */
public class ErrorDTO {
    
    public final int code;
    public final String type;
    public final String message;
    public final String description;
    
    public ErrorDTO(
            int code,
            String type,
            String message,
            String description
    ) {
        this.code = code;
        this.type = type;
        this.message = message;
        this.description = description;
    }
    
    public ErrorDTO(
            int code,
            String type,
            String message
    ) {
                this.code = code;
        this.type = type;
        this.message = message;
        this.description = null;
    }
    
}
