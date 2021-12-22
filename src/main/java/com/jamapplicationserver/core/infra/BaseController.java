/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.infra;

import spark.*;
import java.io.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.TikaUtils;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author amirhossein
 */
public abstract class BaseController implements Route {
    
    protected Request req;
    private Response res;
    protected boolean requireAuthClaims = true;
    
    protected UniqueEntityId subjectId;
    protected UserRole subjectRole;
    
    protected abstract void executeImpl();
    
    protected static final ISerializer serializer = Serializer.getInstance();
    
    @Override
    public final Object handle(Request req, Response res) {
        
        this.req = req;
        this.res = res;
        
        final String subjectIdString = req.session().attribute("subjectId");
        final String subjectRoleString = req.session().attribute("subjectRole");
        Result<UniqueEntityId> subjectIdOrError;
        Result<UserRole> subjectRoleOrError;
        
        // subject id and role are not null. They must be valid.
        if(subjectIdString != null && subjectRoleString != null) {
            subjectIdOrError = UniqueEntityId.createFromString(subjectIdString);
            subjectRoleOrError = UserRole.create(subjectRoleString);
            
            if(subjectIdOrError.isFailure || subjectRoleOrError.isFailure) {
                fail("Subject id or role is invalid.");
                System.out.println("Subject vars invalid");
                return res;
            }
            this.subjectId = subjectIdOrError.getValue();
            this.subjectRole = subjectRoleOrError.getValue();

        } else { // subject id and/or role are null.
            
            if(requireAuthClaims) {
                fail("Subject id and role are required.");
                System.out.println("Subject vars required");
                return res;
            }
            
        }
        
        executeImpl();
        
        return res.body();
        
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
    protected <T extends IQueryResponseDTO> void ok(T dto) {
        BaseController.jsonResponse(res, 200, dto.filter(subjectRole));
    }
    
    protected <T extends IQueryResponseDTO> void ok(Set<T> dto) {
        BaseController.jsonResponse(
                res,
                200,
                dto
                        .stream()
                        .map(item -> item.filter(subjectRole))
                        .collect(Collectors.toSet()));
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
        BaseController.jsonResponse(res, 304, null);
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
        BaseController.jsonResponse(res, 500, exception != null ? exception.getMessage() : "Internal Server Error (500)");
    }
    
    protected final void fail(String error) {
        BaseController.jsonResponse(res, 500, error != null ? error : "Internal Server Error (500)");
    }
    
    protected final void fail() {
        BaseController.jsonResponse(res, 500, "Internal Server Error (500)");
    }
    
    // CACHE CONTROL
    protected final void cache(Duration duration) {
        res.header("Cache-Control", "max-age=" + duration.toSeconds());
    }
    
    protected final ETag getEtag() {
        final String etag = req.raw().getHeader("ETag");
        return ETag.reconstitute(etag);
    }
    
    protected final boolean hasETag() {
        return req.raw().getHeader("ETag") != null;
    }
    
    protected final void attachEtag(ETag etag) {
        res.header("ETag", etag.getValue());
    }
    
    
}
