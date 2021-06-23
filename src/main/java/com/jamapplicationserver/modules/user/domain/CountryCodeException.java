/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.domain;

/**
 *
 * @author amirhossein
 */
public class CountryCodeException extends Exception {
    
    public CountryCodeException(String mobile) {
        super("Mobile number calling code is not supported yet");
    }
    
}
