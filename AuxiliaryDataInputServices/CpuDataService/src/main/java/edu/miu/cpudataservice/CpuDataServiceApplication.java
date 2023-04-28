package edu.miu.cpudataservice;

import edu.miu.cpudataservice.domain.CpuData;
import edu.miu.cpudataservice.domain.Metric;
import edu.miu.cpudataservice.service.CpuService;
import edu.miu.cpudataservice.service.IMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CpuDataServiceApplication implements CommandLineRunner {
    @Autowired
    CpuService cpuService;
    public static void main(String[] args) {
        SpringApplication.run(CpuDataServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String apiUrl = "http://localhost:19999/api/v1/data?chart=system.cpu";
                apiUrl += "&after=-2&format=json&points=1";
                Metric data = cpuService.getData(apiUrl);
                if(data != null) cpuService.sendData(data);
            }
        }, 0, 1000);
    }
}
