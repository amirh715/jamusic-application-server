/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.logic;

import java.util.Optional;

/**
 *
 * @author amirhossein
 * @param <L>
 * @param <R>
 */
public final class Either<L, R> {
    
    public static <L, R> Either<L, R> left(L value) {
        return new Either<>(Optional.of(value), Optional.empty());
    }
    
    public static <L, R> Either<L, R> right(R value) {
        return new Either<>(Optional.empty(), Optional.of(value));
    }
    
    private final Optional<L> left;
    private final Optional<R> right;
    
    private Either(Optional<L> l, Optional<R> r) {
        left = l;
        right = r;
    }
    
    public final boolean isLeft() {
        return left.isPresent();
    }
    
    public final boolean isRight() {
        return right.isPresent();
    }
    
    public final L getLeft() throws Exception {
        if(isRight()) throw new Exception("");
        return left.get();
    }
    
    public final R getRight() throws Exception {
        if(isLeft()) throw new Exception("");
        return right.get();
    }
    
}
