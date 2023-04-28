package edu.miu.ramdataservice.utils;


import edu.miu.ramdataservice.domain.Metric;
import edu.miu.ramdataservice.domain.RamData;
import edu.miu.ramdataservice.repository.RamDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class RamDataFeignClientFallback implements RamDataFeignClient {

    @Autowired private RamDataRepository ramDataRepository;

    @Override
    public String sendRemoteData(Metric metric) {
        ramDataRepository.save((RamData) metric);
        return "Data saved locally as we cannot reach the parent main service!";
    }
}
