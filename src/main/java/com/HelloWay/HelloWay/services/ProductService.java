package com.HelloWay.HelloWay.services;

import com.HelloWay.HelloWay.entities.Categorie;
import com.HelloWay.HelloWay.entities.Product;
import com.HelloWay.HelloWay.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository ;

    @Autowired
    CategorieService categorieService ;

    public Optional<Product> addProduct(Product product){

        if (!productRepository.existsByProductTitle(product.getProductTitle()))
            return Optional.of(productRepository.save(product));
        else throw new IllegalArgumentException("products exists with this title");
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // exist exeption
    // generation du code table auto increment
    public Product addProductByIdCategorie(Product product, Long id_categorie){
        Categorie categorie = categorieService.findCategorieById(id_categorie);
        Product productObject = new Product();
        productObject = product;
        productObject.setCategorie(categorie);
        List<Product> products = new ArrayList<Product>();
        products = categorie.getProducts();
        products.add(product);

        productRepository.save(productObject);
        categorie.setProducts(products);
        return productObject;
    }
    public List<Product> getProductsByIdCategorie(Long id_categorie){
        Categorie categorie = categorieService.findCategorieById(id_categorie);
        return categorie.getProducts();
    }
}
