package org.example.cardservice.verification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Olga Maciaszek-Sharma
 */
@Component
public class VerificationServiceClient {

	private final WebClient.Builder webClientBuilder;

	VerificationServiceClient(@LoadBalanced WebClient.Builder webClientBuilder) {
		this.webClientBuilder = webClientBuilder;
	}

	public Mono<VerificationResult> verify(VerificationApplication verificationApplication) {
		WebClient webClient = webClientBuilder.build();
		return webClient.get()
				.uri(uriBuilder -> uriBuilder
						.scheme("http")
						.host("fraud-verifier").path("/cards/verify")
						.queryParam("uuid", verificationApplication.getUserId())
						.queryParam("cardCapacity", verificationApplication
								.getCardCapacity())
						.build())
				.retrieve().bodyToMono(VerificationResult.class);
	}
}
