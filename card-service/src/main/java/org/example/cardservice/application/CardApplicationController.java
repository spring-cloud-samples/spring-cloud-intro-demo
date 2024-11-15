package org.example.cardservice.application;

import org.example.cardservice.verification.VerificationServiceClient;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Olga Maciaszek-Sharma
 */
@RestController
@RequestMapping("/application")
class CardApplicationController {

	private final CardApplicationService cardApplicationService;
	private final VerificationServiceClient verificationServiceClient;

	public CardApplicationController(CardApplicationService cardApplicationService, VerificationServiceClient verificationServiceClient) {
		this.cardApplicationService = cardApplicationService;
		this.verificationServiceClient = verificationServiceClient;
	}

	@PostMapping
	Mono<ApplicationResult> apply(@RequestBody CardApplicationDto applicationDTO) {
		return cardApplicationService.registerApplication(applicationDTO);
	}

	@GetMapping("/test")
	Mono<String> test() {
		return verificationServiceClient.test();
	}
}
