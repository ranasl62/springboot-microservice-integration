package edu.miu.kafkaserver.controller;

import edu.miu.kafkaserver.domain.Computer;
import edu.miu.kafkaserver.domain.CpuData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/cpu-data")
@CrossOrigin
public class CpuDataConsumerController {
    Map<Long, CpuData> latestCpuData = new HashMap<>();
    Map<Long, Computer> latestComputers = new HashMap<>();

    @KafkaListener(topics = "${kafka.topics.cpu-data}", groupId = "cpu-data-consumer-group", containerFactory = "cpuDataKafkaListenerContainerFactory")
    public void receiveCpuData(CpuData data) {
        latestCpuData.put(data.getComputer().getId(), data);
        latestComputers.put(data.getComputer().getId(), data.getComputer());
        System.out.println("Cpu Data received from Kafka");
    }

    @GetMapping("/{computerID}/get-current-data")
    public CpuData sendData(@PathVariable("computerID") Long computerId) {
        if(computerId == null || latestCpuData.isEmpty()) return null;
        return latestCpuData.get(computerId);
    }

    @GetMapping("/get-current-computers")
    public List<Computer> getComputers() {
        return latestComputers.values().stream().toList();
    }
}
