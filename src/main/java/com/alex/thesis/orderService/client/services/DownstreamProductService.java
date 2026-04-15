package com.alex.thesis.orderService.client.services;

import org.springframework.stereotype.Service;

import com.alex.thesis.proto.product.v1.CheckAvailabilityRequest;
import com.alex.thesis.proto.product.v1.CheckAvailabilityResponse;
import com.alex.thesis.proto.product.v1.GetProductByIdRequest;
import com.alex.thesis.proto.product.v1.GetProductByIdResponse;
import com.alex.thesis.proto.product.v1.ProductServiceGrpc.ProductServiceBlockingStub;

import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class DownstreamProductService {
    
    @GrpcClient("productService")
    private ProductServiceBlockingStub productStub;

    public GetProductByIdResponse getProductById(String productId){
        return productStub.getProductById(
            GetProductByIdRequest.newBuilder().setProductId(productId).build()
        );
    }

    public CheckAvailabilityResponse checkAvailability(String productId, int quantity){
        return productStub.checkAvailability(
            CheckAvailabilityRequest.newBuilder()
                .setProductId(productId)
                .setRequestedQuantity(quantity)
                .build()   
        );
    }
}
