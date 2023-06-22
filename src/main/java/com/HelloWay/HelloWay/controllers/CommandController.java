package com.HelloWay.HelloWay.controllers;

import com.HelloWay.HelloWay.entities.Command;
import com.HelloWay.HelloWay.entities.User;
import com.HelloWay.HelloWay.services.CommandService;
import com.HelloWay.HelloWay.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commands")
public class CommandController {
    private final CommandService commandService;
    private final UserService userService;

    public CommandController(CommandService commandService, UserService userService) {
        this.commandService = commandService;
        this.userService = userService;
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

    @GetMapping("/for/server/{serverId}")
    public ResponseEntity<?> getServersCommand(@PathVariable long serverId){
        User server = userService.findUserById(serverId);
        if (server == null){
            return ResponseEntity.badRequest().body("server doesn't exist with this id");
        }
        List<Command> commands = commandService.getServerCommands(server);
        return ResponseEntity.ok(commands);
    }
}
