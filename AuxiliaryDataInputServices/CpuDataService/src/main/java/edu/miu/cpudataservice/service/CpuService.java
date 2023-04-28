package edu.miu.cpudataservice.service;

import edu.miu.cpudataservice.domain.Computer;
import edu.miu.cpudataservice.domain.CpuData;
import edu.miu.cpudataservice.domain.Metric;
import edu.miu.cpudataservice.repository.CpuDataRepository;
import edu.miu.cpudataservice.utils.CpuDataFeignClient;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CpuService implements IMetricService {
    @Value("${computer.id}")
    private Long computerId;

    @Value("${computer.name}")
    private String computerName;

    @Autowired
    CpuDataRepository cpuDataRepository;

    @Qualifier("edu.miu.cpudataservice.utils.CpuDataFeignClient")
    @Autowired private CpuDataFeignClient cpuDataFeignClient;

    @Override
    public Metric getData(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            JSONParser parser = new JSONParser(response);
            List<Object> data = (List<Object>) parser.parseObject().get("data");
            List<Object> values = (List<Object>) data.get(0);

            Computer computer = new Computer(computerId, computerName);
            CpuData cpuData = new CpuData(computer,
                    Long.parseLong(values.get(0).toString()),
                    Double.parseDouble(values.get(1).toString()),
                    Double.parseDouble(values.get(2).toString()),
                    Double.parseDouble(values.get(3).toString())); // For MacOS

            if(values.size() >= 9) {
                cpuData = new CpuData(computer,
                        Long.parseLong(values.get(0).toString()),
                        Double.parseDouble(values.get(6).toString()),
                        Double.parseDouble(values.get(7).toString()),
                        Double.parseDouble(values.get(8).toString()));
            } // For Windows

            return cpuData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void sendData(Metric metric) {
        String response = cpuDataFeignClient.sendRemoteData(metric);
        System.out.println(response);
    }
    @Override
    public void save(Metric metric) {
        cpuDataRepository.save((CpuData) metric);
    }
}

