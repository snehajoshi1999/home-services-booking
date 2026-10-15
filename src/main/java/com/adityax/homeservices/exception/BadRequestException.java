package com.adityax.homeservices.exception;

/**
 * Thrown for invalid input that Bean Validation cannot check on its own,
 * e.g. a serviceId that does not exist in the database.
 */
public class BadRequestException extends RuntimeException {

    private final String field;

    public BadRequestException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
