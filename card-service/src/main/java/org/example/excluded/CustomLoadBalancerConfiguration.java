package org.example.excluded;

import org.jspecify.annotations.Nullable;

import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.accept.InvalidApiVersionException;
import org.springframework.web.accept.MissingApiVersionException;
import org.springframework.web.reactive.accept.ApiVersionStrategy;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author Olga Maciaszek-Sharma
 */
public class CustomLoadBalancerConfiguration {

//	@Bean
//	public ServiceInstanceListSupplier discoveryClientServiceInstanceListSupplier(
//			ConfigurableApplicationContext context) {
//		return ServiceInstanceListSupplier.builder()
//				.withDiscoveryClient()
//				.withZonePreference()
//				.build(context);
//	}

//	@Bean
//	public ApiVersionStrategy customStrategy(){
//		return new ApiVersionStrategy() {
//			@Override
//			public @Nullable String resolveVersion(ServerWebExchange exchange) {
//				return "3";
//			}
//
//			@Override
//			public Comparable<?> parseVersion(String version) {
//				return new Comparable<Object>() {
//					@Override
//					public int compareTo(Object o) {
//						return 0;
//					}
//				};
//			}
//
//			@Override
//			public void validateVersion(@Nullable Comparable<?> requestVersion, ServerWebExchange exchange) throws MissingApiVersionException, InvalidApiVersionException {
//
//			}
//
//			@Override
//			public @Nullable Comparable<?> getDefaultVersion() {
//				throw new UnsupportedOperationException("Please, implement me.");
//			}
//
//			@Override
//			public void handleDeprecations(Comparable<?> version, ServerWebExchange exchange) {
//
//			}
//		};
//	}
}
