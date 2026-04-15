package com.alex.thesis.orderService.client.services;

import org.springframework.stereotype.Service;
import com.alex.thesis.proto.user.v1.ValidateUserRequest;
import com.alex.thesis.proto.user.v1.ValidateUserResponse;

import net.devh.boot.grpc.client.inject.GrpcClient;

import com.alex.thesis.proto.user.v1.UserServiceGrpc.UserServiceBlockingStub;

@Service
public class DownstreamUserService {
    
    @GrpcClient("userService")
    private UserServiceBlockingStub userStub;

    public ValidateUserResponse validateUser(String userId){
        return userStub.validateUser(
            ValidateUserRequest.newBuilder().setId(userId).build()
        );
    }
}
