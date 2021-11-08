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
public class UniqueEntityId extends Identifier<UUID> {
    
    public UniqueEntityId(UUID value) {
        super(value != null ? value : UUID.randomUUID());
    }
    
    public UniqueEntityId() {
        super(UUID.randomUUID());
    }
    
    public static Result<UniqueEntityId> createFromString(String uuid) {
        try {
            if(uuid == null) return Result.fail(new ValidationError("Entity id is required."));
            final UUID id = UUID.fromString(uuid);
            return Result.ok(new UniqueEntityId(id));
        } catch(Exception e) {
            return Result.fail(new ValidationError("Entity id is invalid."));
        }
    }
    
    public static Result<Set<UniqueEntityId>> createFromStrings(Set<String> uuids) {
        if(uuids == null || uuids.isEmpty()) return Result.fail(new ValidationError("Entity ids are required"));
        final Result<Set<Result<UniqueEntityId>>> result =
                Result.combine(uuids
                .stream()
                .map(uuid -> UniqueEntityId.createFromString(uuid))
                .collect(Collectors.toSet())
        );
        if(result.isFailure) return Result.fail(result.getError());
        return Result.ok(
                result.getValue()
                .stream()
                .map(el -> el.getValue())
                .collect(Collectors.toSet())
        );
    }
    
    public static Result<UniqueEntityId> createFromUUID(UUID uuid) {
        if(uuid == null) return Result.fail(new ValidationError("Entity id is required"));
        return Result.ok(new UniqueEntityId(uuid));
    }
    
    public static Result<Set<UniqueEntityId>> createFromUUIDs(Set<UUID> uuids) {
        if(uuids == null || uuids.isEmpty()) return Result.fail(new ValidationError("Entity ids are required"));
        final Result<Set<Result<UniqueEntityId>>> result =
                Result.combine(uuids
                .stream()
                .map(uuid -> UniqueEntityId.createFromUUID(uuid))
                .collect(Collectors.toSet())
        );
        if(result.isFailure) return Result.fail(result.getError());
        return Result.ok(
                result.getValue()
                .stream()
                .map(el -> el.getValue())
                .collect(Collectors.toSet())
        );
    }
    
    public static Result<List<UniqueEntityId>> createFromUUIDs(List<UUID> uuids) {
        return Result.combine(uuids
                .stream()
                .map(uuid -> UniqueEntityId.createFromUUID(uuid))
                .collect(Collectors.toList())
        );
    }
    
    public static Result<Set<UniqueEntityId>> createFromUUIDs(UUID[] uuids) {
        return Result.combine(Stream.of(uuids)
                .map(uuid -> UniqueEntityId.createFromUUID(uuid))
                .collect(Collectors.toSet())
        );
    }
    
    @Override
    public boolean equals(Object id) {
        if(id == this)
            return true;
        if(!(id instanceof UniqueEntityId))
            return false;
        UniqueEntityId i = (UniqueEntityId) id;
        return this.value.equals(i.toValue());
    }
    
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
    
    @Override
    public String toString() {
        return this.value.toString();
    }
    
}
