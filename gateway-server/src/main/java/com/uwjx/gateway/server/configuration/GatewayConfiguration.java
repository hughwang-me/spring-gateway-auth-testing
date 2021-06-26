package com.uwjx.gateway.server.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/26 13:54
 */
@Configuration
@Slf4j
public class GatewayConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder builder = routeLocatorBuilder.routes();
        builder.route("b-router" ,f->f.path("/test/**")
                .uri("http://localhost:8021"));
        builder.route("b-router" ,f->f.path("/login/**")
                .uri("http://localhost:8022"));
        return builder.build();
    }
}
