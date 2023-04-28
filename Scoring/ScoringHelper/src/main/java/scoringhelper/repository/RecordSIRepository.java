package scoringhelper.repository;

import scoringhelper.entity.RecordSI;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordSIRepository extends MongoRepository<RecordSI, String> {
}
