package edu.miu.maindiskdataservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class DiskData implements Metric {
    private Computer computer;
    @Id
    private Long time;
    private double in;
    private double out;
}
