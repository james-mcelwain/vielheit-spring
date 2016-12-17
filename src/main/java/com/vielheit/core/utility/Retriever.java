package com.vielheit.core.utility;

import javax.persistence.NoResultException;

@FunctionalInterface
public interface Retriever<T> {
    T retrieve() throws NoResultException;
}
