package com.HelloWay.HelloWay.repos;

import com.HelloWay.HelloWay.entities.CommandProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandProductRepository extends JpaRepository<CommandProduct, Long> {
}
