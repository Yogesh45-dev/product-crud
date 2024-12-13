package com.spring.crud.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.crud.model.Product;
import com.spring.crud.repo.ProductRepository;


@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Product saveProduct(Product product) {
        validateProduct(product);

        Optional<Product> existingProduct = Optional.ofNullable(repository.findByName(product.getName()));
        if (existingProduct.isPresent()) {
            throw new IllegalArgumentException("Product with name '" + product.getName() + "' already exists.");
        }

        return repository.save(product);
    }

    public List<Product> saveProducts(List<Product> products) {
        for (Product product : products) {
            validateProduct(product);
        }

        for (Product product : products) {
            Optional<Product> existingProduct = Optional.ofNullable(repository.findByName(product.getName()));
            if (existingProduct.isPresent()) {
                throw new IllegalArgumentException("Product with name '" + product.getName() + "' already exists.");
            }
        }

        return repository.saveAll(products);
    }
    
    private void validateProduct(Product product) {
        if (product.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive integer.");
        }

        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be a positive value.");
        }

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Product getProductById(int id) {
        return repository.findById(id).orElse(null);  
    }

    public String deleteProduct(int id) {
        repository.deleteById(id);
        return "product removed !! " + id;
    }

    public Product updateProduct(Product product) {
        Product existingProduct = repository.findById(product.getId()).orElse(null);
        existingProduct.setName(product.getName());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        return repository.save(existingProduct);
    }


}