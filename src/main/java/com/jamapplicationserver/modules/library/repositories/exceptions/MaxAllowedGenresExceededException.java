/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.repositories.exceptions;

/**
 *
 * @author dada
 */
public class MaxAllowedGenresExceededException extends Exception {
    
    public MaxAllowedGenresExceededException() {
        super("Max allowed genres exceeded");
    }
    
}
