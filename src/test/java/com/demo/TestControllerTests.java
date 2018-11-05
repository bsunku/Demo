package com.demo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.demo.rest.TestController;

@RunWith(SpringRunner.class)
@WebMvcTest(TestController.class)
public class TestControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void taskDistributionStatusCheck() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/test/taskDistribution?noOfTasks=4&noOfVendors=3&percentage=30,30,40");
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}

	@Test
	public void taskDistributionWithWrongNoOfPercentages() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/test/taskDistribution?noOfTasks=4&noOfVendors=3&percentage=30,30");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[\"Number of Vendors and number of Percentages defined are not equal\"]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void taskDistributionWithTotalPercentagesNotEqualto100() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/test/taskDistribution?noOfTasks=4&noOfVendors=3&percentage=30,30,50");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[\"Total Percentage must be equal to 100\"]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void taskDistributionWithTestData() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/test/taskDistribution?noOfTasks=4&noOfVendors=3&percentage=30,30,40");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "[\"Vendor at position: 1 will get 1 tasks\",\"Vendor at position: 2 will get 1 tasks\","
				+ "\"Vendor at position: 3 will get 2 tasks\"]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}	
}
