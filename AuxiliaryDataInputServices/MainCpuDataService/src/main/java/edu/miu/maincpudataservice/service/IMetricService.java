package edu.miu.maincpudataservice.service;


import edu.miu.maincpudataservice.domain.Metric;
import org.springframework.stereotype.Service;

public interface IMetricService {
    Metric getData(String url);
    void sendData(Metric metric);

    void save(Metric metric);
}
