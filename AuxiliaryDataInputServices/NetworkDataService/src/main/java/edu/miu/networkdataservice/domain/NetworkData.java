package edu.miu.networkdataservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class NetworkData implements Metric {

    private Computer computer;

    @Id
    private Long time;
    private double received;
    private double sent;
}
