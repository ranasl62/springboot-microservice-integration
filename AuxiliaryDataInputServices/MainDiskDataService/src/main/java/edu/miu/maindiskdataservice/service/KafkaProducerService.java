package edu.miu.maindiskdataservice.service;




import edu.miu.maindiskdataservice.domain.DiskData;
import edu.miu.maindiskdataservice.domain.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaProducerService {
    @Value("${kafka.topics.disk-data}")
    private String DISK_TOPIC;

    @Autowired
    private KafkaTemplate<String, Metric> kafkaTemplate;

    public void send(Metric diskData){
        kafkaTemplate.send(DISK_TOPIC, UUID.randomUUID().toString(), diskData);
    }
}
