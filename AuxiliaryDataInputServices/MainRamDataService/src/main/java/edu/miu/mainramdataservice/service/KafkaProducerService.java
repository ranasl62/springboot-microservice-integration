package edu.miu.mainramdataservice.service;





import edu.miu.mainramdataservice.domain.Metric;
import edu.miu.mainramdataservice.domain.RamData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaProducerService {
    @Value("${kafka.topics.ram-data}")
    private String DISK_TOPIC;

    @Autowired
    private KafkaTemplate<String, Metric> kafkaTemplate;

    public void send(Metric ramData){
        kafkaTemplate.send(DISK_TOPIC, UUID.randomUUID().toString(),ramData);
    }
}
