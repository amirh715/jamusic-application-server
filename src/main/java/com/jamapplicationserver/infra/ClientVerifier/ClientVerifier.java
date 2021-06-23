/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.ClientVerifier;

import spark.Request;
import spark.Response;
import spark.Filter;
import static spark.Spark.*;

/**
 *
 * @author amirhossein
 */
public class ClientVerifier implements Filter {
    
    private ClientVerifier() {
    }
    
    @Override
    public void handle(Request req, Response res) {
        
        final String clientVersion = req.headers("x-client-version");
        
        // client id is not provided
        if(clientVersion == null)
            throw halt(400, "No client id is provided.");
        
        // client id is invalid
        if(!CLIENT_VERSIONS.isClientValid(clientVersion))
            throw halt(400, "Client id is invalid.");
        
    }
    
    public static ClientVerifier getInstance() {
        return ClientVerifierHolder.INSTANCE;
    }
    
    private static class ClientVerifierHolder {

        private static final ClientVerifier INSTANCE = new ClientVerifier();
    }
}
