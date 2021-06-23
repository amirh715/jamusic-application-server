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
import com.jamapplicationserver.core.domain.IUseCase;
import com.jamapplicationserver.modules.user.infra.services.LoginAuditManager;
import ua_parser.*;

/**
 *
 * @author amirhossein
 */
public class LoginController extends BaseController {
    
    private final IUseCase useCase;
    
    private LoginController(IUseCase useCase) {
        this.useCase = useCase;
    }
    
    @Override
    public void executeImpl() {
        
        System.out.println("LoginController");
        
        final Map<String, String> fields = MultipartFormDataUtil.toMap(this.req.raw());
        
        final Parser userAgentParser = new Parser();
        final Client client = userAgentParser.parse(this.req.userAgent());
        final Device device = client.device;
        final OS os = client.os;
        final String ip = this.req.ip();
        
        final LoginRequestDTO dto =
                new LoginRequestDTO(
                        fields.get("mobile"),
                        fields.get("password"),
                        device,
                        ip,
                        os
                );
        
        try {
            
            final Result result = this.useCase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                
                if(error instanceof NotFoundError)
                    this.notFound(error);
                
                if(error instanceof ConflictError)
                    this.conflict(error);
            
                if(error instanceof ClientErrorError)
                    this.clientError(error);
                
                return;
            }
            
            this.ok(result.getValue());
            
        } catch(Exception e) {
            this.fail(e);
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
