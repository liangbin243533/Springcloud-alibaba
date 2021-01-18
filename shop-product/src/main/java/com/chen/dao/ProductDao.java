package com.chen.dao;

import com.chen.domain.Product;
import com.chen.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {
}
