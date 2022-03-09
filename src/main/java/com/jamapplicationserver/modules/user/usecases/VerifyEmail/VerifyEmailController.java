/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.user.usecases.VerifyEmail;

import com.jamapplicationserver.core.infra.BaseController;
import com.jamapplicationserver.core.domain.IUsecase;
import com.jamapplicationserver.core.logic.*;
import com.jamapplicationserver.infra.Services.LogService.LogService;

/**
 *
 * @author amirhossein
 */
public class VerifyEmailController extends BaseController {
    
    private final IUsecase useCase;
    
    private VerifyEmailController(IUsecase useCase) {
        this.useCase = useCase;
        this.requireAuthClaims = false;
    }
    
    @Override
    public void executeImpl() {
                
        try {
            
            final VerifyEmailRequestDTO dto = new VerifyEmailRequestDTO(req.queryParams("token"));
            
            final Result result = useCase.execute(dto);
            
            if(result.isFailure) {
                
                final BusinessError error = result.getError();
                final String html = "<h1 style=\"text-align: center;\"><span style=\"direction: rtl\">" + error.message + "</span></h1>";
                
                if(error instanceof ConflictError)
                    sendHtml(html, 409);
                
                if(error instanceof NotFoundError)
                    sendHtml(html, 404);
                
                if(error instanceof ClientErrorError)
                    sendHtml(html, 400);
                
                return;
            }
            
            sendHtml("<h1 style=\"text-align: center;\"><span style=\"direction: rtl\">ایمیل شما تایید شد.</span></h1>", 200);
                    
        } catch(Exception e) {
            LogService.getInstance().error(e);
            fail(e);
        }
        
    }
    
    public static VerifyEmailController getInstance() {
        return VerifyEmailControllerHolder.INSTANCE;
    }
    
    private static class VerifyEmailControllerHolder {

        private static final VerifyEmailController INSTANCE =
                new VerifyEmailController(VerifyEmailUseCase.getInstance());
    }
}
