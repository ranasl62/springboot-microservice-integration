package sa.project.codesupplierservice.utils.impl;

import org.springframework.stereotype.Component;
import sa.project.codesupplierservice.utils.ICodeReadWrite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileReadWrite implements ICodeReadWrite {

    @Override
    public String readFromSource(String sourceName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(sourceName)));
    }

    @Override
    public String writeProcessedCode(String code, String filename) {
        return null;
    }
}
