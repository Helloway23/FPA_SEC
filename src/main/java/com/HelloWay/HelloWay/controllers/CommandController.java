package com.HelloWay.HelloWay.controllers;

import com.HelloWay.HelloWay.Security.Jwt.CustomSessionRegistry;
import com.HelloWay.HelloWay.entities.Basket;
import com.HelloWay.HelloWay.entities.Board;
import com.HelloWay.HelloWay.entities.Command;
import com.HelloWay.HelloWay.entities.User;
import com.HelloWay.HelloWay.payload.response.Command_NumTableDTO;
import com.HelloWay.HelloWay.services.BasketService;
import com.HelloWay.HelloWay.services.CommandService;
import com.HelloWay.HelloWay.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/commands")
public class CommandController {
    private final CommandService commandService;
    private final UserService userService;

    private final BasketService basketService;

    private final CustomSessionRegistry customSessionRegistry;

    public CommandController(CommandService commandService, UserService userService, BasketService basketService, CustomSessionRegistry customSessionRegistry) {
        this.commandService = commandService;
        this.userService = userService;
        this.basketService = basketService;
        this.customSessionRegistry = customSessionRegistry;
    }

    @PostMapping("/{commandId}/accept")
    public ResponseEntity<String> acceptCommand(@PathVariable Long commandId) {
        commandService.acceptCommand(commandId);
        return ResponseEntity.ok("Command accepted");
    }


    @PostMapping("/{commandId}/refuse")
    public ResponseEntity<String> refuseCommand(@PathVariable Long commandId) {
        commandService.refuseCommand(commandId);
        return ResponseEntity.ok("Command refused");
    }

    // there we must remove users from the table, and disconnect the guests :: ?? TODo :: done
    // we must remove they from the list of connected users in this table :: done
    // then we must implement the creation of a new basket with this fucking table :: done
    @PostMapping("/{commandId}/pay")
    public ResponseEntity<String> payCommand(@PathVariable Long commandId) {
        Command command = commandService.findCommandById(commandId);
        if (command == null){
            return ResponseEntity.badRequest().body("command not found");
        }
        Board board = command.getBasket().getBoard();
        commandService.payCommand(commandId);
        customSessionRegistry.removeUsersWhenCommandIsPayed(board.getIdTable().toString());
        Basket basket = new Basket();
        basket.setBoard(board);
        basketService.addNewBasket(basket);
        return ResponseEntity.ok("Command payed");
    }

   /*
   then we must create the command with the server and the user then the basket id
    with getCommandsOfTheServer
    the server can see all his commands and can accept or refuse then will be payed
    so there we will play with the status also server must list the commands with status
     only not yet in the other side the creation of a command will be by default not yet
    */


    @GetMapping("/calculate/sum/{commandId}")
    public ResponseEntity<?> getSumOfCommand(@PathVariable long commandId){
        Command command = commandService.findCommandById(commandId);
        if (command == null){
            return ResponseEntity.badRequest().body("command doesn't exist with this id");
        }
        double sum = commandService.CalculateSum(command);
        return ResponseEntity.ok(sum);
    }

    // output : command : num table  : list<Command,numTable> :: Done
    // wissal will test (my dataBase effected) TODO ::
    @GetMapping("/for/server/{serverId}")
    public ResponseEntity<?> getServersCommand(@PathVariable long serverId){
        User server = userService.findUserById(serverId);
        if (server == null){
            return ResponseEntity.badRequest().body("server doesn't exist with this id");
        }
        List<Command_NumTableDTO> commandNumTableDTOS = new ArrayList<>();
        List<Command> commands = commandService.getServerCommands(server);
        for (Command command : commands){
            commandNumTableDTOS.add(new Command_NumTableDTO(command, command.getBasket().getBoard().getNumTable()));
        }
        return ResponseEntity.ok(commandNumTableDTOS);
    }

    // we will use this after update the basket (after adding a product to the basket)
    @PutMapping("/update/{commandId}/basket/{basketId}")
    public ResponseEntity<?> updateCommand(@PathVariable long basketId, @PathVariable long commandId){
        Command command = commandService.findCommandById(commandId);
        if (command == null){
            return ResponseEntity.badRequest().body("command doesn't exist with this id");
        }
        Basket basket = basketService.findBasketById(basketId);
        if (basket == null){
            return ResponseEntity.badRequest().body("basket doesn't exist with id");
        }
        command.setBasket(basket);
        commandService.updateCommand(command);
        return ResponseEntity.ok("command updated");
    }

}
