/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.Login;

import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.utils.MultipartFormDataUtil;
import java.util.*;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.modules.user.infra.services.LoginAuditManager;
import com.jamapplicationserver.modules.user.infra.DTOs.queries.AuthToken;
import ua_parser.*;

/**
 *
 * @author amirhossein
 */
public class LoginController extends BaseController {
    
    private final IUsecase useCase;
    
    private LoginController(IUsecase useCase) {
        this.useCase = useCase;
        this.requireAuthClaims = false;
    }
    
    @Override
    public void executeImpl() {
        
        System.out.println("LoginController");
        
        final Map<String, String> fields = MultipartFormDataUtil.toMap(req.raw());
        
        final Parser userAgentParser = new Parser();
        final Client client = userAgentParser.parse(req.userAgent());
        final Device device = client.device;
        final OS os = client.os;
        final String ip = req.ip();
        
        final LoginRequestDTO dto =
                new LoginRequestDTO(
                        fields.get("mobile"),
                        fields.get("password"),
                        fields.get("FCMToken"),
                        device,
                        ip,
                        os
                );
        
        try {
            
            final Result<AuthToken> result = useCase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    notFound(error);
                
                if(error instanceof ConflictError)
                    conflict(error);
            
                if(error instanceof ClientErrorError)
                    clientError(error);
                
                return;
            }
            
            ok(result.getValue());
            
        } catch(Exception e) {
            e.printStackTrace();
            fail(e);
        }
        
    }
    
    public static LoginController getInstance() {
        return LoginControllerHolder.INSTANCE;
    }
    
    private static class LoginControllerHolder {

        private static final LoginController INSTANCE =
                new LoginController(LoginUseCase.getInstance());
    }
}
