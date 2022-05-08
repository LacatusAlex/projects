package org.example.logic.validator;

/**
 * Generic interface used for implementing the validators for certain fields in our tables
 * @param <T>
 */
public interface Validator<T> {
     void validate(T t);
}
