package edu.miu.networkdataservice.utils;

import edu.miu.networkdataservice.domain.Metric;
import edu.miu.networkdataservice.domain.NetworkData;
import edu.miu.networkdataservice.repository.NetworkDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class NetworkDataFeignClientFallback implements NetworkDataFeignClient {

    @Autowired private NetworkDataRepository networkDataRepository;

    @Override
    public String sendRemoteData(Metric metric) {
        networkDataRepository.save((NetworkData) metric);
        return "Data saved locally as we cannot reach the parent main service!";
    }

    @Override
    public String saveDataLocally(Metric metric, Throwable throwable) {
        return NetworkDataFeignClient.super.saveDataLocally(metric, throwable);
    }
}
