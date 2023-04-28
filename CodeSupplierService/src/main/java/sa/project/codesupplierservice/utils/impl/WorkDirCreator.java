package sa.project.codesupplierservice.utils.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sa.project.codesupplierservice.utils.IWorkDirCreator;

import java.io.File;

@Component
public class WorkDirCreator implements IWorkDirCreator {

    @Value("${zipDir}")
    private String zipDir;

    @Value("${codeDir}")
    private String codeDir;

    @Value("${zipOutDir}")
    private String zipOutDir;

    private void createDirIfNotExists(String dir) {
        try {
            File file = new File(dir);
            if (!file.exists())
                file.mkdirs();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to create the directory");
        }
    }

    @Override
    public void createWorkDir() {
        createDirIfNotExists(zipDir);
        createDirIfNotExists(codeDir);
        createDirIfNotExists(zipOutDir);
    }
}
