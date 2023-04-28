package edu.miu.maincpudataservice.repository;

import edu.miu.maincpudataservice.domain.CpuData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CpuRepository extends MongoRepository<CpuData,Long> {
}
