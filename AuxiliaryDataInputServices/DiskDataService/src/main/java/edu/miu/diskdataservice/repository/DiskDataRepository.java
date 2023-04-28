package edu.miu.diskdataservice.repository;


import edu.miu.diskdataservice.domain.DiskData;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface DiskDataRepository extends MongoRepository<DiskData, Long> {

}
