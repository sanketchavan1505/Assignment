package com.demo.controller;



import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/process-concurrent")
public class ProcessConcurrentController {

    @PostMapping
    public Response processConcurrent(@RequestBody Payload payload) throws InterruptedException {
        long startTime = System.nanoTime();
        // Sort each sub-array concurrently
        int[][] toSort = payload.getToSort();
        CountDownLatch latch = new CountDownLatch(toSort.length);

        for (int i = 0; i < toSort.length; i++) {
            final int index = i;
            new Thread(() -> {
                Arrays.sort(toSort[index]);
                latch.countDown();
            }).start();
        }

        latch.await(); // Wait for all sorting threads to finish
        long endTime = System.nanoTime();
        long timeTaken = endTime - startTime;

        return new Response(toSort, timeTaken);
    }

    static class Payload {
        private int[][] toSort;

        public int[][] getToSort() {
            return toSort;
        }

        public void setToSort(int[][] toSort) {
            this.toSort = toSort;
        }
    }

    static class Response {
        @JsonProperty("sorted_arrays")
        private int[][] sortedArrays;

        @JsonProperty("time_ns")
        private long timeInNanoSeconds;

        public Response(int[][] sortedArrays, long timeInNanoSeconds) {
            this.sortedArrays = sortedArrays;
            this.timeInNanoSeconds = timeInNanoSeconds;
        }
    }
}


