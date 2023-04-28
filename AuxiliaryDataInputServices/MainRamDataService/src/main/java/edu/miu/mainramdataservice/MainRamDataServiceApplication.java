package edu.miu.mainramdataservice;

import edu.miu.mainramdataservice.domain.Metric;
import edu.miu.mainramdataservice.domain.RamData;
import edu.miu.mainramdataservice.service.RamDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@EnableDiscoveryClient
public class MainRamDataServiceApplication /*implements CommandLineRunner*/ {
    @Autowired
    RamDataService ramDataService;

    public static void main(String[] args) {
        SpringApplication.run(MainRamDataServiceApplication.class, args);
    }

/*    @Override
    public void run(String... args) throws Exception {


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String apiUrl = "http://localhost:19999/api/v1/data?chart=system.ram";
                apiUrl += "&after=-2&format=json&points=1";
                Metric data = ramDataService.getData(apiUrl);
                if(data != null) ramDataService.sendData(data);
            }
        }, 0, 1000);
    }*/
}
