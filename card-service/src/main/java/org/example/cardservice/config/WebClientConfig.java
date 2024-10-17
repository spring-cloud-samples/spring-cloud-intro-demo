package org.example.cardservice.config;

import org.example.excluded.CustomLoadBalancerConfiguration;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Olga Maciaszek-Sharma
 */
@Configuration
@LoadBalancerClient(value = "fraud-verifier", configuration = CustomLoadBalancerConfiguration.class)
public class WebClientConfig {

	@Bean
	@LoadBalanced
	WebClient.Builder loadBalancedWebClientBuilder(ObjectProvider<WebClientCustomizer> customizerProvider) {
		WebClient.Builder builder = WebClient.builder();
		customizerProvider.orderedStream().forEach((customizer) -> customizer.customize(builder));
		return builder;
	}

	@Bean
	@Qualifier("webClient")
	WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}
}



