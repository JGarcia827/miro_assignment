package com.miro.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents the exception thrown when the requested page size (when
 * using filtered paging) exceeds the maximum allowed.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PagingSizeExceededException extends RuntimeException {

    /**
     * Generated serial ID
     */
    private static final long serialVersionUID = 6893498381591881520L;

    /**
     * Generates the exception
     * 
     * @param maximumPagingSize The maximum page size allowed
     * @param requestedSize     The requested size
     */
    public PagingSizeExceededException(final int maximumPagingSize, final int requestedSize) {
        super("The requested paging size of " + requestedSize + "exceeds the maximum allowed of " + maximumPagingSize
                + ".");
    }
}