package edu.miu.kafkaserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class RamData implements Metric {

    private Computer computer;
    @Id
    private Long time;
    private double active;
    private double wired;
    private double throttled;
    private double compressor;
    private double inactive;
    private double purgeable;
    private double speculative;
    private double free;
}
