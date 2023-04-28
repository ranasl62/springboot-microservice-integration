package edu.miu.maindiskdataservice.controller;


import edu.miu.maindiskdataservice.domain.DiskData;
import edu.miu.maindiskdataservice.service.IMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/disk-data")
public class DiskDataServiceController {

    @Autowired
    IMetricService diskService;
    @PostMapping("/send")
    public String getDisk(@RequestBody DiskData diskData){
        diskService.sendData(diskData);
        return "Data received and sent to Kafka successfully!";
    }
}
