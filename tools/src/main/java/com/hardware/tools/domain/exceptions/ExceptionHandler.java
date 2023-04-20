package com.hardware.tools.domain.exceptions;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;


@Component
public class ExceptionHandler extends DataFetcherExceptionResolverAdapter {

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        logger.error("Error while executing query", ex);
        return GraphqlErrorBuilder.newError()
                .errorType(ErrorType.DataFetchingException)
                .message(ex.getMessage())
                .build();
    }
}
