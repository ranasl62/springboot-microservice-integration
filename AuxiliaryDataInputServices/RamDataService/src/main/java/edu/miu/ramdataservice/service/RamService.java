package edu.miu.ramdataservice.service;

import edu.miu.ramdataservice.domain.Computer;
import edu.miu.ramdataservice.domain.Metric;
import edu.miu.ramdataservice.domain.RamData;
import edu.miu.ramdataservice.repository.RamDataRepository;
import edu.miu.ramdataservice.utils.RamDataFeignClient;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RamService implements IMetricService {
    @Value("${computer.id}")
    private Long computerId;

    @Value("${computer.name}")
    private String computerName;

    @Autowired
    private RamDataRepository ramDataRepository;
    @Qualifier("edu.miu.ramdataservice.utils.RamDataFeignClient")
    @Autowired private RamDataFeignClient ramDataFeignClient;
    @Override
    public Metric getData(String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            JSONParser parser = new JSONParser(response);
            List<Object> data = (List<Object>) parser.parseObject().get("data");
            List<Object> values = (List<Object>) data.get(0);

            Computer computer = new Computer(computerId, computerName);
            RamData ramData = new RamData(computer,
                    Long.parseLong(values.get(0).toString()),
                    Double.parseDouble(values.get(1).toString()),
                    Double.parseDouble(values.get(2).toString()),
                    Double.parseDouble(values.get(3).toString()),
                    Double.parseDouble(values.get(4).toString()),
                    0, 0, 0, 0); // For Windows

            if(values.size() >= 9) {
                ramData = new RamData(computer,
                        Long.parseLong(values.get(0).toString()),
                        Double.parseDouble(values.get(1).toString()),
                        Double.parseDouble(values.get(2).toString()),
                        Double.parseDouble(values.get(3).toString()),
                        Double.parseDouble(values.get(4).toString()),
                        Double.parseDouble(values.get(5).toString()),
                        Double.parseDouble(values.get(6).toString()),
                        Double.parseDouble(values.get(7).toString()),
                        Double.parseDouble(values.get(8).toString())); // For MacOs
            }

            return ramData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void sendData(Metric metric) {
        String response = ramDataFeignClient.sendRemoteData(metric);
        System.out.println(response);
    }

    @Override
    public void save(Metric metric) {
        ramDataRepository.save((RamData) metric);
    }
}
