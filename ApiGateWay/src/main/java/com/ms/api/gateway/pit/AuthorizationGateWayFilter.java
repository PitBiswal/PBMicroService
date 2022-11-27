package com.ms.api.gateway.pit;

import org.apache.http.HttpStatus;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationGateWayFilter extends AbstractGatewayFilterFactory<AuthorizationGateWayFilter.Config>{
	
	public static class Config {
		
	}



	@Override
	public GatewayFilter apply(Config config) {
		return (exchange,chain)->{
			ServerHttpRequest req=exchange.getRequest();
			if(!req.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange,"No Authorization Header",HttpStatus.SC_UNAUTHORIZED);
				
			}
			String authHeader=req.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwt=authHeader.replace("pit", "");
			return chain.filter(exchange);
			
		};
		
	}



	private Mono<Void> onError(ServerWebExchange exchange, String string, int scUnauthorized) {
		 ServerHttpResponse res=exchange.getResponse();
		 res.setRawStatusCode(scUnauthorized);
		return res.setComplete();
	}
	
	private boolean isJWTValid(String Jwt) {
		boolean retuenBalue=true;
		//Jwts.parser().setSigningKey(null);
		return retuenBalue;
	}
}
