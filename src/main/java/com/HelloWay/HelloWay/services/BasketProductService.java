package com.HelloWay.HelloWay.services;


import com.HelloWay.HelloWay.entities.Basket;
import com.HelloWay.HelloWay.entities.BasketProduct;
import com.HelloWay.HelloWay.entities.BasketProductKey;
import com.HelloWay.HelloWay.entities.Product;
import com.HelloWay.HelloWay.repos.BasketProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasketProductService {
    @Autowired
    BasketProductRepository basketProductRepository;
    @Autowired
    BasketService basketService ;
    @Autowired
    ProductService productService ;
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

    public void addProductToBasket(Basket basket, Product product, int quantity) {
        basketProductRepository.save(new BasketProduct(
                new BasketProductKey(basket.getId_basket(),product.getIdProduct()),
                basket,
                product,
                quantity
        ));
    }


    public List<BasketProduct> getBasketProductsByBasketId(Long id) {
        Basket basket = basketService.findBasketById(id);
        return new ArrayList<>(basketProductRepository.findAllByBasket(basket));
    }

    public List<BasketProduct> getBasketProductsByProductId(Long id) {
        Product product = productService.findProductById(id);
        return new ArrayList<>(basketProductRepository.findAllByProduct(product));
    }

    public void deleteProductFromBasket(Long bid, Long pid) {
        BasketProduct basketProduct =basketProductRepository.findById_IdBasketAndId_IdProduct(bid, pid);
        basketProductRepository.delete(basketProduct);
    }


    public List<Product> getProductsByBasketId(Long id) {
        Basket basket = basketService.findBasketById(id);
        List<BasketProduct> basketProducts = new ArrayList<>();
               basketProducts =  basketProductRepository.findAllByBasket(basket);
        List<Product> products = new ArrayList<>();
        for (BasketProduct basketProduct : basketProducts){
            products.add(basketProduct.getProduct());
        }
        return products;
    }


}
