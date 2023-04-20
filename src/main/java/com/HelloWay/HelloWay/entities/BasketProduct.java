package com.HelloWay.HelloWay.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketProduct {

    @EmbeddedId
    private BasketProductKey id;

    @ManyToOne
    @MapsId("idBasket")
    @JoinColumn(name = "id_basket")
    private Basket basket;

    @ManyToOne
    @MapsId("idProduct")
    @JoinColumn(name = "id_product")
    private Product product;

    private int quantity;
}
