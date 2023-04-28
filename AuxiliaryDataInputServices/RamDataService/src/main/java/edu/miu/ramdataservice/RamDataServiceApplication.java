package edu.miu.ramdataservice;

import edu.miu.ramdataservice.domain.Metric;
import edu.miu.ramdataservice.domain.RamData;
import edu.miu.ramdataservice.service.RamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class RamDataServiceApplication implements CommandLineRunner {
    @Autowired
    RamService ramService;

    public static void main(String[] args) {
        SpringApplication.run(RamDataServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String apiUrl = "http://localhost:19999/api/v1/data?chart=system.ram";
                apiUrl += "&after=-2&format=json&points=1";
                Metric data = ramService.getData(apiUrl);
                if(data != null) ramService.sendData(data);
            }
        }, 0, 1000);
    }

}
