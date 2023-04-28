package edu.miu.cpudataservice.utils;

import edu.miu.cpudataservice.domain.CpuData;
import edu.miu.cpudataservice.domain.Metric;
import edu.miu.cpudataservice.repository.CpuDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class CpuDataFeignClientFallback implements CpuDataFeignClient {

    @Autowired private CpuDataRepository cpuDataRepository;

    @Override
    public String sendRemoteData(Metric metric) {
        cpuDataRepository.save((CpuData) metric);
        return "Data saved locally as we cannot reach the parent main service!";
    }
}
