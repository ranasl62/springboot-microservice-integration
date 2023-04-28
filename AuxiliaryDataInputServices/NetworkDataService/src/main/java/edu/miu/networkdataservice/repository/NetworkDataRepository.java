package edu.miu.networkdataservice.repository;


import edu.miu.networkdataservice.domain.NetworkData;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface NetworkDataRepository extends MongoRepository<NetworkData, Long> {

}
