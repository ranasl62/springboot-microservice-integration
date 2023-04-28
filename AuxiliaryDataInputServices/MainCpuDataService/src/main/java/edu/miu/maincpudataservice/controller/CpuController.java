package edu.miu.maincpudataservice.controller;

import edu.miu.maincpudataservice.domain.CpuData;
import edu.miu.maincpudataservice.service.IMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cpu-data")
public class CpuController {
    @Autowired
    IMetricService cpuService;
    @PostMapping("/send")
    public String getCpu(@RequestBody CpuData cpuData) {
        cpuService.sendData(cpuData);
        return "Data received and sent to Kafka successfully!";
    }
}
