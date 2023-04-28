package com.DSGS.controller;


import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class SimulationController {

    private AdminClient adminClient;

    @Autowired
    KafkaTemplate<String, String> template;

    @Autowired
    public SimulationController(KafkaProperties kafkaProperties) {
        Properties props = new Properties();
        props.putAll(kafkaProperties.buildAdminProperties());
        this.adminClient = AdminClient.create(props);
    }

    @GetMapping(value = "/zip", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadZip(@RequestParam("service-name") String serviceName, @RequestParam("topics") String topics) {
        try {
            File file = new ClassPathResource("demo.zip").getFile();
            Resource resource = new FileSystemResource(file);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @GetMapping("/addTopic/{topicName}")
    public ResponseEntity<String> addTopic(@PathVariable String topicName) {
        adminClient.createTopics(List.of(TopicBuilder.name(topicName)
                .replicas(1)
                .partitions(1)
                .build()));
        return ResponseEntity.ok(" Topic created successfully.");
    }

    @DeleteMapping("/deleteAllTopics")
    public ResponseEntity<String> deleteAllTopics() throws ExecutionException, InterruptedException {
        adminClient.listTopics().names().get().forEach(topic -> {
            adminClient.deleteTopics(Collections.singleton(topic));
        });
        return ResponseEntity.ok("All topics deleted successfully.");
    }

    @GetMapping("/add")
    public ResponseEntity<String> writeMsg(@RequestParam("topic") String topic, @RequestParam("msg") String msg) {
        template.send(topic, msg);
        return ResponseEntity.ok("Send msg");
    }
}