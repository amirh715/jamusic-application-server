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
import com.jamapplicationserver.infra.Services.AuthService.AuthService;

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
    
    private ArrayList<String> cacheControlDirectives = new ArrayList<>();
    
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
        
        // access to this controller is restricted
        if(requireAuthClaims) {
            // subject id and role are not null. They must be valid.
            if(subjectIdString != null && subjectRoleString != null) {
                subjectIdOrError = UniqueEntityId.createFromString(subjectIdString);
                subjectRoleOrError = UserRole.create(subjectRoleString);

                if(subjectIdOrError.isFailure || subjectRoleOrError.isFailure) {
                    fail("Subject id or role is invalid.");
                    return res;
                }
                this.subjectId = subjectIdOrError.getValue();
                this.subjectRole = subjectRoleOrError.getValue();
                
                // check if access is allowed to this controller
                final AuthService authService = AuthService.getInstance();
                final AccessControlPolicy acp = new AccessControlPolicy(this.getClass().getSimpleName(), subjectRole.getValue());
                if(!authService.canAccess(acp)) {
                    unauthorized(new AccessDeniedError());
                    return res;
                }

            } else { // subject id and/or role are null.

                forbidden(new AccessCredentialsAreNotProvidedError());
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
    
    protected static Response htmlResponse(Response res, int code, String html) {
        res.body(html);
        res.type("text/html; charset=UTF-8");
        res.status(code);
        return res;
    }

    // SUCCESS RESPONSES (2**)
    protected <T extends IQueryResponseDTO> void ok(T dto) {
        setCacheControlDirectives();
        BaseController.jsonResponse(res, 200, dto.filter(subjectRole));
    }
    
    protected <T extends IQueryResponseDTO> void ok(Set<T> dto) {
        setCacheControlDirectives();
        BaseController.jsonResponse(
                res,
                200,
                dto
                        .stream()
                        .map(item -> item.filter(subjectRole))
                        .collect(Collectors.toSet()));
    }
    
    protected void sendHtml(String html, int code) {
        setCacheControlDirectives();
        BaseController.htmlResponse(res, code, html);
    }
    
    protected void sendFile(InputStream stream) {
        setCacheControlDirectives();
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
    protected final BaseController cache(Duration duration) {
        addCacheControlDirective("max-age=" + duration.toSeconds());
        return this;
    }
    
    /**
     * Response can only be stored in private caches
     * @return BaseController
     */
    protected final BaseController privateCache() {
        addCacheControlDirective("private");
        return this;
    }
    
    /**
     * Response can be store in intermediate caches
     * @return 
     */
    protected final BaseController publicCache() {
        addCacheControlDirective("public");
        return this;
    }
        
    /**
     * Indicates that the response won't be updated while it's fresh
     * @return BaseController
     */
    protected final BaseController immutable() {
        addCacheControlDirective("immutable");
        return this;
    }
    
    /**
     * Indicates that the cache could reuse a stale response while it re-validates
     * it to a cache.
     * @param duration
     * @return BaseController
     */
    protected final BaseController staleWhileRevalidate(Duration duration) {
        addCacheControlDirective("stale-while-revalidate=" + duration.toSeconds());
        return this;
    }
    
    /**
     * Indicates that the cache can reuse a stale response when a origin server
     * responds with an error.
     * @param duration
     * @return BaseController
     */
    protected final BaseController staleIfError(Duration duration) {
        addCacheControlDirective("stale-if-error=" + duration.toSeconds());
        return this;
    }
    
    /**
     * Do not store (cache) anywhere.
     * @return BaseController
     */
    protected final BaseController noStore() {
        addCacheControlDirective("no-store");
        return this;
    }
    
    // VERSIONING
    protected final ETag getEtag() {
        final String etag = req.raw().getHeader("If-None-Match");
        return ETag.reconstitute(etag);
    }
    
    protected final boolean hasETag() {
        return req.raw().getHeader("If-None-Match") != null;
    }
    
    protected final void attachEtag(ETag etag) {
        res.header("ETag", etag.getValue());
    }
    
    private void addCacheControlDirective(String directive) {
        if(cacheControlDirectives.contains(directive)) return;
        cacheControlDirectives.add(directive);
    }
    
    private void setCacheControlDirectives() {
        res.header("Cache-Control", cacheControlDirectives.stream().collect(Collectors.joining(",")));
    }
    
    
}
