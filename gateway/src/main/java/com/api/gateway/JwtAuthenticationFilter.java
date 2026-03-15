package com.api.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

//@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Autowired
    private JwtUtil jwtUtil;
@Override
public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

    String path = exchange.getRequest().getURI().getPath();

    // Skip auth endpoints
    if (path.startsWith("/auth")) {
        return chain.filter(exchange);
    }

    String authHeader = exchange.getRequest()
            .getHeaders()
            .getFirst("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return Mono.error(new RuntimeException("Missing or Invalid Authorization Header"));
    }

    return chain.filter(exchange);
}
}