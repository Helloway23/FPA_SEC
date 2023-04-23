package com.HelloWay.HelloWay.controllers;

import com.HelloWay.HelloWay.entities.Basket;
import com.HelloWay.HelloWay.services.BasketService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {

    BasketService basketService;
    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
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
}
