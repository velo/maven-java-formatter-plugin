package com.marvinformatics.formatter;

import java.util.concurrent.atomic.AtomicInteger;

public class ResultCollector {

	private final AtomicInteger successCount = new AtomicInteger();

	private final AtomicInteger failCount = new AtomicInteger();

	private final AtomicInteger skippedCount = new AtomicInteger();

	public void collect(Result result) {
		switch (result) {
		case SUCCESS:
			successCount.incrementAndGet();
			break;
		case FAIL:
			failCount.incrementAndGet();
			break;
		case SKIPPED:
			skippedCount.incrementAndGet();
			break;
		}
	}

	public int successCount() {
		return successCount.get();
	}

	public int failCount() {
		return failCount.get();
	}

	public int skippedCount() {
		return skippedCount.get();
	}

}
