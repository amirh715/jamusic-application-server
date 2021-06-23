/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain.events;

/**
 *
 * @author amirhossein
 */
public interface IHandle<T> {
    void setupSubscriptions();
    void handle(DomainEvent event);
}
