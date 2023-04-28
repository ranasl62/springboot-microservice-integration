package com.swa.DataInputService.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.swa.DataInputService.model.InputData;

@Repository
public class DataInputRepository {
    private List<InputData> messages;

    public DataInputRepository() {
        this.messages = new ArrayList<>();
    }

    public List<InputData> getMessages() {
        return messages;
    }

    public void save(InputData inputData) {
        this.messages.add(inputData);
    }

    public void clear(){
        this.messages.clear();
    }

    public List<InputData> findAll() {
        return this.messages;
    }
}
