/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.infra;

/**
 *
 * @author amirhossein
 */
public interface Mapper<T, U> {
    
    T toDomain(U u);
    
    U toPersistence(T t);
    
    U mergeForPersistence(T t, U u);
    
}
