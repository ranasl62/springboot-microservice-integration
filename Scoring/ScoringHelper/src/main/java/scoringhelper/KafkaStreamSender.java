package scoringhelper;

import scoringhelper.entity.RecordSI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import scoringhelper.repository.RecordSIRepository;

@Service
public class KafkaStreamSender {
    @Value("${kafka.topics.scoring.lu-bound}")
    private String luBoundTopic;
    private int lowerBound = 100;
    private int upperBound = -100;

    @Autowired
    private RecordSIRepository recordSIRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaStreamSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //@KafkaListener(topics = "#{'${si-topics}'.split(',')}")
    // ***************************************************************************You NEED TO CHANGE THIS before pushing github
    @KafkaListener(topicPattern = "SI_.*")
    public void receiveSI(@Payload Integer si){
        System.out.println("Received - SensitivityIndex: "+ si);

        int sIndex = si;

        if (sIndex < lowerBound)
            lowerBound = sIndex;
        if (sIndex > upperBound)
            upperBound = sIndex;

        recordSIRepository.save(new RecordSI(sIndex,lowerBound,upperBound));
        System.out.println("\t\tLower-Bound: "+lowerBound+"   Upper-Bound:"+upperBound);
        kafkaTemplate.send(luBoundTopic, lowerBound+","+upperBound);

    }

}
