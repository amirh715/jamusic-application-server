/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Filters;

import spark.Request;
import spark.Response;
import spark.Filter;
import static spark.Spark.*;

/**
 *
 * @author dada
 */
public class Tokenn implements Filter {
    
    private Tokenn() {
    }
    
    @Override
    public void handle(Request req, Response res) {
        
        
        
    }
    
    public static Tokenn getInstance() {
        return TokennHolder.INSTANCE;
    }
    
    private static class TokennHolder {

        private static final Tokenn INSTANCE = new Tokenn();
    }
}
