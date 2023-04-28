package miu.edu.swa.pproject.report.repository;

import miu.edu.swa.pproject.report.domain.KafkaTopic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KafkaTopicRepository extends MongoRepository<KafkaTopic, String> {
}
