package com.in28minutes.microservices.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter {

	// 1st, we'll create a logger
	 private Logger logger = LoggerFactory.getLogger(LoggingFilter.class);  
	
	 // This class will be applicable for global logging.
	 
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		// below line will log the path of incoming requests
		logger.info("---------Path of the request recieved -> {}", exchange.getRequest().getPath());
		
		// you can explore more & log more things
		
		// this will log and let the execution continue
		return chain.filter(exchange);
	}

}
