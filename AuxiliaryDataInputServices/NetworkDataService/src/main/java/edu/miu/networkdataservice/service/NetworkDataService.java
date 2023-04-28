package edu.miu.networkdataservice.service;


import edu.miu.networkdataservice.domain.Computer;
import edu.miu.networkdataservice.domain.Metric;
import edu.miu.networkdataservice.domain.NetworkData;
import edu.miu.networkdataservice.repository.NetworkDataRepository;
import edu.miu.networkdataservice.utils.NetworkDataFeignClient;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NetworkDataService implements IMetricService {
    @Value("${computer.id}")
    private Long computerId;

    @Value("${computer.name}")
    private String computerName;
    @Autowired
    NetworkDataRepository networkDataRepository;

    @Qualifier("edu.miu.networkdataservice.utils.NetworkDataFeignClient")
    @Autowired private NetworkDataFeignClient networkDataFeignClient;

    @Override
    public Metric getData(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            JSONParser parser = new JSONParser(response);
            List<Object> data = (List<Object>) parser.parseObject().get("data");
            List<Object> values = (List<Object>) data.get(0);

            Computer computer = new Computer(computerId, computerName);
            NetworkData networkData = new NetworkData(computer,
                    Long.parseLong(values.get(0).toString()),
                    Double.parseDouble(values.get(1).toString()),
                    Double.parseDouble(values.get(2).toString()));

            return networkData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void sendData(Metric metric) {
        String response = networkDataFeignClient.sendRemoteData(metric);
        System.out.println(response);
    }
    @Override
    public void save(Metric metric) {
        networkDataRepository.save((NetworkData) metric);
    }
}

