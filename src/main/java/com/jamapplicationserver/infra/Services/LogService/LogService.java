/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Services.LogService;

import org.apache.logging.log4j.*;

/**
 *
 * @author dada
 */
public class LogService {
    
    private final Logger logger;
    
    private LogService(Logger logger) {
        this.logger = logger;
    }
    
    public void log(String message) {
        logger.log(Level.INFO, message);
    }
    
    public void warn(String message) {
        logger.log(Level.WARN, message);
    }
    
    public void warn(Exception e) {
        logger.log(Level.WARN, e.getMessage(), e);
    }
    
    public void error(String message) {
        logger.log(Level.ERROR, message);
    }
    
    public void error(Exception e) {
        logger.log(Level.ERROR, e.getMessage(), e);
    }
    
    public void fatal(Exception e) {
        logger.log(Level.FATAL, e.getMessage(), e);
    }
    
    public void fatal(String message) {
        logger.log(Level.FATAL, message);
    }
    
    public static LogService getInstance() {
        return LogManagerHolder.INSTANCE;
    }
    
    private static class LogManagerHolder {

        private static final LogService INSTANCE =
                new LogService(LogManager.getLogger(LogService.class));
    }
}
