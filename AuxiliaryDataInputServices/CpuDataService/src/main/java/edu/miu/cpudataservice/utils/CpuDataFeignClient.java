package edu.miu.cpudataservice.utils;

import edu.miu.cpudataservice.domain.Metric;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Main-Cpu-Data-Service", fallback = CpuDataFeignClientFallback.class)
public interface CpuDataFeignClient {
    @PostMapping("/cpu-data/send")
    @CircuitBreaker(name = "cpu-data-feign-client-circuit-breaker", fallbackMethod = "saveDataLocally")
    String sendRemoteData(@RequestBody Metric metric);

    default String saveDataLocally(Metric metric, Throwable throwable) {
        return "Data cannot be sent remotely because of an exception!";
    }
}
