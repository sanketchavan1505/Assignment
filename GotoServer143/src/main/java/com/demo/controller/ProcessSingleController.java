package com.demo.controller;


	
	import com.fasterxml.jackson.annotation.JsonProperty;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;

	import java.util.Arrays;

	@RestController
	@RequestMapping("/process-single")
	public class ProcessSingleController {

	    @PostMapping
	    public Response processSingle(@RequestBody Payload payload) {
	        long startTime = System.nanoTime();
	        // Sort each sub-array sequentially
	        int[][] toSort = payload.getToSort();
	        for (int[] arr : toSort) {
	            Arrays.sort(arr);
	        }
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



