package edu.miu.mainramdataservice.controller;

import edu.miu.mainramdataservice.domain.RamData;
import edu.miu.mainramdataservice.service.IMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ram-data")
public class RamDataController {
    @Autowired
    IMetricService ramService;
    @PostMapping("/send")
    public String getRam(@RequestBody RamData ramData){
        ramService.sendData(ramData);
        return "Data received and sent to Kafka successfully!";
    }
}
