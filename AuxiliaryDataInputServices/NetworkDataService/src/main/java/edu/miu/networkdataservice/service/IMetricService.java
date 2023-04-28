package edu.miu.networkdataservice.service;

import edu.miu.networkdataservice.domain.Metric;

public interface IMetricService {
    Metric getData(String url);
    void sendData(Metric metric);

    void save(Metric metric);

}
