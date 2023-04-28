package miu.edu.swa.pproject.report.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "kafka_topics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaTopic {
    @Id
    private String name;
    private int firstIndex;
    private int secondIndex;
}
