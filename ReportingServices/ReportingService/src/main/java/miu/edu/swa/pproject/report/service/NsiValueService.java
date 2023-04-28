package miu.edu.swa.pproject.report.service;

import miu.edu.swa.pproject.report.domain.NSIValue;
import miu.edu.swa.pproject.report.dto.NsiReportDto;

import java.io.PrintWriter;
import java.util.Set;

public interface NsiValueService {
    NsiReportDto getByTopicName(String topicName);

    void getByTopicName(String topicName, PrintWriter writer);

    Set<NsiReportDto> getByDuration(Long from, Long to);

    void getByDuration(Long from, Long to, PrintWriter writer);

    NsiReportDto getByTopicNameAndDuration(String topicName, Long from, Long to);

    void getByTopicNameAndDuration(String topicName, Long from, Long to, PrintWriter writer);

    NSIValue save(Double value, Long timestamp, String topicName);

    Set<NsiReportDto> getReport(String topicName, Long from, Long to);

    void getCsvReport(String topicName, Long from, Long to, PrintWriter writer);
}
