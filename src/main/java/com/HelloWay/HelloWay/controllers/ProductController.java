package com.HelloWay.HelloWay.controllers;

import com.HelloWay.HelloWay.entities.Image;
import com.HelloWay.HelloWay.entities.Space;
import com.HelloWay.HelloWay.repos.ImageRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.HelloWay.HelloWay.entities.Product;
import com.HelloWay.HelloWay.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    ProductService productService;
    ImageRepository imageRepository;

    @Autowired
    public ProductController(ProductService productService, ImageRepository imageRepository) {
        this.productService = productService;
        this.imageRepository = imageRepository;
    }

    @PostMapping("/add")
    @ResponseBody
    public Optional<Product> addNewProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @JsonIgnore
    @GetMapping("/all")
    @ResponseBody
    public List<Product> allProducts() {
        return productService.findAllProducts();
    }


    @GetMapping("/id/{id}")
    @ResponseBody
    public Product findProductById(@PathVariable("id") long id) {
        return productService.findProductById(id);
    }


    @PutMapping("/update")
    @ResponseBody
    public void updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteProduct(@PathVariable("id") long id) {
        productService.deleteProduct(id);
    }

    @PostMapping("/add/id_categorie/{id_categorie}")
    @ResponseBody
    public Product addNewProductByIdCategorie(@RequestBody Product product, @PathVariable Long id_categorie) {
        return productService.addProductByIdCategorie(product, id_categorie);
    }

    @GetMapping("/all/id_categorie/{id_categorie}")
    @ResponseBody
    public List<Product> getProductsByIDCategory(@PathVariable Long id_categorie) {
        return productService.getProductsByIdCategorie(id_categorie);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<String> addImage(@PathVariable("id") Long id,
                                           @RequestParam("file") MultipartFile file) {
        try {
            Product product = productService.findProductById(id);

            // Create the Image entity and set the reference to the Space entity
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setData(file.getBytes());

            // Persist the Image entity to the database
            imageRepository.save(image);

            return ResponseEntity.ok().body("Image uploaded successfully");
        } catch (IOException ex) {
            throw new RuntimeException("Error uploading file", ex);
        }

    }
}
