package edu.miu.ramdataservice.service;

import edu.miu.ramdataservice.domain.Metric;

public interface IMetricService {
    Metric getData(String url);
    void sendData(Metric metric);

    void save(Metric metric);

}
