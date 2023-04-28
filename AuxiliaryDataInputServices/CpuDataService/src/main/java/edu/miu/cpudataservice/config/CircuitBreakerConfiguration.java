package edu.miu.cpudataservice.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfiguration {
    @Value("${resilience.factory.configs.time-limiter.timeout}")
    private String timeOutDuration;
    @Value("${resilience.factory.configs.circuit-breaker.sliding-window-size}")
    private String slidingWindowSize;
    @Value("${resilience.factory.configs.circuit-breaker.failure-rate}")
    private String failureRateThreshold;
    @Value("${resilience.factory.configs.circuit-breaker.duration-in-open-state}")
    private String waitDurationInOpenState;

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofSeconds(Integer.parseInt(timeOutDuration)))
                        .build())
                .circuitBreakerConfig(CircuitBreakerConfig.custom()
                        .slidingWindowSize(Integer.parseInt(slidingWindowSize))
                        .failureRateThreshold(Float.parseFloat(failureRateThreshold))
                        .waitDurationInOpenState(Duration.ofSeconds(Integer.parseInt(waitDurationInOpenState)))
                        .build())
                .build());
    }
}
