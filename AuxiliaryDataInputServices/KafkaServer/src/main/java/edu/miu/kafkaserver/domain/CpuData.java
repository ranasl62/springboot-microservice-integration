package edu.miu.kafkaserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class CpuData implements Metric {

    private Computer computer;
    @Id
    private Long time;
    private double user;
    private double nice;
    private double system;

}


