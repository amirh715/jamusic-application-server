/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.usecases.GetGenreByTitle;

import com.jamapplicationserver.core.infra.BaseController;

/**
 *
 * @author dada
 */
public class GetGenreByTitleController extends BaseController {
    
    private GetGenreByTitleController() {
    }
    
    @Override
    public void executeImpl() {
        
    }
    
    public static GetGenreByTitleController getInstance() {
        return GetGenreByTitleControllerHolder.INSTANCE;
    }
    
    private static class GetGenreByTitleControllerHolder {

        private static final GetGenreByTitleController INSTANCE = new GetGenreByTitleController();
    }
}
