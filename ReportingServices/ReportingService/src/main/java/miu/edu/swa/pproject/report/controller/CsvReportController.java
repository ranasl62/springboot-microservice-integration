package miu.edu.swa.pproject.report.controller;

import io.swagger.v3.oas.annotations.Operation;
import miu.edu.swa.pproject.report.service.NsiValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@CrossOrigin
public class CsvReportController {

    private final NsiValueService nsiValueService;

    @Autowired
    public CsvReportController(NsiValueService nsiValueService) {
        this.nsiValueService = nsiValueService;
    }

    @Operation(summary = "Get NSI CSV report by topic name and time period")
    @GetMapping("/report/csv")
    @ResponseStatus(HttpStatus.OK)
    public void getCsvReport(@RequestParam(value = "topicName", required = false) String topicName,
                             @RequestParam(value = "from", required = false) Long from,
                             @RequestParam(value = "to", required = false) Long to,
                             HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"nsi_report.csv\"");
        servletResponse.addHeader("Access-Control-Allow-Origin", "*");
        nsiValueService.getCsvReport(topicName, from, to, servletResponse.getWriter());
    }

    @Operation(summary = "Get NSI CSV report by time period")
    @GetMapping("/csv/time")
    @ResponseStatus(HttpStatus.OK)
    public void getByTime(@RequestParam("from") Long from, @RequestParam("to") Long to, HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"nsi.csv\"");
        nsiValueService.getByDuration(from, to, servletResponse.getWriter());
    }

    @Operation(summary = "Get NSI csv report by topic name")
    @GetMapping("/csv/topic/{topicName}")
    @ResponseStatus(HttpStatus.OK)
    public void getByTopicName(@PathVariable("topicName") String topicName, HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"nsi.csv\"");
        nsiValueService.getByTopicName(topicName,servletResponse.getWriter());
    }

    @Operation(summary = "Get NSI csv report by topic name and time period")
    @GetMapping("/csv/topic/{topicName}/time")
    @ResponseStatus(HttpStatus.OK)
    public void getByTimeAndTopic(@PathVariable("topicName") String topicName,
                                          @RequestParam("from") Long from, @RequestParam("to") Long to,
                                          HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"nsi.csv\"");
        servletResponse.addHeader("Access-Control-Allow-Origin", "*");
        servletResponse.addHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, PATCH, OPTIONS");
        servletResponse.addHeader("Access-Control-Max-Age", "3600");
        servletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        servletResponse.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept");
        nsiValueService.getByTopicNameAndDuration(topicName, from, to,servletResponse.getWriter());
    }
}
