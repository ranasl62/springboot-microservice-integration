package miu.edu.swa.pproject.report.repository;

import miu.edu.swa.pproject.report.domain.KafkaTopic;
import miu.edu.swa.pproject.report.domain.NSIValue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NsiValueRepository extends MongoRepository<NSIValue, String> {

    Set<NSIValue> findByTopic(KafkaTopic topic);

    Set<NSIValue> findByTopicName(String topicName);

    Set<NSIValue> findByTimestampBetween(Long from, Long to);

    Set<NSIValue> findByTopicNameAndTimestampBetween(String topicName, Long from, Long to);
}
