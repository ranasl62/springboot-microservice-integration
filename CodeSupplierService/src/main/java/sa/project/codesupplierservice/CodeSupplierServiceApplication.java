package sa.project.codesupplierservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import sa.project.codesupplierservice.utils.impl.WorkDirCreator;

@SpringBootApplication
@EnableDiscoveryClient
public class CodeSupplierServiceApplication implements CommandLineRunner {

    @Autowired
    private WorkDirCreator workDirCreator;

    public static void main(String[] args) {
        SpringApplication.run(CodeSupplierServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        workDirCreator.createWorkDir();
    }
}
