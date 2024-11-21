package org.example.cardservice.user;

import org.example.cardservice.application.CardApplicationDto;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * @author Olga Maciaszek-Sharma
 */
@Component
public class UserServiceClient {

	private final RestClient restClient;
	private final DiscoveryClient discoveryClient;


	UserServiceClient(RestClient.Builder restClientBuilder,
			DiscoveryClient discoveryClient) {
		this.restClient = restClientBuilder.build();
		this.discoveryClient = discoveryClient;
	}

	public ResponseEntity<User> registerUser(CardApplicationDto.User userDto) {
		ServiceInstance instance = discoveryClient.getInstances("proxy")
				.stream().findAny()
				.orElseThrow(() -> new IllegalStateException("Proxy unavailable"));
		return restClient.post().uri(instance.getUri().toString()
						+ "/user-service/registration")
				.body(userDto)
				.retrieve()
				.toEntity(User.class);
	}
}