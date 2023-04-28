package edu.miu.maincpudataservice.service;



import edu.miu.maincpudataservice.domain.CpuData;
import edu.miu.maincpudataservice.domain.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaProducerService {
    @Value("${kafka.topics.cpu-data}")
    private String CPU_TOPIC;

    @Autowired
    private KafkaTemplate<String, Metric> kafkaTemplate;

    public void send(Metric cpuData){
        kafkaTemplate.send(CPU_TOPIC, UUID.randomUUID().toString(),cpuData);
    }
}
