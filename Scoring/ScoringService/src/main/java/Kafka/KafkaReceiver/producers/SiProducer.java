package Kafka.KafkaReceiver.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SiProducer {
    @Autowired
    KafkaTemplate<String, Integer> kafkaTemplate;
    @Value("${kafka.topics.scoring.si.one}")
    private String siTopic;
    public void publishSensitivityIndex(String key, int value){
        System.out.println("Published - ");
        System.out.println("\tSensitivityIndex: "+ value + " to Topic: "+ siTopic);
        kafkaTemplate.send(siTopic, key,value);
    }

}
