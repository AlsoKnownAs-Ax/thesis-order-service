package com.alex.thesis.orderService.grpc;

import com.alex.thesis.orderService.entity.Order;
import com.alex.thesis.orderService.grpc.OrderServiceGrpc.OrderServiceImplBase;
import com.alex.thesis.orderService.service.OrderBusinessService;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class OrderGrpcService extends OrderServiceImplBase {
    
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

            responObserver.onNext(response);
            responObserver.onCompleted();
        } catch (Exception e) {
            responObserver.onError(e);
        }
    }
}
