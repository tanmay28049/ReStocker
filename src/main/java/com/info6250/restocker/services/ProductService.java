/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.info6250.restocker.services;

import com.info6250.restocker.dao.ProductDao;
import com.info6250.restocker.models.Product;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tanmay
 */
@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public List<Product> getExpiringProducts() {
        LocalDate threshold = LocalDate.now().plusDays(7);
        return productDao.findExpiringProducts(threshold);
    }
    
    public void addDonationSuggestion(Long productId, Long centerId) {
        productDao.addDonationSuggestion(productId, centerId);
    }
}
