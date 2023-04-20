package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.Basket;
import com.HelloWay.HelloWay.repos.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService {
    @Autowired
    BasketRepository basketRepository ;

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
}
