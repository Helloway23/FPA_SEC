package com.HelloWay.HelloWay.controllers;

import com.HelloWay.HelloWay.entities.Basket;
import com.HelloWay.HelloWay.entities.Command;
import com.HelloWay.HelloWay.entities.Product;
import com.HelloWay.HelloWay.services.BasketProductService;
import com.HelloWay.HelloWay.services.BasketService;
import com.HelloWay.HelloWay.services.CommandService;
import com.HelloWay.HelloWay.services.ProductService;
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
    @Autowired
    public BasketController(BasketService basketService, BasketProductService basketProductService, ProductService productService, CommandService commandService) {
        this.basketService = basketService;
        this.basketProductService = basketProductService;
        this.productService = productService;
        this.commandService = commandService;
    }

    @PostMapping("/add")
    @ResponseBody
    public Basket addNewBasket(@RequestBody Basket basket) {
        return basketService.addNewBasket(basket);
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
    public void addProductToBasket(@PathVariable long basketId, @PathVariable long productId, @PathVariable int quantity) {
        Basket basket = basketService.findBasketById(basketId);
        Product product = productService.findProductById(productId);
        basketProductService.addProductToBasket(basket, product,quantity);
    }

    @PostMapping("/delete/product/{productId}/from_basket/{basketId}")
    public void deleteProductFromBasket(@PathVariable long basketId, @PathVariable long productId) {
        basketProductService.deleteProductFromBasket(basketId, productId);
    }

    @PostMapping("/{basketId}/commands")
    public ResponseEntity<Command> createCommand(@PathVariable Long basketId) {
        Basket basket = basketService.findBasketById(basketId);
        Command command = commandService.createCommand(new Command());
        basketService.assignCommandToBasket(basketId, command);
        return ResponseEntity.ok(command);
    }

}
