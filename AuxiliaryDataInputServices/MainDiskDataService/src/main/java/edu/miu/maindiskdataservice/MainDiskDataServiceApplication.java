package edu.miu.maindiskdataservice;

import edu.miu.maindiskdataservice.domain.DiskData;
import edu.miu.maindiskdataservice.domain.Metric;
import edu.miu.maindiskdataservice.service.DiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@EnableDiscoveryClient
public class MainDiskDataServiceApplication /*implements CommandLineRunner*/ {
    @Autowired
    DiskService diskService;

    public static void main(String[] args) {
        SpringApplication.run(MainDiskDataServiceApplication.class, args);
    }

/*    @Override
    public void run(String... args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String apiUrl = "http://localhost:19999/api/v1/data?chart=system.io";
                apiUrl += "&after=-2&format=json&points=1";
                Metric data = diskService.getData(apiUrl);
                if(data != null) diskService.sendData(data);
            }
        }, 0, 1000);
    }*/



}
