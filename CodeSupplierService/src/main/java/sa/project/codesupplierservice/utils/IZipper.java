package sa.project.codesupplierservice.utils;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

public interface IZipper {
    void unZip(String zipSrc, String dest) throws IOException;
    void zipDir(String src, String dest) throws IOException;
}
