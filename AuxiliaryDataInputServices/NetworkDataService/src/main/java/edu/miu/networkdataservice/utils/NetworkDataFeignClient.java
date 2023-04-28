package edu.miu.networkdataservice.utils;

import edu.miu.networkdataservice.domain.Metric;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Main-Network-Data-Service", fallback = NetworkDataFeignClientFallback.class)
public interface NetworkDataFeignClient {
    @PostMapping("/network-data/send")
    @CircuitBreaker(name = "network-data-feign-client-circuit-breaker", fallbackMethod = "saveDataLocally")
    String sendRemoteData(@RequestBody Metric metric);

    default String saveDataLocally(Metric metric, Throwable throwable) {
        return "Data cannot be sent remotely because of an exception!";
    }
}
