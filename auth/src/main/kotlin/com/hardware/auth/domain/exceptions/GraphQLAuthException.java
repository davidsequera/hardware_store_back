package com.hardware.auth.domain.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

/**
 * A custom exception for GraphQL authentication errors.
 */
public class GraphQLAuthException extends RuntimeException implements GraphQLError {

    /**
     * Constructs a new instance of the exception with the specified error message.
     *
     * @param message the error message
     */
    public GraphQLAuthException(String message) {
        super(message);
    }

    /**
     * Returns the source locations associated with this exception.
     *
     * @return the source locations
     */
    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    /**
     * Returns the error type classification associated with this exception.
     *
     * @return the error type classification
     */
    @Override
    public ErrorClassification getErrorType() {
        return null;
    }
}
