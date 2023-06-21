package com.HelloWay.HelloWay.controllers;

import com.HelloWay.HelloWay.entities.*;
import com.HelloWay.HelloWay.repos.UserRepository;
import com.HelloWay.HelloWay.services.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {

    BasketService basketService;
    ProductService productService;
    BasketProductService basketProductService;

    private final CommandService commandService;

    private  UserService userService;

    private BoardService boardService ;
    @Autowired
    public BasketController(UserService userService, BasketService basketService, BasketProductService basketProductService, ProductService productService, CommandService commandService, BoardService boardService) {
        this.basketService = basketService;
        this.basketProductService = basketProductService;
        this.productService = productService;
        this.commandService = commandService;
        this.boardService = boardService;
        this.userService = userService;
    }

    @PostMapping("/add")
    @ResponseBody
    public Basket addNewBasket(@RequestBody Basket basket) {
        return basketService.addNewBasket(basket);
    }


    @PostMapping("/tables/{boardId}/baskets")
    public ResponseEntity<?> addBasketToBoard(@PathVariable Long boardId, @RequestBody Basket basket) {
        Board table = boardService.findBoardById(boardId);
        if (table == null) {
            return ResponseEntity.notFound().build();
        }

        basket.setBoard(table);
        basketService.addNewBasket(basket);

        return ResponseEntity.ok("Basket added to the table successfully");
    }


    @JsonIgnore
    @GetMapping("/all")
    @ResponseBody
    public List<Basket> allBaskets(){
        return basketService.findAllBaskets();
    }


    @GetMapping("/id/{id}")
    @ResponseBody
    public Basket findBasketById(@PathVariable("id") long id){
        return basketService.findBasketById(id);
    }


    @PutMapping("/update")
    @ResponseBody
    public void updateBasket(@RequestBody Basket basket){
        basketService.updateBasket(basket); }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteBasket(@PathVariable("id") long id){
        basketService.deleteBasket(id); }

    @PostMapping("/add/product/{productId}/to_basket/{basketId}/quantity/{quantity}")
    public ResponseEntity<?> addProductToBasket(@PathVariable long basketId, @PathVariable long productId, @PathVariable int quantity) {
        Basket basket = basketService.findBasketById(basketId);
        if (basket == null){
            return ResponseEntity.badRequest().body("basket doesn't exist");
        }
        Product product = productService.findProductById(productId);
        if (product == null){
            return ResponseEntity.badRequest().body("product doesn't exist");
        }
        basketProductService.addProductToBasket(basket, product,quantity);
        return ResponseEntity.ok().body("product added with success");
    }

    @PostMapping("/delete/product/{productId}/from_basket/{basketId}")
    public ResponseEntity<?> deleteProductFromBasket(@PathVariable long basketId, @PathVariable long productId) {
        basketProductService.deleteProductFromBasket(basketId, productId);
        return ResponseEntity.ok().body("product deleted with success");
    }

    @PostMapping("/{basketId}/commands")
    public ResponseEntity<Command> createCommand(@PathVariable Long basketId) {
        Basket basket = basketService.findBasketById(basketId);
        Command command = commandService.createCommand(new Command());
        basketService.assignCommandToBasket(basketId, command);
        return ResponseEntity.ok(command);
    }

    @PostMapping("/{basketId}/commands/add")
    public ResponseEntity<Command> createCommandWithServer(@PathVariable Long basketId) {
        Basket basket = basketService.findBasketById(basketId);
        Board board = basket.getBoard();
        List<User> servers = board.getZone().getServers();
        int indexOfTheLastServer = servers.indexOf(commandService.getLastServerWithBoardIdForCommand().get(board.getIdTable()));
        User currentAvailableServer = servers.get(indexOfTheLastServer + 1);
        Command command = commandService.createCommand(new Command());
        basketService.assignCommandToBasket(basketId, command);
        commandService.setServerForCommand(command.getIdCommand(), currentAvailableServer);
        commandService.updateCommand(command);
        return ResponseEntity.ok(command);
    }

}
