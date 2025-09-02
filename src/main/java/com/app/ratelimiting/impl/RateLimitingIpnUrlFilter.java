package com.app.ratelimiting.impl;

import com.app.ratelimiting.RateLimiting;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Primary
public class RateLimitingIpnUrlFilter extends OncePerRequestFilter implements RateLimiting {

    private  final Map<String, Bucket> buckets = new ConcurrentHashMap<>();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        String url = request.getRequestURI();
        String key = ip+":"+url;

        Bucket bucket = buckets.computeIfAbsent(key,this::createBucket);
        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request,response);
        }else {
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\" : \"Too Many Request for that url " + url + " \"}");
        }

    }

    private Bucket createBucket(String key){
        Bandwidth bandwidth = Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(bandwidth).build();
    }
}
