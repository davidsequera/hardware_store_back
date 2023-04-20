package com.hardware.auth.domain.exceptions

import graphql.ErrorType
import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.stereotype.Component

/**
 * Handles GraphQL exceptions and maps them to [GraphQLError]s.
 */
@Component
class GraphQLExceptionHandler : DataFetcherExceptionResolverAdapter() {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * Resolves the given [Throwable] to a [GraphQLError].
     *
     * If the [Throwable] is a [GraphQLAuthException], it is converted to a [GraphQLError] with the exception message
     * as the error message and [ErrorType.DataFetchingException] as the error type. Otherwise, it delegates to the
     * super class for handling the exception.
     *
     * @param e the [Throwable] to be resolved
     * @param env the [DataFetchingEnvironment] in which the exception was thrown
     * @return the [GraphQLError] for the given [Throwable]
     */
    override fun resolveToSingleError(e: Throwable, env: DataFetchingEnvironment): GraphQLError? {
        return when (e) {
            is GraphQLAuthException -> toGraphQLError(e)
            else -> super.resolveToSingleError(e, env)
        }
    }

    /**
     * Converts the given [GraphQLAuthException] to a [GraphQLError].
     *
     * @param e the [GraphQLAuthException] to be converted
     * @return the [GraphQLError] for the given [GraphQLAuthException]
     */
    private fun toGraphQLError(e: GraphQLAuthException): GraphQLError {
        log.warn("Exception while handling request: ${e.message}", e)
        return GraphqlErrorBuilder.newError()
            .message(e.message)
            .errorType(ErrorType.DataFetchingException)
            .build()
    }
}
