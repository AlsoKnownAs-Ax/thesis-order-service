package com.alex.thesis.benchmarkService.grpc;

import com.alex.thesis.benchmarkService.service.BenchmarkService;
import com.alex.thesis.proto.benchmark.v1.BenchmarkItem;
import com.alex.thesis.proto.benchmark.v1.BenchmarkRequest;
import com.alex.thesis.proto.benchmark.v1.BenchmarkResponse;
import com.alex.thesis.proto.benchmark.v1.BenchmarkServiceGrpc.BenchmarkServiceImplBase;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BenchmarkGrpcService extends BenchmarkServiceImplBase {
	private final BenchmarkService benchmarkService;

	public BenchmarkGrpcService(BenchmarkService benchmarkService) {
		this.benchmarkService = benchmarkService;
	}

	@Override
	public void getItemsUnary(BenchmarkRequest request, StreamObserver<BenchmarkResponse> responseObserver) {
		try {
			BenchmarkResponse.Builder response = BenchmarkResponse.newBuilder();
			benchmarkService.generateItems(
					request.getItemCount(),
					request.getPayloadSizeBytes(),
					response::addItems);
			responseObserver.onNext(response.build());
			responseObserver.onCompleted();
		} catch (IllegalArgumentException ex) {
			responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(ex.getMessage()).asRuntimeException());
		}
	}

	@Override
	public void getItemsStream(BenchmarkRequest request, StreamObserver<BenchmarkItem> responseObserver) {
		try {
			benchmarkService.generateItems(
					request.getItemCount(),
					request.getPayloadSizeBytes(),
					responseObserver::onNext);
			responseObserver.onCompleted();
		} catch (IllegalArgumentException ex) {
			responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(ex.getMessage()).asRuntimeException());
		}
	}
}
