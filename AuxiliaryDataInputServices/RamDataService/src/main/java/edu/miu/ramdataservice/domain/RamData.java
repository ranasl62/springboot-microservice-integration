package edu.miu.ramdataservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RamData implements Metric {

    private Computer computer;
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
