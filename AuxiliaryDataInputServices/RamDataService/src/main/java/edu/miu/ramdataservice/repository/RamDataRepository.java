package edu.miu.ramdataservice.repository;

import edu.miu.ramdataservice.domain.RamData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RamDataRepository extends MongoRepository<RamData, Long> {
}
