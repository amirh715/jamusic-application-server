/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.logic;

import java.util.*;

/**
 *
 * @author amirhossein
 */
public class Result<T> {
    
    public boolean isSuccess;
    public boolean isFailure;
    private T value;
    public BusinessError error;
    
    protected Result(boolean isSuccess, BusinessError error, T value) throws Exception {
        
        if(isSuccess == true && error != null)
            throw new Exception("InvalidOperation: A result cannot be successful and contain an error");
        if(isSuccess == false && error == null)
            throw new Exception("InvalidOperation: A failing result needs to contain an error message");
            
        this.isSuccess = isSuccess;
        this.isFailure = !isSuccess;
        this.error = error;
        this.value = value;
        
    }
    
    public T getValue() throws IllegalStateException {
        if(this.isFailure)
            throw new IllegalStateException("Can't get the value of an error result. Use 'getError' instead");
        return this.value;
    }
    
    public BusinessError getError() {
        return this.error;
    }
    
    public static <T>Result ok(T value) {
        Result result = null;
        try {
            result = new Result(true, null, value);
        } catch(Exception e) {
            System.out.print(e);
        }
        return result;
    }
    
    public static <T>Result ok() {
        Result result = null;
        try {
            result = new Result(true, null, null);
        } catch(Exception e) {
            System.out.print(e);
        }
        return result;
    }
    
    public static Result fail(BusinessError value) {
        Result result = null;
        try {
            result = new Result(false, value, null);
        } catch(Exception e) {
            System.out.print(e);
        }
        return result;
    }
    
    public static Result combine(Collection<Result> results) {
        for (Result result : results)
            if(result.isFailure) return result;
        return Result.ok(results);
    }
    
    public static Result combine(List<Result> results) {
        for(Result result : results)
            if(result.isFailure) return result;
        return Result.ok(results);
    }
    
    public static Result combine(Result[] results) {
        for (Result result : results)
            if(result.isFailure) return result;
        return Result.ok(results);
    }
    
}
