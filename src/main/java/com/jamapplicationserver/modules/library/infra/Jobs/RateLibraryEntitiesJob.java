/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.library.infra.Jobs;

import org.quartz.*;

/**
 *
 * @author dada
 */
public class RateLibraryEntitiesJob implements Job {
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
        System.out.println("JOB : " + this.getClass().getSimpleName());
        
    }
    
}
