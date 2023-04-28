package scoringhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import scoringhelper.repository.RecordSIRepository;

@SpringBootApplication
@EnableMongoRepositories
@EnableKafka
@EnableDiscoveryClient
public class ScoringHelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoringHelperApplication.class, args);
	}

}
