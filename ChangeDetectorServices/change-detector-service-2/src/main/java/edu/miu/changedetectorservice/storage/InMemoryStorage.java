package edu.miu.changedetectorservice.storage;

import java.util.LinkedList;
import java.util.Queue;

public class InMemoryStorage {
    public static final Queue<Long> VALUE_HOLDING = new LinkedList<>();
    public static Double PREVIOUS_SD = 0D;

    private InMemoryStorage(){}

}
