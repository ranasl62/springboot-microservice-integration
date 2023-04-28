package edu.miu.maincpudataservice;

import edu.miu.maincpudataservice.domain.CpuData;
import edu.miu.maincpudataservice.domain.Metric;
import edu.miu.maincpudataservice.service.IMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@EnableDiscoveryClient
public class MainCpuDataServiceApplication /*implements CommandLineRunner*/ {
    @Autowired
    IMetricService cpuServices;

    public static void main(String[] args) {
        SpringApplication.run(MainCpuDataServiceApplication.class, args);
    }
/*    @Override
    public void run(String... args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String apiUrl = "http://localhost:19999/api/v1/data?chart=system.cpu";
                apiUrl += "&after=-2&format=json&points=1";
                Metric data = cpuServices.getData(apiUrl);
                if(data != null) cpuServices.sendData(data);
            }
        }, 0, 2000);

    }*/

}
