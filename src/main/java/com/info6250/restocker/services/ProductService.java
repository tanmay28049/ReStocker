/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.info6250.restocker.services;

import com.info6250.restocker.dao.ProductDao;
import com.info6250.restocker.models.Product;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    public List<Product> getExpiringProducts(LocalDate threshold) {
        return productDao.findExpiringProducts(threshold);
    }
    
    public void addDonationSuggestion(Long productId, Long centerId) {
        productDao.addDonationSuggestion(productId, centerId);
    }
    
    public void calculateDiscount(Product product, LocalDate today) {
        long daysUntilExpiry = ChronoUnit.DAYS.between(today, product.getExpiryDate());
        
        if(daysUntilExpiry <= 0) {
            product.setDiscountPercentage(70);
        } else if(daysUntilExpiry <= 2) {
            product.setDiscountPercentage(50);
        } else if(daysUntilExpiry <= 5) {
            product.setDiscountPercentage(30);
        } else if(daysUntilExpiry <= 7) {
            product.setDiscountPercentage(15);
        } else {
            product.setDiscountPercentage(null);
        }
    }
}
