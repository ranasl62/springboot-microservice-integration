package miu.edu.mainnetworkdataservice.service;


import miu.edu.mainnetworkdataservice.domain.Metric;

public interface IMetricService {
    Metric getData(String url);
    void sendData(Metric metric);

    void save(Metric metric);

}
