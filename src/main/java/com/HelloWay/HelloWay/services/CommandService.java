package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.Command;
import com.HelloWay.HelloWay.repos.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandService {
    @Autowired
    CommandRepository commandRepository ;

    public List<Command> findAllCommands() {
        return commandRepository.findAll();
    }

    public Command updateCommand(Command event) {
        return commandRepository.save(event);
    }

    public Command findCommandById(Long id) {
        return commandRepository.findById(id)
                .orElse(null);
    }

    public void deleteCommand(Long id) {
        commandRepository.deleteById(id);
    }
}
