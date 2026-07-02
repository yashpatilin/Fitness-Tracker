package com.example.gateway;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import user.RegisterRequest;
import user.UserService;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserSyncFilter implements WebFilter{
	
	private final UserService userService;
	
	
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain){
	String userId = exchange.getRequest().getHeaders().getFirst("X-User-Id");
	String token = exchange.getRequest().getHeaders().getFirst("Authorization");
	RegisterRequest request = getUserDetails(token);
	
	
	if(userId == null) {
		userId = request.getKeycloakId();
	}
}


	private RegisterRequest getUserDetails(String token) {
		try {
			String tokenWithoutBearer = token.replace("Bearer ", "");
		}
	}
