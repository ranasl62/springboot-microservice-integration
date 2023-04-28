package sa.project.codesupplierservice.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sa.project.codesupplierservice.service.ICodeSupplierService;
import sa.project.codesupplierservice.utils.IZipper;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Service
@RequiredArgsConstructor
public class RepoCodeSupplierService implements ICodeSupplierService {

    private final IZipper zipper;

    @Value("${css.cds.src}")
    private String cdsSrc;

    @Value("${css.ss.src}")
    private String ssSrc;

    @Value("${css.rs.src}")
    private String rsSrc;

    @Value("${zipDir}")
    private String zipDir;

    @Value("${codeDir}")
    private String codeDir;

    @Value("${zipOutDir}")
    private String zipOutDir;

    @Value("${css.cds.projectName}")
    private String cdsProjectName;

    @Value("${css.ss.projectName}")
    private String ssProjectName;

    private final static String APPLICATION_YML_REL_PATH = "/src/main/resources/application.yml";
    private final static int FILE_READ_CHAR_BUFFER_SIZE = 10;

    private void downloadZip(String serviceName, String dest) throws IOException {
        String srcUrl = "";
        if (serviceName.equals("cds"))
            srcUrl = cdsSrc;
        else if (serviceName.equals("ss"))
            srcUrl = ssSrc;
        else
            srcUrl = rsSrc;

        try(FileOutputStream fileOutputStream = new FileOutputStream(dest)) {
            ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(srcUrl).openStream());
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        }
    }

    private File getApplicationPropertiesFile(File workDir, String projectName) {
        System.out.println(workDir.getAbsolutePath());
        File applicationProperties = new File(workDir.getAbsolutePath() + "/" + projectName + APPLICATION_YML_REL_PATH);
        if (!applicationProperties.exists()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Properties file not found!");
        }
        return applicationProperties;
    }

    private String getStringFromFile(File file) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(file));) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] charArrBuffer = new char[FILE_READ_CHAR_BUFFER_SIZE];
            while (reader.read(charArrBuffer) != -1) {
                stringBuilder.append(new String(charArrBuffer));
                charArrBuffer = new char[FILE_READ_CHAR_BUFFER_SIZE];
            }
            return stringBuilder.toString();
        }
    }

    private void writeContentToFile(File file, String content) throws IOException {
        String refinedContent = content.replace('\0', ' '); // Bypass the null character
        try(FileWriter fw = new FileWriter(file);BufferedWriter writer = new BufferedWriter(fw);) {
            writer.write(refinedContent);
        }
    }

    @Override
    public void getCDSCode(String topic, File workDir) throws IOException {
        File applicationPropertiesFile = getApplicationPropertiesFile(workDir, cdsProjectName);
        String propertiesString = getStringFromFile(applicationPropertiesFile);
        String newPropertiesString = propertiesString.replace("$$input$$", topic);
        String[] topicParts = topic.split("_");
        int dsTopicIndex = Integer.parseInt(topicParts[topicParts.length - 1]);
        String outputTopic = "CDS_" + dsTopicIndex;
        newPropertiesString = newPropertiesString.replace("$$output$$", outputTopic);
        writeContentToFile(applicationPropertiesFile, newPropertiesString);
    }

    @Override
    public void getSSCode(String topic1, String topic2, File workDir) throws IOException {
        File applicationPropertiesFile = getApplicationPropertiesFile(workDir, ssProjectName);
        String propertiesString = getStringFromFile(applicationPropertiesFile);
        String newPropertiesString = propertiesString.replace("$$inputX$$", topic1);
        newPropertiesString = newPropertiesString.replace("$$inputY$$", topic2);
        String[] topicParts1 = topic1.split("_");
        String[] topicParts2 = topic2.split("_");
        int cds1TopicIndex = Integer.parseInt(topicParts1[topicParts1.length - 1]);
        int cds2TopicIndex = Integer.parseInt(topicParts2[topicParts2.length - 1]);
        String siOutputTopic = "SI_" + cds1TopicIndex + "_" + cds2TopicIndex;
        String nsiOutputTopic = "NSI_" + cds1TopicIndex + "_" + cds2TopicIndex;
        newPropertiesString = newPropertiesString.replace("$$sioutput$$", siOutputTopic);
        newPropertiesString = newPropertiesString.replace("$$nioutput$$", nsiOutputTopic);
        writeContentToFile(applicationPropertiesFile, newPropertiesString);
    }

    @Override
    public void getRSCode(String topics, File workDir) throws IOException {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Service not available for Reporting Service!");
    }

    private void validateServiceName(String serviceName) {
        if (!serviceName.matches("^(cds)|(ss)|(rs)$"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Allowed service names: cds, ss, rs!");
    }

    private void validateTopics(String[] topics) {
        boolean isValid = true;
        if (topics.length == 0) {
            isValid = false;
        }
        else {
            for (String topic : topics) {
                if (!topic.matches("^(DS_\\d+)|(CDS_\\d+)|(SI_\\d+_\\d+)|(NSI_\\d+_\\d+)$")) {
                    isValid = false;
                    break;
                }
            }
        }

        if (!isValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Topics!");
        }
    }

    @Override
    public File getCode(String serviceName, String topics, ServletOutputStream servletOutputStream) throws IOException {
        validateServiceName(serviceName);
        String[] topicArr = topics.split(" *, *");
        validateTopics(topicArr);
        long currentTimeStamp = System.currentTimeMillis();
        String zipSrc = zipDir + "/" + serviceName + "_" + currentTimeStamp + ".zip";
        String extractSrc = codeDir + "/" + serviceName + "_" + currentTimeStamp;
        String exportSrc = zipOutDir + "/" + serviceName + "_" + currentTimeStamp + ".zip";
        File extractDir = new File(extractSrc);
        if (!extractDir.mkdir())
            throw new IOException("Failed to create directory: " + extractDir);

        downloadZip(serviceName, zipSrc);
        zipper.unZip(zipSrc, extractSrc);
        switch (serviceName) {
            case "cds" -> getCDSCode(topics, extractDir);
            case "ss" -> getSSCode(topicArr[0], topicArr[1], extractDir);
            case "rs" -> getRSCode(topics, extractDir);
        };
        File[] filesInExtractDir = extractDir.listFiles();
        if (filesInExtractDir == null || filesInExtractDir.length == 0)
            throw new IOException("Empty Extract Dir!");
        zipper.zipDir(filesInExtractDir[0].getAbsolutePath(), exportSrc);

        return new File(exportSrc);
    }
}
