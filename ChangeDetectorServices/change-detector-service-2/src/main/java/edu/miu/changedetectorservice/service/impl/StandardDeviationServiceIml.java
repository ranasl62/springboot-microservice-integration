package edu.miu.changedetectorservice.service.impl;

import edu.miu.changedetectorservice.service.StandardDeviationService;
import edu.miu.changedetectorservice.storage.InMemoryStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class StandardDeviationServiceIml implements StandardDeviationService {

    @Value("${kafka.topics.cds.two}")
    private String CHANGE_DETECTED_TOPIC;
    @Value("${kafka.topics.cds.key}")
    private String TOPIC_DATA_KEY_FOR_WINDOWED;
    private final KafkaTemplate<String, Integer> kafkaTemplate;

    @Override
    public void findDataChange(Long x) {

        if (InMemoryStorage.VALUE_HOLDING.size() < 20) {
            InMemoryStorage.VALUE_HOLDING.add(x);
        } else {
            InMemoryStorage.VALUE_HOLDING.add(x);
            InMemoryStorage.VALUE_HOLDING.remove();
        }

        double sd = calculateStandardDeviation();
        double rate = calculateDataChangeRate(sd);
        InMemoryStorage.PREVIOUS_SD = sd;
        int result = 0;
        try {
            // rate matrix in percentage
            if (rate > 50) {
                result = 1;
            }
            kafkaTemplate.send(CHANGE_DETECTED_TOPIC, TOPIC_DATA_KEY_FOR_WINDOWED, result);
        } catch (Exception ex) {
            log.info("Error: ", ex);
        }
        log.info("=====> data change result: {}", result);
    }

    private Double calculateDataChangeRate(double sd) {
        double diff = Math.abs(sd - InMemoryStorage.PREVIOUS_SD);
        return (diff / InMemoryStorage.PREVIOUS_SD) * 100;
    }

    private Double calculateStandardDeviation() {
        Integer average;
        double sum = 0;
        for (Long x : InMemoryStorage.VALUE_HOLDING) {
            sum += x;
        }
        average = (int) (sum / InMemoryStorage.VALUE_HOLDING.size());
        sum = 0;
        for (Long x : InMemoryStorage.VALUE_HOLDING) {
            sum += Math.pow((x - average), 2);
        }
        return Math.sqrt(sum / InMemoryStorage.VALUE_HOLDING.size());
    }
}
