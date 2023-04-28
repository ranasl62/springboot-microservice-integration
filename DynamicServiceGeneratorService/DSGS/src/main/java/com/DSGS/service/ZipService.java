package com.DSGS.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class ZipService {

    private static final int BUFFER_SIZE = 4096;

    private static final String LOCAL_DIR = System.getProperty("java.io.tmpdir");

    public String unzip(byte[] file) throws IOException {

        String destDirectory = LOCAL_DIR + File.separator + UUID.randomUUID();
        File destDir = new File(destDirectory);
        destDir.mkdir();

        ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(file));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            if(entry.getName().startsWith(".")) {
                entry = zipIn.getNextEntry();
                continue;
            }
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdirs();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
        return destDirectory;
    }

    /**
     * Extracts a zip entry (file entry)
     *
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}
