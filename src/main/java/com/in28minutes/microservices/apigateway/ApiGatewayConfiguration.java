package com.in28minutes.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	// The idea here is to build custom routes, by hardcoding beans
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		
		/*
		 * For using custom routes, you have to disable the locator in
		 * application.properties. We'll make use of lambda function // suppose we
		 * want to add a requestHeader / a requestParameter, for that, use filters()		 
		 * similarly, you can add multiple other things as well thru filters()
		 */
		
		// CASE 01 : if the path is /get, then return this uri (http://httpbin.org:80)
		
		/*
		 * CASE 02 : Using loadbalancer & naming server to redirect to
		 * /currency-exchange for any request coming to currency-exchange. 
		 * So if a request starts with /currency-exchange, 
		 * 		> i want to talk to eureka, 
		 * 		> find the location of that service & 
		 * 		> loadbalance between the instances that are returned.
		 */
		
		return builder.routes()
				.route(p -> p.path("/get")
						.filters(f -> f.addRequestHeader("MyHeader", "MyURI")
										.addRequestParameter("Param", "MyValue"))
						.uri("http://httpbin.org:80"))
				.route(p -> p.path("/currency-exchange/**")
						.uri("lb://currency-exchange"))
				.route(p -> p.path("/currency-conversion/**")
						.uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-feign/**")
						.uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-new/**")
						.filters(f -> f
								.rewritePath("/currency-conversion-new/(?<segment>.*)", 
												"/currency-conversion-feign/${segment}"))
						.uri("lb://currency-conversion"))
				
				.build();
				
		
//		return builder.routes().build();  this line is used when we dont customise the routes
	}
	
}
