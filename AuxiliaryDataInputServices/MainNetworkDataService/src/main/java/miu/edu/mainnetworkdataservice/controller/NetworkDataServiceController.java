package miu.edu.mainnetworkdataservice.controller;

import miu.edu.mainnetworkdataservice.domain.NetworkData;
import miu.edu.mainnetworkdataservice.service.IMetricService;
import miu.edu.mainnetworkdataservice.service.NetworkDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/network-data")
public class NetworkDataServiceController {

    @Autowired
    IMetricService networkService;
    @PostMapping("/send")
    public String getRam(@RequestBody NetworkData networkData){
        networkService.sendData(networkData);
        return "Data received and sent to Kafka successfully!";
    }
}
