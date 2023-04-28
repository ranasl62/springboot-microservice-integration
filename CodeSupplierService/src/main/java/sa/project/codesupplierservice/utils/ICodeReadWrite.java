package sa.project.codesupplierservice.utils;

import java.io.IOException;

public interface ICodeReadWrite {
    String readFromSource(String sourceName) throws IOException;
    String writeProcessedCode(String code, String filename);
}
