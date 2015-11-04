package com.davidtschida.purduemenu.exceptions;

/**
 * Created by david on 11/3/2015.
 */
public class MealDoesNotExistException extends RuntimeException {
    public MealDoesNotExistException(String message) {
        super(message);
    }
}
