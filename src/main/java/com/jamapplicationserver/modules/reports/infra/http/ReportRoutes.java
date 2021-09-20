/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.infra.http;

import static spark.Spark.*;
import spark.RouteGroup;
import com.jamapplicationserver.modules.reports.usecases.CreateReport.CreateReportController;
import com.jamapplicationserver.modules.reports.usecases.GetReportById.GetReportByIdController;
import com.jamapplicationserver.modules.reports.usecases.GetReportsByFilters.GetReportsByFiltersController;
import com.jamapplicationserver.modules.reports.usecases.ProcessReport.ProcessReportController;

/**
 *
 * @author dada
 */
public class ReportRoutes implements RouteGroup {
    
    private ReportRoutes() {
    }
    
    @Override
    public void addRoutes() {
        
        before("/*", (req, res) -> System.out.println(req.url()));
        
        // create new report
        post(
                ReportPaths.CREATE_REPORT,
                CreateReportController.getInstance()
        );
        
        // get reports by filters
        get(
                ReportPaths.GET_REPORTS_BY_FILTERS,
                GetReportsByFiltersController.getInstance()
        );
        
        // get report by id
        get(
                ReportPaths.GET_REPORT_BY_ID,
                GetReportByIdController.getInstance()
        );
        
        // process report
        put(
                ReportPaths.PROCESS_REPORT,
                ProcessReportController.getInstance()
        );
        
    }
    
    public static ReportRoutes getInstance() {
        return ReportRoutesHolder.INSTANCE;
    }
    
    private static class ReportRoutesHolder {

        private static final ReportRoutes INSTANCE = new ReportRoutes();
    }
}
