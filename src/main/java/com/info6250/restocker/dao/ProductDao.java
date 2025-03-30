/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.info6250.restocker.dao;

import com.info6250.restocker.models.Product;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author tanmay
 */
public interface ProductDao {
    void save(Product product);
    List<Product> findAll();
    Product findById(Long id);
    void delete(Product product);
    void update(Product product);
    List<Product> findExpiringProducts(LocalDate threshold);
}
