/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.infra.Persistence.database;

/**
 *
 * @author dada
 */
public class UpdaterOrCreatorDoesNotExistException extends Exception {
    
    public UpdaterOrCreatorDoesNotExistException() {
        super("Updater/Creator user does not exist or is not an admin");
    }
    
}
