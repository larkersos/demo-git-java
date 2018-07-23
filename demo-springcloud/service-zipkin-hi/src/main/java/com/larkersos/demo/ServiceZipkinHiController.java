package com.larkersos.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ServiceZipkinHiController {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceZipkinHiController.class.getName());


	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/hi")
	public String callHome(){
		LOG.info("calling trace service-hi  ");
		return restTemplate.getForObject("http://localhost:8989/miya", String.class);
	}
	@RequestMapping("/info")
	public String info(){
		LOG.info("calling trace service-hi ");

		return "ServiceZipkinHiController : i'm service-hi";

	}

	@Bean
	public AlwaysSampler defaultSampler(){
		return new AlwaysSampler();
	}
}
