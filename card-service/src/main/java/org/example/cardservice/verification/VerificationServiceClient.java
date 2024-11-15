package org.example.cardservice.verification;

import reactor.core.publisher.Mono;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
						.host("fraud-verifier").path("/cards/test")
						.queryParam("uuid", verificationApplication.getUserId())
						.queryParam("cardCapacity", verificationApplication
								.getCardCapacity())
						.build())
				.retrieve().bodyToMono(VerificationResult.class);
	}

	public Mono<String> test() {
		WebClient webClient = webClientBuilder.build();
		return webClient.get()
				.uri("http://fraud-verifier/cards/test/{testSegment}",
						"test1")
				.retrieve().bodyToMono(String.class);

	}
}
