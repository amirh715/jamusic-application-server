/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.infra.DTOs.queries;

/**
 *
 * @author dada
 */
public class ReportedLibraryEntityIdAndTitle {
    
    public String id;
    public String title;
    
    public ReportedLibraryEntityIdAndTitle(String id, String title) {
        this.id = id;
        this.title = title;
    }
    
    public String getId() {
        return this.id;
    }
    
}
