/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.infra;

import spark.Request;
import spark.Response;
import spark.Route;
import java.io.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.TikaUtils;

/**
 *
 * @author amirhossein
 */
public abstract class BaseController implements Route {
    
    protected Request req;
    private Response res;
    
    protected String subjectId;
    protected String subjectRole;
    
    protected abstract void executeImpl();
    
    protected static final ISerializer serializer = Serializer.getInstance();
    
    @Override
    public final Object handle(Request req, Response res) {
        
        this.req = req;
        this.res = res;
        this.subjectId = this.req.session().attribute("subjectId");
        this.subjectRole = this.req.session().attribute("role");
        
        this.executeImpl();
        
        return this.res.body();
        
    }
    
    protected static <T> Response jsonResponse(Response res, int code, T dto) {
        String stringified = serializer.serialize(dto);
        res.header("Content-Type", "application/json");
        res.status(code);
        res.body(stringified);
        return res;
    }
    
    protected static Response fileResponse(Response res, InputStream in) {

        try {
            
            final TikaUtils tika = TikaUtils.getInstance();

            final String contentType = tika.getContentType(in);
            res.header("Content-Type", contentType);
            
            final OutputStream out = res.raw().getOutputStream();
            in.transferTo(out);
            
            return res;
            
        } catch(IOException e) {
            e.printStackTrace();
            res.status(500);
            res.body(e.getMessage());
            return res;
        }
        
    }

    // SUCCESS RESPONSES (2**)
    protected <T> void ok(T dto) {
        BaseController.jsonResponse(res, 200, dto);
    }
    
    protected void sendFile(InputStream stream) {
        BaseController.fileResponse(res, stream);
    }
    
    protected final void created() {
        res.status(201);
        res.body("");
    }
    
    public final <T> void created(T dto) {
        BaseController.jsonResponse(res, 201, dto);
    }
        
    /**
    * A success response with no content to return to client.
    */
    protected final void noContent() {
        BaseController.jsonResponse(res, 204, null);
    }
    
    // REDIRECT RESPONSES (3**)
    protected final void notModified() {
        BaseController.jsonResponse(res, 301, null);
    }
    
    // FAILURE RESPONSES (4**)
    protected final void notFound(BusinessError error) {
        BaseController.jsonResponse(res, 404, error != null ? error : "Not found (404)");
    }

    protected final void notFound(String message) {
        BaseController.jsonResponse(res, 404, message != null ? message : "Not found (404)");
    }
    
    protected final void clientError(BusinessError error) {
        BaseController.jsonResponse(res, 400, error != null ? error : "Bad request (400)");
    }
    
    protected final void clientError(String message) {
        BaseController.jsonResponse(res, 400, message != null ? message : "Bad request (400)");
    }
    
    protected final void unauthorized(BusinessError error) {
        BaseController.jsonResponse(res, 401, error != null ? error : "Unauthorized (401)");
    }
    
    protected final void unauthorized(String message) {
        BaseController.jsonResponse(res, 401, message != null ? message : "Unauthorized (401)");
    }
    
    protected final void forbidden(BusinessError error) {
        BaseController.jsonResponse(res, 403, error != null ? error : "Forbidden (403)");
    }
    
    protected final void forbidden(String message) {
        BaseController.jsonResponse(res, 403, message != null ? message : "Forbidden (403)");
    }
    
    protected final void conflict(BusinessError error) {
        BaseController.jsonResponse(res, 409, error != null ? error : "Conflict (409)");
    }
    
    protected final void conflict(String message) {
        BaseController.jsonResponse(res, 409, message != null ? message : "Conflict (409)");
    }
    
    // INTERNAL ERRORS (5**)
    protected final void fail(Exception exception) {
        res.status(500);
        res.body(exception != null ? exception.getMessage() : "Internal Server Error (500)");
    }
    
    protected final void fail(String error) {
        res.status(500);
        res.body(error != null ? error : "Internal Server Error (500)");
    }
    
}
