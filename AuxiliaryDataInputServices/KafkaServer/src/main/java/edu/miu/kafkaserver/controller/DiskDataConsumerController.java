package edu.miu.kafkaserver.controller;

import edu.miu.kafkaserver.domain.DiskData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/disk-data")
@CrossOrigin
public class DiskDataConsumerController {
    Map<Long, DiskData> latestDiskData = new HashMap<>();

    @KafkaListener(topics = "${kafka.topics.disk-data}", groupId = "disk-data-consumer-group", containerFactory = "diskDataKafkaListenerContainerFactory")
    public void receiveCpuData(DiskData data) {
        latestDiskData.put(data.getComputer().getId(), data);
        System.out.println("Disk Data received from Kafka");
    }

    @GetMapping("/{computerID}/get-current-data")
    public DiskData sendData(@PathVariable("computerID") Long computerId) {
        if(computerId == null || latestDiskData.isEmpty()) return null;
        return latestDiskData.get(computerId);
    }
}
