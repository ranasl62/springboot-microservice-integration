package com.swa.DataInputService.component;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.swa.DataInputService.service.DataInputService;
import com.swa.DataInputService.service.Sender;

@Component
public class DataInputScheduledTasks {
	
    @Autowired
    Sender sender;

    @Autowired
    DataInputService service;
    @Value("${kafka.topics.dis.prefix}")
    private String SERVICE_TOPIC_PREFIX;
    @Value("${app.properties.upper-bound}")
    private long UPPER_BOUND;
    
    private static final int DSI_1 = 1;
    private static final int DSI_2 = 2;
    private static final int DSI_3 = 3;
    private static final int DSI_4 = 4;
    private static final int DSI_5 = 5;
    private static final int DSI_6 = 6;
    private static final int DSI_7 = 7;
    private static final int DSI_8 = 8;
    private static final int DSI_9 = 9;
    private static final int DSI_10 = 10;
    private static final int DSI_11 = 11;
    private static final int DSI_12 = 12;
    private static final int DSI_13 = 13;
    private static final int DSI_14 = 14;
    private static final int DSI_15 = 15;
    private static final int DSI_16 = 16;
    private static final int DSI_17 = 17;
    private static final int DSI_18 = 18;
    private static final int DSI_19 = 19;
    private static final int DSI_20 = 20;

    private long getRandomNumber(long upperBound){
        Random rand = new Random();
        return rand.nextLong(upperBound);
    }

    private long getRandomAddition(long upperBound){
        return getRandomNumber(upperBound) + getRandomNumber(upperBound);
    }

    private long getRandomSubtraction(long upperBound){
        return getRandomNumber(upperBound) - getRandomNumber(upperBound);
    }

    private long getRandomMultiplication(long upperBound){
        return getRandomNumber(upperBound) * getRandomNumber(upperBound);
    }

    private long getRandomDivision(long upperBound){
        long dividedBy = getRandomNumber(upperBound);
        if(dividedBy == 0) return 0;
        return getRandomNumber(upperBound) / dividedBy;
    }

    @Scheduled(fixedRate = 1000)
    public void sendData1() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_1, getRandomNumber(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 1500)
    public void sendData2() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_2, getRandomAddition(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 2000)
    public void sendData3() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_3, getRandomSubtraction(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 2500)
    public void sendData4() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_4, getRandomMultiplication(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 3000)
    public void sendData5() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_5, getRandomDivision(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 3500)
    public void sendData6() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_6, getRandomNumber(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 4000)
    public void sendData7() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_7, getRandomAddition(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 4500)
    public void sendData8() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_8, getRandomSubtraction(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 5000)
    public void sendData9() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_9, getRandomMultiplication(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 5500)
    public void sendData10() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_10, getRandomDivision(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 6000)
    public void sendData11() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_11, getRandomNumber(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 6500)
    public void sendData12() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_12, getRandomAddition(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 7000)
    public void sendData13() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_13, getRandomSubtraction(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 7500)
    public void sendData14() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_14, getRandomMultiplication(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 8000)
    public void sendData15() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_15, getRandomDivision(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 8500)
    public void sendData16() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_16, getRandomNumber(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 9000)
    public void sendData17() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_17, getRandomAddition(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 9500)
    public void sendData18() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_18, getRandomSubtraction(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 10000)
    public void sendData19() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_19, getRandomMultiplication(UPPER_BOUND));
        }
    }

    @Scheduled(fixedRate = 10500)
    public void sendData20() {
        if (service.isEnabled()) {
            sender.send(SERVICE_TOPIC_PREFIX + DSI_20, getRandomDivision(UPPER_BOUND));
        }
    }

}
