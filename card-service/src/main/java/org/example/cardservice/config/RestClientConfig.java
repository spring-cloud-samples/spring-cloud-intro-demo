package org.example.cardservice.config;

import org.example.excluded.CustomLoadBalancerConfiguration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

/**
 * @author Olga Maciaszek-Sharma
 */
@Configuration
@LoadBalancerClient(value = "fraud-verifier", configuration = CustomLoadBalancerConfiguration.class)
public class RestClientConfig {

	@Bean
	@LoadBalanced
	RestClient.Builder loadBalancedRestClient() {
		return RestClient.builder();
	}

	@Bean
	@Primary
	RestClient.Builder restClient() {
		return RestClient.builder();
	}

}



