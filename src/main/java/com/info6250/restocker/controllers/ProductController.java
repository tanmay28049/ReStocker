/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.info6250.restocker.controllers;

import com.info6250.restocker.dao.ProductDao;
import com.info6250.restocker.models.Product;
import com.info6250.restocker.services.DonationCenterService;
import com.info6250.restocker.services.ProductService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author tanmay
 */
@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private DonationCenterService donationCenterService;
    
    @GetMapping
    public String listProducts(Model model) {
        LocalDate today = LocalDate.now();
        LocalDate threshold = LocalDate.now().plusDays(7);
        List<Product> products = productDao.findAll();
        products.forEach(p -> productService.calculateDiscount(p, today));
        model.addAttribute("products", products);
        model.addAttribute("expiringProducts", productService.getExpiringProducts(threshold));
        model.addAttribute("allCenters", donationCenterService.getAllCenters());
        model.addAttribute("today", LocalDate.now());
        return "products/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/create";
    }

    @PostMapping
    public String createProduct(@Valid @ModelAttribute Product product,
            BindingResult result) {
        if (result.hasErrors()) {
            return "products/create";
        }
        productDao.save(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productDao.findById(id);
        model.addAttribute("product", product);
        return "products/edit";
    }

    @PostMapping("/update")
    public String updateProduct(@Valid @ModelAttribute Product product,
            BindingResult result) {
        if (result.hasErrors()) {
            return "products/edit";
        }
        productDao.update(product);
        return "redirect:/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id") Long id) {
        Product product = productDao.findById(id);
        productDao.delete(product);
        return "redirect:/products";
    }

}
