package org.example.cardservice.application;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.example.cardservice.user.User;
import org.example.cardservice.user.UserServiceClient;
import org.example.cardservice.verification.VerificationApplication;
import org.example.cardservice.verification.VerificationResult;
import org.example.cardservice.verification.VerificationServiceClient;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

/**
 * @author Olga Maciaszek-Sharma
 */
@Service
class CardApplicationService {

	private final UserServiceClient userServiceClient;
	private final VerificationServiceClient verificationServiceClient;

	public CardApplicationService(UserServiceClient userServiceClient,
			VerificationServiceClient verificationServiceClient) {
		this.userServiceClient = userServiceClient;
		this.verificationServiceClient = verificationServiceClient;
	}

	Mono<ApplicationResult> registerApplication(CardApplicationDto applicationDTO) {
		return userServiceClient.registerUser(applicationDTO.user)
				.map(createdUser -> new CardApplication(UUID.randomUUID(),
						createdUser, applicationDTO.cardCapacity)
				)
				.flatMap(this::verifyApplication);
	}

	private Mono<ApplicationResult> verifyApplication(CardApplication application) {
		return verificationServiceClient  // uses @LoadBalanced WebClient.Builder
						.verify(new VerificationApplication(application.getUuid(),
								application.getCardCapacity()))
						.map(verificationResult -> updateApplication(verificationResult,
								application));
	}

	private ApplicationResult updateApplication(VerificationResult verificationResult,
			CardApplication application) {
		if (!VerificationResult.Status.VERIFICATION_PASSED
				.equals(verificationResult.status)
				|| !User.Status.OK.equals(application.getUser().getStatus())) {
			application.setApplicationResult(ApplicationResult.rejected());
		}
		else {
			application.setApplicationResult(ApplicationResult.granted());
		}
		return application.getApplicationResult();
	}
}
