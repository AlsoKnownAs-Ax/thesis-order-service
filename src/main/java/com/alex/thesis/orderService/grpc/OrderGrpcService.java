package com.alex.thesis.orderService.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alex.thesis.orderService.entity.Order;
import com.alex.thesis.orderService.service.OrderBusinessService;
import com.alex.thesis.proto.order.v1.CreateOrderRequest;
import com.alex.thesis.proto.order.v1.CreateOrderResponse;
import com.alex.thesis.proto.order.v1.GetOrderByIdRequest;
import com.alex.thesis.proto.order.v1.GetOrderByIdResponse;
import com.alex.thesis.proto.order.v1.OrderServiceGrpc.OrderServiceImplBase;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class OrderGrpcService extends OrderServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(OrderGrpcService.class);

    private final OrderBusinessService orderBusinessService;

    public OrderGrpcService(OrderBusinessService orderBusinessService){
        this.orderBusinessService = orderBusinessService;
    }

    @Override
    public void getOrderById(GetOrderByIdRequest request, StreamObserver<GetOrderByIdResponse> responseObserver) {
        try {
            Order order = orderBusinessService.getOrderById(request.getOrderId());

            GetOrderByIdResponse response = GetOrderByIdResponse.newBuilder()
                    .setOrderId(order.getId())
                    .setUserId(order.getUserId())
                    .setProductId(order.getProductId())
                    .setQuantity(order.getQuantity())
                    .setUnitPrice(order.getUnitPrice())
                    .setTotalPrice(order.getTotalPrice())
                    .setStatus(order.getStatus())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void createOrder(CreateOrderRequest request, StreamObserver<CreateOrderResponse> responObserver){
        log.info("gRPC CreateOrder received userId={}, productId={}, quantity={}",
            request.getUserId(), request.getProductId(), request.getQuantity());

        try {
            Order order = orderBusinessService.createOrder(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity()
            );

            CreateOrderResponse response = CreateOrderResponse.newBuilder()
                .setOrderId(order.getId())
                .setStatus(order.getStatus())
                .setTotalPrice(order.getTotalPrice())
                .setMessage("Order created successfully")
                .build();

            log.info("gRPC CreateOrder success orderId={}, status={}", order.getId(), order.getStatus());
            responObserver.onNext(response);
            responObserver.onCompleted();
        } catch (Exception e) {
            log.error("gRPC CreateOrder failed userId={}, productId={}, quantity={}",
                request.getUserId(), request.getProductId(), request.getQuantity(), e);
            responObserver.onError(e);
        }
    }
}
