package com.service.usermanagement.repository;

import com.service.usermanagement.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findAllByIdIn(Set<String> productID);
}
