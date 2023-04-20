package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.CommandProduct;
import com.HelloWay.HelloWay.repos.CommandProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandProductService {
    @Autowired
    CommandProductRepository commandProductRepository;

    public List<CommandProduct> findAllCommandProducts() {
        return commandProductRepository.findAll();
    }

    public CommandProduct updateCommandProduct(CommandProduct commandProduct) {
        return commandProductRepository.save(commandProduct);
    }

    public CommandProduct findCommandProductById(Long id) {
        return commandProductRepository.findById(id)
                .orElse(null);
    }

    public void deleteCommandProduct(Long id) {
        commandProductRepository.deleteById(id);
    }
}
