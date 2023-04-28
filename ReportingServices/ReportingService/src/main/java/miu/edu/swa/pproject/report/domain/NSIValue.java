package miu.edu.swa.pproject.report.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "nsi_values")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NSIValue {
    @Id
    private String id;
    private Double value;
    private Long timestamp;
    private KafkaTopic topic;
}
