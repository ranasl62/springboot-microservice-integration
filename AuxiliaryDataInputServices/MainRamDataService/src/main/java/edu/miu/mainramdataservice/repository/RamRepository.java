package edu.miu.mainramdataservice.repository;


import edu.miu.mainramdataservice.domain.RamData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RamRepository extends MongoRepository<RamData,Long> {
}
