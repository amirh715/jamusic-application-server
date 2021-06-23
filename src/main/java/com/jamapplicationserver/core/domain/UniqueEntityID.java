/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

import java.util.*;
import com.jamapplicationserver.core.logic.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author amirhossein
 */
public class UniqueEntityID extends Identifier<UUID> {
    
    public UniqueEntityID(UUID value) {
        super(value != null ? value : UUID.randomUUID());
    }
    
    public UniqueEntityID() {
        super(UUID.randomUUID());
    }
    
    public static Result<UniqueEntityID> createFromString(String uuid) {
        try {
            if(uuid == null) return Result.fail(new ValidationError("Entity id is required."));
            final UUID id = UUID.fromString(uuid);
            return Result.ok(new UniqueEntityID(id));
        } catch(Exception e) {
            return Result.fail(new ValidationError("Entity id is invalid."));
        }
    }
    
    public static Result<UniqueEntityID> createFromUUID(UUID uuid) {
        try {
            if(uuid == null) return Result.fail(new ValidationError("Entity id is required."));
            return Result.ok(new UniqueEntityID(uuid));
        } catch(Exception e) {
            return Result.fail(new ValidationError("Entity id is invalid."));
        }
    }
    
    public static Result<Set<UniqueEntityID>> createFromUUIDs(Set<UUID> uuids) {
        return Result.combine(
                uuids
                .stream()
                .map(uuid -> UniqueEntityID.createFromUUID(uuid))
                .collect(Collectors.toSet())
        );
    }
    
    public static Result<List<UniqueEntityID>> createFromUUIDs(List<UUID> uuids) {
        return Result.combine(
                uuids
                .stream()
                .map(uuid -> UniqueEntityID.createFromUUID(uuid))
                .collect(Collectors.toList())
        );
    }
    
    public static Result<Set<UniqueEntityID>> createFromUUIDs(UUID[] uuids) {
        return Result.combine(
                Stream.of(uuids)
                .map(uuid -> UniqueEntityID.createFromUUID(uuid))
                .collect(Collectors.toSet())
        );
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    @Override
    public boolean equals(Object id) {
        if(id == this)
            return true;
        if(!(id instanceof UniqueEntityID))
            return false;
        UniqueEntityID i = (UniqueEntityID) id;
        return i.value.equals(id);
    }
    
    @Override
    public String toString() {
        return this.value.toString();
    }
    
}
