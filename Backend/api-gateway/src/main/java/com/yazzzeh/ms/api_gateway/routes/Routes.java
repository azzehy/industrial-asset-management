package com.yazzzeh.ms.api_gateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class Routes {

    @Value("${machine.service.url}")
    private String machineServiceUrl;

    @Value("${machineavailability.service.url}")
    private String machineAvailabilityServiceUrl;

    @Value("${workorder.service.url}")
    private String workOrderServiceUrl;

    @Bean
    public RouterFunction<ServerResponse> machineServiceRoute(){
        return GatewayRouterFunctions.route("machine_service")
                .route(RequestPredicates.path("/api/machine"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri(machineServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("machineServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> machineServiceSwaggerRoute(){
        return GatewayRouterFunctions.route("machine_service_swagger")
                .route(RequestPredicates.path("/aggregate/machine-service/v3/api-docs"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri(machineServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("machineServiceSwaggerCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> availabilityServiceRoute(){
        return GatewayRouterFunctions.route("machineavailability_service")
                .route(RequestPredicates.path("/api/machine-availability/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri(machineAvailabilityServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("availabilityServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute(){
        return GatewayRouterFunctions.route("workorder_service")
                .route(RequestPredicates.path("/api/workorder"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri(workOrderServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("orderServiceCircuitBreaker", URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute(){
        return GatewayRouterFunctions.route("fallbackRoute")
                .GET("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("Service Unavailable, please try again later"))
                .build();
    }
}
