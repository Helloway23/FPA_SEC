package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.Basket;
import com.HelloWay.HelloWay.entities.Command;
import com.HelloWay.HelloWay.exception.ResourceNotFoundException;
import com.HelloWay.HelloWay.repos.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService {
    @Autowired
    BasketRepository basketRepository ;

    @Autowired
    CommandService commandService;

    public List<Basket> findAllBaskets() {
        return basketRepository.findAll();
    }

    public Basket updateBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    public Basket findBasketById(Long id) {
        return basketRepository.findById(id)
                .orElse(null);
    }

    public void deleteBasket(Long id) {
        basketRepository.deleteById(id);
    }

    public Basket addNewBasket(Basket basket) {

        return basketRepository.save(basket);
    }

    public void assignCommandToBasket(Long basketId, Command command) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(()->new ResourceNotFoundException("basket not found"));
        basket.setCommand(command);
        command.setBasket(basketRepository.save(basket));
        commandService.updateCommand(command);
        basketRepository.save(basket);
    }
}
