package Kafka.KafkaReceiver.processor;

import Kafka.KafkaReceiver.producers.NsiProducer;
import Kafka.KafkaReceiver.producers.SiProducer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;


@Component
public class ScoreServiceProcessor {
    @Value("${kafka.topics.cds.one}")
    private String inputX;
    @Value("${" +
            "}")
    private String inputY;
    @Autowired
    NsiProducer nsiProducer;
    @Autowired
    SiProducer siProducer;
//    @Value("${spring.kafka.window-size}")
    private int dynamicWindowSize = 5;

    private final Duration windowSize = Duration.ofSeconds(dynamicWindowSize); //create a window size duration

    private Sensitivity sensitivity = new Sensitivity();



    @Autowired
    public void processChanges(StreamsBuilder streamsBuilder){
        // Create a windowed stream of CDS_x and CDS_y topics

        KTable<Windowed<String>, String> windowedStreamX = streamsBuilder.stream(inputX, Consumed.with(Serdes.String(), Serdes.String()))
                .groupByKey()
                .windowedBy(TimeWindows.of(windowSize))
                .reduce((v1, v2) -> {
                    int val =  Integer.parseInt(v1) + Integer.parseInt(v2);
                    return val + "";
                }).toStream()
                //.peek((k,v)-> System.out.println("kx: "+k+" vx:"+ v))
                .toTable();

        KTable<Windowed<String>, String> windowedStreamY = streamsBuilder.stream(inputY, Consumed.with(Serdes.String(), Serdes.String()))
                .groupByKey()
                .windowedBy(TimeWindows.of(windowSize))
                .reduce((v1, v2) -> {
                    int val =  Integer.parseInt(v1) + Integer.parseInt(v2);
                    return val + "";
                }).toStream()
                //.peek((k,v)-> System.out.println("ky: "+k+" vy:"+ v))
                .toTable();

        windowedStreamX
                .join(windowedStreamY, (v1, v2) -> new Integer[]{Integer.parseInt(v1), Integer.parseInt(v2)})
                .toStream()
                //.peek((k, v)-> System.out.println("wi: "+ Arrays.toString(v) + "K: "+ k.toString()))
                // 3rd stage
                .map((k, v) -> {
                    int interaction = 0;
                    if (v[0] > 0 && v[1] > 0 || v[0]== 0 && v[1]== 0) {
                        interaction = 1;
                    } else {
                        interaction = -1;
                    }
                    System.out.println("Window based - WeightedInteraction: "+ interaction);
                    int newSensitivityIndex = sensitivity.getIndex() + interaction;
                    sensitivity.setIndex(newSensitivityIndex);
                    return KeyValue.pair(k, newSensitivityIndex);
                })
                .map((k, v) -> {
                    //System.out.println("SensitivityIndex_SI : " + v + " Key: "+ k.key());
                    siProducer.publishSensitivityIndex(k.key(), v);
                    return new KeyValue<>(k.key(), v );
                });

    }

    @KafkaListener(topics = "${kafka.topics.scoring.lu-bound}", groupId = "bound")
    public void boundListener(@Payload String bounds){
        String[] boundValues = bounds.split(",");
        int lowerBound = Integer.parseInt(boundValues[0]);
        int upperBound = Integer.parseInt(boundValues[1]);
        System.out.println("Consumed from - Helper Service");
        System.out.println("\t Lower Bound: "+lowerBound+" Upper Bound: "+upperBound);
        double nsi = 0;
        if (upperBound != lowerBound)
            nsi = (double) (sensitivity.getIndex() - lowerBound)/(upperBound - lowerBound);
        nsiProducer.publishNormalizedSensitivityIndex(nsi);
    }
}
