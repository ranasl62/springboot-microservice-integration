package miu.edu.swa.pproject.report.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NsiValueDto implements Serializable {
    private String id;
    private Double value;
    private Long timestamp;
    @JsonIgnore
    private String topic;

    public NsiValueDto(String id, Double value, Long timestamp) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
    }
}
