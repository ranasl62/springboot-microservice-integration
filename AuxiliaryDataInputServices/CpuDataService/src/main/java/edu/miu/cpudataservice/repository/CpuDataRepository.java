package edu.miu.cpudataservice.repository;

import edu.miu.cpudataservice.domain.CpuData;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CpuDataRepository extends MongoRepository<CpuData, Long> {

}
