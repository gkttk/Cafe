package com.github.gkttk.epam.model.builder;

/**
 * Implementations of the interface return an mutable object which stores main entity values.
 */
public interface Builder<T> {

    /**
     * Method return immutable main entity with values of current Builder.
     */
    T build();

}
