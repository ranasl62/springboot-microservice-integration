package edu.miu.maindiskdataservice.service;


import edu.miu.maindiskdataservice.domain.Metric;

public interface IMetricService {
    Metric getData(String url);
    void sendData(Metric metric);

    void save(Metric metric);

}
