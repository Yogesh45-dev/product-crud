package com.spring.crud.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.crud.model.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findByName(String name);
}