package com.alex.thesis.orderService.grpc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.alex.thesis.orderService.exception.InsufficientStockException;
import com.alex.thesis.orderService.exception.OrderNotFoundException;
import com.alex.thesis.orderService.exception.ProductNotFoundException;
import com.alex.thesis.orderService.exception.UserNotActiveException;
import com.alex.thesis.orderService.exception.UserNotFoundException;

import io.grpc.Status;

class GrpcExceptionMapperTest {

    @Test
    void mapsNotFoundExceptionsToNotFound() {
        assertEquals(Status.Code.NOT_FOUND, Status.fromThrowable(GrpcExceptionMapper.toGrpcException(new UserNotFoundException("user"))).getCode());
        assertEquals(Status.Code.NOT_FOUND, Status.fromThrowable(GrpcExceptionMapper.toGrpcException(new OrderNotFoundException("order"))).getCode());
        assertEquals(Status.Code.NOT_FOUND, Status.fromThrowable(GrpcExceptionMapper.toGrpcException(new ProductNotFoundException("product"))).getCode());
    }

    @Test
    void mapsPreconditionExceptionsToFailedPrecondition() {
        assertEquals(Status.Code.FAILED_PRECONDITION, Status.fromThrowable(GrpcExceptionMapper.toGrpcException(new UserNotActiveException("inactive"))).getCode());
        assertEquals(Status.Code.FAILED_PRECONDITION, Status.fromThrowable(GrpcExceptionMapper.toGrpcException(new InsufficientStockException("stock"))).getCode());
    }

    @Test
    void mapsIllegalArgumentToInvalidArgument() {
        assertEquals(Status.Code.INVALID_ARGUMENT, Status.fromThrowable(GrpcExceptionMapper.toGrpcException(new IllegalArgumentException("bad"))).getCode());
    }

    @Test
    void mapsUnexpectedErrorsToInternal() {
        assertEquals(Status.Code.INTERNAL, Status.fromThrowable(GrpcExceptionMapper.toGrpcException(new RuntimeException("boom"))).getCode());
    }
}