package com.demo.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {
		
    @RequestMapping(value = "/taskDistribution")
    public ResponseEntity<?> taskDistribution(
    		@RequestParam(value="noOfTasks", required=true) int noOfTasks,
    		@RequestParam(value="noOfVendors", required=true) int noOfVendors,
    		@RequestParam(value="percentage", required=true) int[] percentage) {
    	List<String> result = null;
    	int totalPercentage = 0;
    	if (percentage.length != noOfVendors) {
    		result = new ArrayList<>(1);
    		result.add("Number of Vendors and number of Percentages defined are not equal");
    		return new ResponseEntity<>(result, HttpStatus.OK);
    	}
		
		for (int i: percentage ) {
			totalPercentage += i;
		}
		if (totalPercentage != 100) {
    		result = new ArrayList<>(1);
    		result.add("Total Percentage must be equal to 100");
    		return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		result = new ArrayList<>(noOfVendors);
		long[] dis = new long[noOfVendors];
		long remaining = noOfTasks;
		for (int i=1; i<noOfVendors; i++) {
			dis[i] = Math.round(noOfTasks * percentage[i-1] / 100.0);
			result.add("Vendor at position: " + (i) + " will get " + dis[i] + " tasks");
			remaining -=dis[i];
		}
		result.add("Vendor at position: " + (noOfVendors) + " will get " + remaining + " tasks");
    	
		return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
