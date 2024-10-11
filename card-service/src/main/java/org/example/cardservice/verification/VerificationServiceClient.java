package org.example.cardservice.verification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Olga Maciaszek-Sharma
 */
@Component
public class VerificationServiceClient {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(VerificationServiceClient.class);
	private final RestClient restClient;

	VerificationServiceClient(@LoadBalanced RestClient.Builder restClientBuilder) {
		this.restClient = restClientBuilder.build();
	}

	public ResponseEntity<VerificationResult> verify(VerificationApplication verificationApplication) {
		LOGGER.debug("Sending verification request for application placed by user {}", verificationApplication
				.getUserId());
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
				.fromHttpUrl("http://fraud-verifier/cards/verify")
				.queryParam("uuid", verificationApplication.getUserId())
				.queryParam("cardCapacity", verificationApplication.getCardCapacity());
		return restClient.get().uri(uriComponentsBuilder.toUriString())
				.retrieve().toEntity(VerificationResult.class);
//		return restClient.getForEntity(uriComponentsBuilder.toUriString(),
//				VerificationResult.class);
	}
}
