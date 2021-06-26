package com.uwjx.gateway.server.filter;

import com.alibaba.fastjson.JSON;
import com.uwjx.gateway.server.domain.RespDomain;
import com.uwjx.gateway.server.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/26 17:20
 */
@Slf4j
@Component
@Order(1)
public class TokenFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.warn("进入 TokenFilter");
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getPath().pathWithinApplication().value();
        HttpHeaders headers = request.getHeaders();
        log.info("Headers: " + headers);
        log.info("path: " + path);
        log.info("URI: " + request.getURI());
        if(!path.contains("login")){
            RespDomain respDomain = new RespDomain();
            String token = request.getHeaders().getFirst("token");
            if (!StringUtils.hasLength(token)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                respDomain.setCode(407);
                respDomain.setMessage("TOKEN 不可为空");
                return getVoidMono(response, respDomain);
            }
            boolean isVerify = JWTUtil.verifyToken(token, "zjxf");
            if(!isVerify){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                respDomain.setCode(408);
                respDomain.setMessage("TOKEN 失效，需要重新登录");
                return getVoidMono(response, respDomain);
            }
        }


        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        }));
    }

    private Mono<Void> getVoidMono(ServerHttpResponse serverHttpResponse, Object obj) {
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        DataBuffer dataBuffer = serverHttpResponse.bufferFactory().wrap(JSON.toJSONString(obj).getBytes());
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }
}
