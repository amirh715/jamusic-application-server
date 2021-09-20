/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.modules.reports.infra.serializers;

import java.lang.reflect.Type;
import com.google.gson.*;
import com.jamapplicationserver.modules.reports.domain.*;

/**
 *
 * @author dada
 */
public class ReportSerializer implements JsonSerializer<Report> {
    
    @Override
    public JsonElement serialize(Report report, Type type, JsonSerializationContext context) {
        
        JsonObject json = new JsonObject();
        
        json.add("id", new JsonPrimitive(report.id.toString()));
        json.add("message", new JsonPrimitive(report.getMessage().getValue()));
        json.add("status", new JsonPrimitive(report.getStatus().getValueInPersian()));
        json.add("createdAt", new JsonPrimitive(report.getCreatedAt().toJalali()));
        json.add("lastModifiedAt", new JsonPrimitive(report.getLastModifiedAt().toJalali()));
        json.add("createdAtDuration", new JsonPrimitive(report.getCreatedAt().diffFromNow()));
        json.add("lastModifiedAtDuration", new JsonPrimitive(report.getLastModifiedAt().diffFromNow()));
//        json.add("assignedAt", new JsonPrimitive(report.getAssignedAt().toJalali()));
//        json.add("processedAt", new JsonPrimitive(report.getProcessedAt().toJalali()));
//        json.add("archivedAt", new JsonPrimitive(report.getArchivedAt().toJalali()));
        
        return json;
    }
    
}
