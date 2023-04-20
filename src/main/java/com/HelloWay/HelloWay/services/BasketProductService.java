package com.HelloWay.HelloWay.services;


import com.HelloWay.HelloWay.entities.BasketProduct;
import com.HelloWay.HelloWay.repos.BasketProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketProductService {
    @Autowired
    BasketProductRepository basketProductRepository;
    public List<BasketProduct> findAllBasketProducts() {
        return basketProductRepository.findAll();
    }

    public BasketProduct updateBasketProduct(BasketProduct basketProduct) {
        return basketProductRepository.save(basketProduct);
    }

    public BasketProduct findBasketProductById(Long id) {
        return basketProductRepository.findById(id)
                .orElse(null);
    }

    public void deleteBasketProduct(Long id) {
        basketProductRepository.deleteById(id);
    }
}
