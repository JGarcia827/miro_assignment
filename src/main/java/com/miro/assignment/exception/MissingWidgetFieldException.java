package com.miro.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents the exception thrown when a Widget creation request is
 * missing a required field.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MissingWidgetFieldException extends RuntimeException {

    /**
     * Generated serial ID
     */
    private static final long serialVersionUID = 456649205295028254L;

    /**
     * Generates the exception
     * 
     * @param missingFieldName The name of the required field missing
     */
    public MissingWidgetFieldException(final String missingFieldName) {
        super("Missing required field `" + missingFieldName + "`.");
    }
}