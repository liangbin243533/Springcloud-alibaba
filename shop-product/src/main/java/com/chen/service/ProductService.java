package com.chen.service;

import com.chen.domain.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    //Query information base on id
    Product findByPid(Integer pid);

    //Reducing Inventory
    void reduceInventory(Integer pid, Integer number);
}
