/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.infra;

import com.jamapplicationserver.core.domain.*;
import org.hibernate.exception.ConstraintViolationException;
import com.jamapplicationserver.modules.user.repository.exceptions.*;
import javax.persistence.EntityNotFoundException;



/**
 *
 * @author amirhossein
 * @param <T>
 */
public interface IRepository<T> {
    boolean exists(UniqueEntityId id);
    void save(T t)
            throws
            Exception;
            
    T fetchById(UniqueEntityId id);
    void remove(T aggregate) throws Exception;
}
