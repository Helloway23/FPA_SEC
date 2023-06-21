package com.HelloWay.HelloWay.controllers;

import com.HelloWay.HelloWay.services.CommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands")
public class CommandController {
    private final CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
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


}
