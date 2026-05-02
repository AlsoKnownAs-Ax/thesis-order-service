package com.alex.thesis.orderService.grpc;

import com.alex.thesis.orderService.exception.InsufficientStockException;
import com.alex.thesis.orderService.exception.OrderNotFoundException;
import com.alex.thesis.orderService.exception.ProductNotFoundException;
import com.alex.thesis.orderService.exception.UserNotActiveException;
import com.alex.thesis.orderService.exception.UserNotFoundException;

import io.grpc.Status;

public final class GrpcExceptionMapper {

    private GrpcExceptionMapper() {
    }

    public static RuntimeException toGrpcException(Exception exception) {
        if (exception instanceof io.grpc.StatusRuntimeException runtimeException) {
            return runtimeException;
        }

        if (exception instanceof UserNotFoundException
            || exception instanceof OrderNotFoundException
            || exception instanceof ProductNotFoundException) {
            return Status.NOT_FOUND
                .withDescription(exception.getMessage())
                .withCause(exception)
                .asRuntimeException();
        }

        if (exception instanceof UserNotActiveException || exception instanceof InsufficientStockException) {
            return Status.FAILED_PRECONDITION
                .withDescription(exception.getMessage())
                .withCause(exception)
                .asRuntimeException();
        }

        if (exception instanceof IllegalArgumentException) {
            return Status.INVALID_ARGUMENT
                .withDescription(exception.getMessage())
                .withCause(exception)
                .asRuntimeException();
        }

        return Status.INTERNAL
            .withDescription("Unexpected server error")
            .withCause(exception)
            .asRuntimeException();
    }
}