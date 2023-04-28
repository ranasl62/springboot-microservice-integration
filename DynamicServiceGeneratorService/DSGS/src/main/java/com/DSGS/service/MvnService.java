package com.DSGS.service;

import org.apache.maven.shared.invoker.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@Component
public class MvnService {

    private static Integer port = 9900;


    @Autowired
    private Environment env;
    private String mvnHome;

    public void executeMvn(String serviceName, String dirPath, Set<String> topics) {
        execute(serviceName, dirPath, Arrays.asList("clean", "spring-boot:run " +
                "-Dspring-boot.run.jvmArguments=\"-Dserver.port=" + port++ + " -Ddemo.topic=" + String.join("-",topics) + "\""));
    }

    public void executeMvnDemo(String dirPath, String topic) {
        execute("Demo-Service", dirPath, Arrays.asList("clean", "spring-boot:run " +
                "-Dspring-boot.run.jvmArguments=\"-Dserver.port=" + port++ + " -Ddemo.topic=" + topic + "\""));
    }

    private void execute(String serviceName, String dirPath, List<String> goals) {
        try {
            int p = port - 1;
            InvocationRequest request = new DefaultInvocationRequest();
            request.setBaseDirectory(new File(dirPath));
            request.setGoals(goals);

            String os = System.getProperty("os.name");

            if (os.contains("Mac")) {
                mvnHome = env.getProperty("mvn.home.mac");
            } else {
                mvnHome = env.getProperty("mvn.home.windows");

            }
            request.setMavenHome(new File(mvnHome));
            request.setBatchMode(false);

            request.setOutputHandler(new InvocationOutputHandler() {
                @Override
                public void consumeLine(String s) throws IOException {
                }
            });

            Invoker invoker = new DefaultInvoker();
            new Thread(() -> {
                try {
                    InvocationResult result = invoker.execute(request);
                    if(result.getExecutionException() != null) {
                        result.getExecutionException().printStackTrace();
                    }
                } catch (MavenInvocationException e) {
                    e.printStackTrace();
                }
            }).start();
            System.out.println("Service: " + serviceName + " running in port: " + p);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}