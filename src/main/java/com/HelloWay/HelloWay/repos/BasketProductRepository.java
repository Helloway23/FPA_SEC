package com.HelloWay.HelloWay.repos;

import com.HelloWay.HelloWay.entities.BasketProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, Long> {
}
