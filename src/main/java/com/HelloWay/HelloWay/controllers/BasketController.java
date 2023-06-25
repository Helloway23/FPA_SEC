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
        Basket basketObject = basketService.addNewBasket(basket);

        return ResponseEntity.ok(basketObject);
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
        return ResponseEntity.ok().body(basketProductService.getProductsQuantityByBasketId(basketId));
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

    @PostMapping("/{basketId}/commands/add/user/{userId}")
    public ResponseEntity<Command> createCommandWithServer(@PathVariable Long basketId, @PathVariable long userId) {
        Basket basket = basketService.findBasketById(basketId);
        Board board = basket.getBoard();
        User user = userService.findUserById(userId);
        List<User> servers = board.getZone().getServers();
        User currentAvailableServer = servers.get(0);
        if (commandService.getLastServerWithBoardIdForCommand().get(board.getZone().getIdZone().toString()) != null){
        User lastServer = userService.findUserById(Long.parseLong(commandService.getLastServerWithBoardIdForCommand().get(board.getZone().getIdZone().toString())));
        int indexOfTheLastServer = servers.indexOf(lastServer);
        if (indexOfTheLastServer != servers.size() - 1) {
            currentAvailableServer = servers.get(indexOfTheLastServer + 1);
        }
        }

        Command command = commandService.createCommand(new Command());
        basketService.assignCommandToBasket(basketId, command);
        commandService.setServerForCommand(command.getIdCommand(), currentAvailableServer);
        command.setUser(user);
        commandService.updateCommand(command);
        return ResponseEntity.ok(command);
    }

    //Get products by id basket : done
    @GetMapping("/products/by_basket/{basketId}")
    public ResponseEntity<?> getProductsByIdBasket(@PathVariable long basketId){
        Basket basket = basketService.findBasketById(basketId);
        if (basket == null){
            return  ResponseEntity.badRequest().body("basket doesn't exist with this id");
        }
        List<Product> products = basketProductService.getProductsByBasketId(basketId);
        return ResponseEntity.ok(products);

    }

    @GetMapping("/by_table/{tableId}")
    public ResponseEntity<?> getLatestBasketByIdTable(@PathVariable long tableId){
        Board board = boardService.findBoardById(tableId);
        if (board == null){
            return  ResponseEntity.badRequest().body("board doesn't exist with this id");
        }
        List<Basket> baskets = board.getBaskets();
        Basket basket = baskets.get(baskets.size() - 1);

        return ResponseEntity.ok(basket);
    }
}
