package edu.miu.maindiskdataservice.repository;



import edu.miu.maindiskdataservice.domain.DiskData;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface DiskDataRepository extends MongoRepository<DiskData, Long> {

}
