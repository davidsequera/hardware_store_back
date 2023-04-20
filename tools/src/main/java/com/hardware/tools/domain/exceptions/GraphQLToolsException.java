package com.hardware.tools.domain.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;


/**
 * An exception that is thrown when there is an error related to the tools service in GraphQL queries or mutations.
 */
public class GraphQLToolsException extends RuntimeException implements GraphQLError {

    /**
     * Constructs a new GraphQLToolsException with the specified error message.
     *
     * @param message The error message.
     */
    public GraphQLToolsException(String message) {
        super(message);
    }

    /**
     * Gets the locations of the GraphQL query or mutation where the error occurred.
     *
     * @return The locations of the GraphQL query or mutation where the error occurred.
     */
    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    /**
     * Gets the classification of the error in GraphQL.
     *
     * @return The classification of the error in GraphQL.
     */
    @Override
    public ErrorClassification getErrorType() {
        return null;
    }
}

