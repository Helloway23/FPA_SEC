package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.*;
import com.HelloWay.HelloWay.repos.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.HelloWay.HelloWay.entities.Status.CONFIRMED;
import static com.HelloWay.HelloWay.entities.Status.REFUSED;

@Service
public class CommandService {

     private  Map<String, String> lastServerWithBoardIdForCommand = new HashMap<>();


     @Autowired
     UserService userService ;

     @Autowired
    CommandRepository commandRepository ;

    @Autowired
    BasketService basketService;

    @Autowired
    BasketProductService basketProductService;

    public List<Command> findAllCommands() {
        return commandRepository.findAll();
    }

    public Command updateCommand(Command command) {
        return commandRepository.save(command);
    }
    public Command createCommand(Command command) {
        return commandRepository.save(command);
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
        Zone zone = command.getBasket().getBoard().getZone();
        List<User> servers = new ArrayList<>();
        servers = zone.getServers();
        List<Command> commands = new ArrayList<>();
        if (servers.contains(server)){
            command.setServer(server);
            commandRepository.save(command);
            commands = server.getCommands();
            commands.add(command);
            userService.updateUser(server);
            lastServerWithBoardIdForCommand.put(command.getBasket().getBoard().getIdTable().toString(),server.getId().toString());
        }

    }

    public double CalculateSum(Command command) {
        double result = 0 ;
        Basket basket = command.getBasket();
        Map<Product,Integer> products_Quantity = basketProductService.getProducts_PriceByBasketId(basket.getId_basket());
        for (Product product : products_Quantity.keySet()){
            result += product.getPrice() * products_Quantity.get(product);
        }
        return  result;
    }

    public Map<String, String> getLastServerWithBoardIdForCommand() {
        return lastServerWithBoardIdForCommand;
    }

    public void setLastServerWithBoardIdForCommand(Map<String, String> lastServerWithBoardIdForCommand) {
        this.lastServerWithBoardIdForCommand = lastServerWithBoardIdForCommand;
    }
}
