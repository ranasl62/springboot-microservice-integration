package com.swa.DataInputService.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.swa.DataInputService.model.InputData;

@Service
public class Sender {
    @Autowired
    KafkaTemplate<String, Long> kafkaTemplate;
    @Autowired
    DataInputService service;
    
    Map<String, Long> messages = new HashMap<>();

    public void send(String topic, Long number) {
        kafkaTemplate.send(topic, number);
        System.out.println("Sending Topic:  " + topic + ", Value: " + number);
        service.addMessage(new InputData(topic, number));
    }
}