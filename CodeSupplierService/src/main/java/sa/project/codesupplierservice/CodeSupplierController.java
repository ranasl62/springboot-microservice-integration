package sa.project.codesupplierservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sa.project.codesupplierservice.service.ICodeSupplierService;

import java.time.LocalDateTime;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CodeSupplierController {

    private final ICodeSupplierService codeSupplierService;

    /**
     * Example:
     * Change Detector Service: GET /get-code?service-name=cds&topics=t1
     * Scoring Service: GET /get-code?service-name=ss&topics=t1,t2
     * Reporting Service: GET /get-code?service-name=rs&topics=t1,t2,t3,t4
     * @param serviceName
     * @param topics
     * @return
     */
    @GetMapping("/get-code")
    public ResponseEntity<Resource> getCode(@RequestParam("service-name") String serviceName, @RequestParam String topics, HttpServletResponse response) {
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            File zipFile = codeSupplierService.getCode(serviceName, topics, response.getOutputStream());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(zipFile));
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Disposition", "attachment;filename="+serviceName+".zip");
            log.info(currentTime + "\tService Name: " + serviceName + ", Topics: " + topics);
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(zipFile.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
