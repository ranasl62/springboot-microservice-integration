package miu.edu.mainnetworkdataservice.repository;



import miu.edu.mainnetworkdataservice.domain.NetworkData;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface NetworkDataRepository extends MongoRepository<NetworkData, Long> {

}
