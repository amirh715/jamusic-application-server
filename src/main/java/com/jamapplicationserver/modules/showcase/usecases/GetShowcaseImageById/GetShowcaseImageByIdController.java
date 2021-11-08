/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.usecases.GetShowcaseImageById;

import com.jamapplicationserver.core.infra.*;
import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author dada
 */
public class GetShowcaseImageByIdController extends BaseController {
    
    private GetShowcaseImageByIdController() {
    }
    
    @Override
    public void executeImpl() {
        
        try {

            
        } catch(Exception e) {
            fail(e);
        }
        
    }
    
    public static GetShowcaseImageByIdController getInstance() {
        return GetShowcaseImageByIdHolder.INSTANCE;
    }
    
    private static class GetShowcaseImageByIdHolder {

        private static final GetShowcaseImageByIdController INSTANCE = new GetShowcaseImageByIdController();
    }
}
