/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.domain.events;

import com.jamapplicationserver.core.domain.events.*;
import com.jamapplicationserver.core.domain.*;

/**
 *
 * @author dada
 */
public class ReportArchived extends DomainEvent {
    
    public ReportArchived(
            UniqueEntityId aggregateId
    ) {
        super(aggregateId);
    }
    
}
