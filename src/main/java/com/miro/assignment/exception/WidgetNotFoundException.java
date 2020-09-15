package com.miro.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents the exception thrown when a Widget cannot be found
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WidgetNotFoundException extends RuntimeException {

    /**
     * Generated serial ID
     */
    private static final long serialVersionUID = 456649205295028254L;

    /**
     * Generates the exception
     * 
     * @param id The requested Widget id
     */
    public WidgetNotFoundException(final long id) {
        super("Widget with id `" + id + "` cannot be found.");
    }
}
