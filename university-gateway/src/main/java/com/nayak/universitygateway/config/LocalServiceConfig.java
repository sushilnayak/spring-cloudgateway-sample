package com.nayak.universitygateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// in pcf, cloud profile is added to deployed application hence when deployed on PCF, these beans won't get created
@Profile("!cloud")
@Configuration
public class LocalServiceConfig {

    @Bean
    public RouteLocator localServiceRouter(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(predicateSpec -> predicateSpec.path("/api/v1/student*")
                        .uri("http://localhost:8080")) // in case of EUREKA, the path would be "lb://STUDENT-SERVICE" since spring.application.name of service would be student-service

// Below route is commented is because same stuff is done in application.yml file. Either one approach can be use.

//                .route(predicateSpec -> predicateSpec.path("/student-service/student*")
//                        .filters(f -> f.rewritePath("/student-service/(?<segment>.*)", "/api/v1/${segment}"))
//                        .uri("http://localhost:8080"))
                .build();
    }

}
