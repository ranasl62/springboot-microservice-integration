package miu.edu.swa.pproject.report.service;

import miu.edu.swa.pproject.report.domain.KafkaTopic;

import java.util.Set;

public interface KafkaTopicService {
    Set<String> getAllTopics();

    Set<KafkaTopic> getAllTopicsWithIndices();
}
