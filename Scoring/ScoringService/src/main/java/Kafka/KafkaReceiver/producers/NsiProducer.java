package Kafka.KafkaReceiver.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NsiProducer {


    @Autowired
    KafkaTemplate<String, Double> kafkaTemplate;

    @Value("${kafka.topics.scoring.nsi.one}")
    private String nsiTopic;

    public void publishNormalizedSensitivityIndex(Double value){
        System.out.println("Published - ");
        System.out.println("\tNormalizedSensitivityIndex : "+ value + " to Topic : "+ nsiTopic);
        System.out.println();
        kafkaTemplate.send(nsiTopic, value);
    }

}
