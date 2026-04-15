package com.alex.thesis.orderService.client.services;

import org.springframework.stereotype.Service;

import com.alex.thesis.orderService.client.utils.CallResult;
import com.alex.thesis.orderService.client.utils.SafeCall;
import com.alex.thesis.proto.user.v1.ValidateUserRequest;
import com.alex.thesis.proto.user.v1.ValidateUserResponse;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;

import com.alex.thesis.proto.user.v1.UserServiceGrpc.UserServiceBlockingStub;

@Service
public class DownstreamUserService {
    
    @GrpcClient("userService")
    private UserServiceBlockingStub userStub;

    public ValidateUserResponse validateUser(String userId) {
        //TODO: Make the DX of SafeCall better, if i have time
        CallResult<ValidateUserResponse> result = SafeCall.run(
            () -> userStub.validateUser(
                ValidateUserRequest.newBuilder().setId(userId).build()
            ),
            error -> {
                if (error instanceof StatusRuntimeException e
                    && e.getStatus().getCode() == Status.Code.INVALID_ARGUMENT) {
                    return new IllegalArgumentException("User does not exist: " + userId, e);
                }
                
                return error;
            }
        );

        if (!result.isSuccess()) {
            throw new IllegalArgumentException(result.error().getMessage(), result.error());
        }

        return result.Data();
    }
}
