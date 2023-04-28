package com.DSGS.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KafkaTopicDetectorService {
    private static Set<String> oldTopics = new HashSet<>();
    private static Set<String> cdsTopics = new HashSet<>();
    private final AdminClient adminClient;
    @Autowired
    private ZipService zipService;
    @Autowired
    private MvnService mvnService;
    @Autowired
    private SupplierService supplierService;

    @Autowired
    public KafkaTopicDetectorService(KafkaProperties kafkaProperties) {
        Properties props = new Properties();
        props.putAll(kafkaProperties.buildAdminProperties());
        this.adminClient = AdminClient.create(props);
    }

    @PostConstruct
    public void start() throws ExecutionException, InterruptedException {
     adminClient.listTopics().names().get().forEach(topic -> {
            adminClient.deleteTopics(Collections.singleton(topic));
        });
    }

    @Scheduled(fixedDelayString = "${spring.task.scheduling.fixed-delay}", initialDelayString = "10000")
    public void detectNewTopics() {
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("Detect new topic:  Date is " + localDateTime + " , Topic is : " + "topic");
        Set<String> allTopics = getAllTopics();
        Set<String> newTopics = new HashSet<>(allTopics);
        newTopics.removeAll(oldTopics);
        oldTopics = allTopics;

        Set<String> newCdsTopics = newTopics.stream()
                .filter(topic -> StringUtils.startsWithIgnoreCase(topic, "CDS_"))
                .collect(Collectors.toSet());

        Set<String> dsServicesToGenerate = newTopics.stream()
                .filter(topic -> StringUtils.startsWithIgnoreCase(topic, "DS_"))
                .collect(Collectors.toSet());

        Set<Map.Entry<String, String>> ssServicesToGenerate = generateSsServiceNames(cdsTopics, newCdsTopics);

        Set<String> rsToGenerate = generateRsServiceNames(cdsTopics, newCdsTopics);
        cdsTopics.addAll(newCdsTopics);

        dsServicesToGenerate.stream()
                .forEach(t -> generateService("CDS", Set.of(t)));

        generateSsServices(ssServicesToGenerate);

        rsToGenerate.stream()
                .forEach(t -> generateService("RS", Set.of(t)));

//        newTopics.stream().filter(t -> StringUtils.startsWithIgnoreCase(t, "Demo_"))
//                .forEach(t -> {
//                    generateDemoService(t);
//                });
    }

    private void generateSsServices(Set<Map.Entry<String, String>> ssServicesToGenerate) {
        for (Map.Entry<String, String> pair : ssServicesToGenerate) {
            generateService("SS", Set.of(pair.getKey(), pair.getValue()));
        }
    }

    private void generateService(String serviceName, Set<String> topics) {
        byte[] zipFile = supplierService.getSourceCode(serviceName, topics);
        try {
            String dest = zipService.unzip(zipFile);
            mvnService.executeMvn(serviceName, dest, topics);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void generateDemoService(String topic) {
        System.out.println("Start building & deploying Demo service");
        byte[] zipFile = supplierService.getDemoSourceCode(topic);
        try {
            String dest = zipService.unzip(zipFile);
            mvnService.executeMvnDemo(dest, topic);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Set<String> generateRsServiceNames(Set<String> cdsTopics, Set<String> newCdsTopics) {
        Set<String> names = new HashSet<>();

        for (String s1 : newCdsTopics) {
            for (String s2 : cdsTopics) {
                names.add("NSI" + s2.substring(s2.lastIndexOf("_")) + s1.substring(s1.lastIndexOf("_")));
            }
        }

        return names;
    }

    private Set<Map.Entry<String, String>> generateSsServiceNames(Set<String> ssTopics, Set<String> newSsTopics) {

        Set<Map.Entry<String, String>> servicesToGenerate = new HashSet<>();
        for (String s1 : newSsTopics) {
            for (String s2 : ssTopics) {
                servicesToGenerate.add(new AbstractMap.SimpleImmutableEntry<>(s2, s1));
            }
        }
        return servicesToGenerate;
    }


    private Set<String> getAllTopics() {
        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(true);
        KafkaFuture<Set<String>> future = adminClient.listTopics(options).names();
        try {
            return future.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            return Collections.emptySet();
        }
    }

    @PreDestroy
    public void stop() {
        adminClient.close();
    }
}
