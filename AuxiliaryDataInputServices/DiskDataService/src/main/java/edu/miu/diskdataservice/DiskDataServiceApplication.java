package edu.miu.diskdataservice;

import edu.miu.diskdataservice.domain.DiskData;
import edu.miu.diskdataservice.domain.Metric;
import edu.miu.diskdataservice.service.DiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class DiskDataServiceApplication implements CommandLineRunner {
    @Autowired
    DiskService diskService;

    public static void main(String[] args) {
        SpringApplication.run(DiskDataServiceApplication.class, args);
    }

    @Override
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
    }
}
