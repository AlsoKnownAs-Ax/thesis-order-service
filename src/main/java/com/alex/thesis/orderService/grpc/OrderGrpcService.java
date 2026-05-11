package com.alex.thesis.orderService.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alex.thesis.proto.order.v1.CreateOrderRequest;
import com.alex.thesis.proto.order.v1.CreateOrderResponse;
import com.alex.thesis.proto.order.v1.GetOrderByIdRequest;
import com.alex.thesis.proto.order.v1.GetOrderByIdResponse;
import com.alex.thesis.proto.order.v1.OrderServiceGrpc.OrderServiceImplBase;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class OrderGrpcService extends OrderServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(OrderGrpcService.class);

    public OrderGrpcService(){
    }

    @Override
    public void getOrderById(GetOrderByIdRequest request, StreamObserver<GetOrderByIdResponse> responseObserver) {
        log.info("gRPC GetOrderById called; service logic removed pending redesign");
        responseObserver.onError(Status.UNIMPLEMENTED
            .withDescription("Order service logic removed; endpoint pending redesign")
            .asRuntimeException());
    }

    @Override
    public void createOrder(CreateOrderRequest request, StreamObserver<CreateOrderResponse> responObserver){
        log.info("gRPC CreateOrder called; service logic removed pending redesign");
        responObserver.onError(Status.UNIMPLEMENTED
            .withDescription("Order service logic removed; endpoint pending redesign")
            .asRuntimeException());
    }
}
