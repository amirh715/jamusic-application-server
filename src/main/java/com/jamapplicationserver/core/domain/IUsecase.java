/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

import com.jamapplicationserver.core.logic.*;

/**
 *
 * @author amirhossein
 * @param <IRequest>
 * @param <IResponse>
 */
public interface IUsecase<IRequest, IResponse> {
    Result<IResponse> execute(IRequest request) throws GenericAppException;
}
