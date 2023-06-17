package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.Basket;
import com.HelloWay.HelloWay.entities.Command;
import com.HelloWay.HelloWay.entities.User;
import com.HelloWay.HelloWay.repos.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.HelloWay.HelloWay.entities.Status.CONFIRMED;
import static com.HelloWay.HelloWay.entities.Status.REFUSED;

@Service
public class CommandService {
    @Autowired
    CommandRepository commandRepository ;

    @Autowired
    BasketService basketService;

    public List<Command> findAllCommands() {
        return commandRepository.findAll();
    }

    public Command updateCommand(Command event) {
        return commandRepository.save(event);
    }
    public Command createCommand(Command event) {
        return commandRepository.save(event);
    }

    public Command findCommandById(Long id) {
        return commandRepository.findById(id)
                .orElse(null);
    }

    public void deleteCommand(Long id) {
        commandRepository.deleteById(id);
    }

    public void acceptCommand(Long commandId) {
        Command command = findCommandById(commandId);
        // Logic to accept the command and process it accordingly
        // For example, update the status of the command or trigger external operations
        command.setStatus(CONFIRMED);
        commandRepository.save(command);
    }

    public void refuseCommand(Long commandId) {
        Command command = findCommandById(commandId);
        // Logic to refuse the command
        // For example, update the status of the command or trigger external operations
        command.setStatus(REFUSED);
        commandRepository.save(command);
    }

    public Command createCommandForBasket(Long basketId, Command command) {
        Basket basket = basketService.findBasketById(basketId);
        command.setBasket(basket);
        return commandRepository.save(command);
    }

    public void setServerForCommand(Long commandId, User server) {
        Command command = findCommandById(commandId);
        command.setServer(server);
        commandRepository.save(command);
    }
}
