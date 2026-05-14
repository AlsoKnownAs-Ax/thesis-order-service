package com.alex.thesis.benchmarkService.service;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.alex.thesis.proto.benchmark.v1.BenchmarkItem;

@Service
public class BenchmarkService {

	public static final int MAX_ITEM_COUNT = 10_000;
	public static final int MAX_PAYLOAD_SIZE_BYTES = 32_768;

	public void generateItems(int itemCount, int payloadSizeBytes, Consumer<BenchmarkItem> consumer) {
		validateRequest(itemCount, payloadSizeBytes);
		String payload = createPayload(payloadSizeBytes);
		for (int id = 1; id <= itemCount; id++) {
			consumer.accept(createItem(id, payload));
		}
	}

	private void validateRequest(int itemCount, int payloadSizeBytes) {
		if (itemCount <= 0) {
			throw new IllegalArgumentException("item_count must be greater than 0");
		}
		if (payloadSizeBytes < 0) {
			throw new IllegalArgumentException("payload_size_bytes must be greater than or equal to 0");
		}
		if (itemCount > MAX_ITEM_COUNT) {
			throw new IllegalArgumentException("item_count must be less than or equal to " + MAX_ITEM_COUNT);
		}
		if (payloadSizeBytes > MAX_PAYLOAD_SIZE_BYTES) {
			throw new IllegalArgumentException("payload_size_bytes must be less than or equal to "
					+ MAX_PAYLOAD_SIZE_BYTES);
		}
	}

	private String createPayload(int payloadSizeBytes) {
		return "x".repeat(payloadSizeBytes);
	}

	private BenchmarkItem createItem(int id, String payload) {
		return BenchmarkItem.newBuilder()
				.setId(id)
				.setPayload(payload)
				.build();
	}
}
