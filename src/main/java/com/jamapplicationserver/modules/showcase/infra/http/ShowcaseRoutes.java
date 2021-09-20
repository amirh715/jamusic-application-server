/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.infra.http;

import spark.RouteGroup;
import static spark.Spark.*;
import com.jamapplicationserver.modules.showcase.usecases.CreateShowcase.CreateShowcaseController;
import com.jamapplicationserver.modules.showcase.usecases.GetAllShowcases.GetAllShowcasesController;
import com.jamapplicationserver.modules.showcase.usecases.GetShowcaseById.GetShowcaseByIdController;
import com.jamapplicationserver.modules.showcase.usecases.RemoveShowcase.RemoveShowcaseController;
import com.jamapplicationserver.modules.showcase.usecases.ShowcaseInteractedWith.ShowcaseInteractedWithController;

/**
 *
 * @author dada
 */
public class ShowcaseRoutes implements RouteGroup {
    
    private ShowcaseRoutes() {
    }
    
    @Override
    public void addRoutes() {
        
        post(
                ShowcasePaths.CREATE_SHOWASE,
                CreateShowcaseController.getInstance()
        );
        
        get(
                ShowcasePaths.GET_ALL_SHOWCASES,
                GetAllShowcasesController.getInstance()
        );
        
        get(
                ShowcasePaths.GET_SHOWCASE_BY_ID,
                GetShowcaseByIdController.getInstance()
        );
        
        // get image by id
        
        delete(
                ShowcasePaths.REMOVE_SHOWCASE,
                RemoveShowcaseController.getInstance()
        );
        
        put(
                ShowcasePaths.SHOWCASE_INTERACTED_WITH,
                ShowcaseInteractedWithController.getInstance()
        );
        
    }
    
    public static ShowcaseRoutes getInstance() {
        return ShowcaseRoutesHolder.INSTANCE;
    }
    
    private static class ShowcaseRoutesHolder {

        private static final ShowcaseRoutes INSTANCE = new ShowcaseRoutes();
    }
    
}
