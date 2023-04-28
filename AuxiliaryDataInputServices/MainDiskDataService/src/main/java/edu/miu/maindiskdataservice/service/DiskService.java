package edu.miu.maindiskdataservice.service;



import edu.miu.maindiskdataservice.domain.Computer;
import edu.miu.maindiskdataservice.domain.DiskData;
import edu.miu.maindiskdataservice.domain.Metric;
import edu.miu.maindiskdataservice.repository.DiskDataRepository;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DiskService implements IMetricService {
    @Value("${computer.id}")
    private Long computerId;

    @Value("${computer.name}")
    private String computerName;

    @Autowired
    DiskDataRepository diskDataRepository;

    @Autowired
    KafkaProducerService kafkaProducerService;


    @Override
    public Metric getData(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            JSONParser parser = new JSONParser(response);
            List<Object> data = (List<Object>) parser.parseObject().get("data");
            List<Object> values = (List<Object>) data.get(0);

            Computer computer = new Computer(computerId, computerName);
            DiskData cpuData = new DiskData(computer,
                    Long.parseLong(values.get(0).toString()),
                    Double.parseDouble(values.get(1).toString()),
                    Double.parseDouble(values.get(2).toString())
                    );

            return cpuData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void sendData(Metric metric) {
        try {
            kafkaProducerService.send(metric);
            System.out.println("Data sent to Kafka!");
        } catch (Exception e) {
            save(metric);
            System.out.println("Data saved locally!");
            System.out.println("Data could not be sent to Kafka!");
            System.out.println("Cause: " + e.getMessage());
        }

    }
    @Override
    public void save(Metric metric) {
        diskDataRepository.save((DiskData) metric);
    }
}

