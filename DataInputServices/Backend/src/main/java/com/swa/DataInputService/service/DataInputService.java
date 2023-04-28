package com.swa.DataInputService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swa.DataInputService.model.InputData;
import com.swa.DataInputService.repository.DataInputRepository;

@Service
public class DataInputService {
	
    @Autowired
    DataInputRepository repository;
    
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }
    
    public void addMessage(InputData inputData){
        repository.save(inputData);
    }

    public List<InputData> getMessages(){
        return repository.findAll();
    }

    public void clearAllMessages(){
        repository.clear();
    }

    public void setScheduledTasks(boolean enabled){
        this.enabled = enabled;
    }
}
