package miu.edu.mainnetworkdataservice.service;





import miu.edu.mainnetworkdataservice.domain.Metric;
import miu.edu.mainnetworkdataservice.domain.NetworkData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaProducerService {
    @Value("${kafka.topics.network-data}")
    private String DISK_TOPIC;

    @Autowired
    private KafkaTemplate<String, Metric> kafkaTemplate;

    public void send(Metric networkData){
        kafkaTemplate.send(DISK_TOPIC, UUID.randomUUID().toString(),networkData);
    }
}
