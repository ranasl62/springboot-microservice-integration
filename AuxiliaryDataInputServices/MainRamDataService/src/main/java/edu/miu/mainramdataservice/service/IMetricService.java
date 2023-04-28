package edu.miu.mainramdataservice.service;


import edu.miu.mainramdataservice.domain.Metric;

public interface IMetricService {
    Metric getData(String url);
    void sendData(Metric metric);

    void save(Metric metric);
}
