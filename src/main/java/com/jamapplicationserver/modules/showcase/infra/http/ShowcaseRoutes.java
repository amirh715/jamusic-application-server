/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.showcase.infra.http;

import static spark.Spark.*;
import spark.RouteGroup;
import com.jamapplicationserver.modules.showcase.usecases.CreateShowcase.CreateShowcaseController;
import com.jamapplicationserver.modules.showcase.usecases.GetAllShowcases.GetAllShowcasesController;
import com.jamapplicationserver.modules.showcase.usecases.GetShowcaseImageById.GetShowcaseImageByIdController;
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
        
        // create new showcase
        post(
                ShowcasePaths.CREATE_SHOWASE,
                CreateShowcaseController.getInstance()
        );
        
        // get all showcases
        get(
                ShowcasePaths.GET_ALL_SHOWCASES,
                GetAllShowcasesController.getInstance()
        );
        
        // get image by id
        get(
                ShowcasePaths.GET_SHOWCASE_IMAGE_BY_ID,
                GetShowcaseImageByIdController.getInstance()
        );
        
        // remove a showcase
        delete(
                ShowcasePaths.REMOVE_SHOWCASE,
                RemoveShowcaseController.getInstance()
        );
        
        // showcase is interacted with
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
