package com.micro.consumermovie;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.consumermovie.controller.MovieController;
import feign.Contract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerMovieApplicationTests {

	@Autowired
	DiscoveryClient discoveryClient;

	@Autowired
	MovieController movieController;



	@Test
	public void contextLoads() throws JsonProcessingException {

		movieController.loadBalanceGet();

	}

}
