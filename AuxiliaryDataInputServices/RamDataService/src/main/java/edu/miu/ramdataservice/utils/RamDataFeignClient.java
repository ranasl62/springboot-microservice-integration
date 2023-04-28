package edu.miu.ramdataservice.utils;


import edu.miu.ramdataservice.domain.Metric;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Main-Ram-Data-Service", fallback = RamDataFeignClientFallback.class)
public interface RamDataFeignClient {
    @PostMapping("/ram-data/send")
    @CircuitBreaker(name = "ram-data-feign-client-circuit-breaker", fallbackMethod = "saveDataLocally")
    String sendRemoteData(@RequestBody Metric metric);

    default String saveDataLocally(Metric metric, Throwable throwable) {
        return "Data cannot be sent remotely because of an exception!";
    }
}
