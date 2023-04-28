package com.DSGS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Set;

@Service
public class SupplierService {
    @Autowired
    SupplierServiceClient supplierServiceClient;

    @Autowired
    ZipService zipService;

    public byte[] getSourceCode(String serviceName, Set<String> topics) {
        try {

            return supplierServiceClient.downloadZip(serviceName, String.join(",", topics)).getBody().getInputStream().readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getDemoSourceCode(String topic) {
        try {
            return new ClassPathResource("demo.zip").getInputStream().readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSourceCodePath(String serviceName, Set<String> topics) {
        try {
            byte[] zipFile  = getSourceCode( serviceName, topics);
            String path = zipService.unzip(zipFile);
            return  path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FeignClient(name = "Code-Supplier-Service")
    public interface SupplierServiceClient {
        @GetMapping(value = "/get-code", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
        ResponseEntity<Resource> downloadZip(@RequestParam("service-name") String serviceName, @RequestParam("topics") String topics);
    }

}