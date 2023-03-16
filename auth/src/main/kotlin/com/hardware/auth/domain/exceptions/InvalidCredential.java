package com.hardware.auth.domain.exceptions;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;


public class InvalidCredential extends RuntimeException implements GraphQLError {

    public  InvalidCredential(String message) {
       super(message);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }
}
