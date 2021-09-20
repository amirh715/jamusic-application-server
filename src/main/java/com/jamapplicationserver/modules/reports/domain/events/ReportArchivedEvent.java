/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.domain.events;

import com.jamapplicationserver.core.domain.events.DomainEvent;
import com.jamapplicationserver.modules.reports.domain.Report;

/**
 *
 * @author dada
 */
public class ReportArchivedEvent extends DomainEvent {
    
    public ReportArchivedEvent(
            Report aggregate
    ) {
        super(aggregate);
    }
    
}
