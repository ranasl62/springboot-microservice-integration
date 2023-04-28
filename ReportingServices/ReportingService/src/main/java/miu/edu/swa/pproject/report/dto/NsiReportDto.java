package miu.edu.swa.pproject.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NsiReportDto implements Serializable {
    private String topicName;
    private Set<NsiValueDto> data;
}
