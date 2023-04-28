package com.swa.DataInputService.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swa.DataInputService.model.InputData;
import com.swa.DataInputService.service.DataInputService;

@RestController
@RequestMapping("/datainput/messages")
@CrossOrigin
public class DataInputController {
	
    @Autowired
    DataInputService service;

    @GetMapping
    public ResponseEntity<List<InputData>> getMessages(){
        return new ResponseEntity<>(service.getMessages(), HttpStatus.OK);
    }
    
	@GetMapping("/{bytopic}")
    public List<InputData> getMessage(@PathVariable String bytopic){
		List<InputData> messages =  service.getMessages();
        return messages.stream().filter(m -> m.getTopic().equals(bytopic)).collect(Collectors.toList());
    }

    @PostMapping("/stop")
    public ResponseEntity<?> setScheduledTasksFalse(){
        service.setScheduledTasks(false);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/start")
    public ResponseEntity<?> setScheduledTasksTrue(){
        service.setScheduledTasks(true);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/clear")
    public ResponseEntity<?> clearAllMessages(){
        service.clearAllMessages();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
