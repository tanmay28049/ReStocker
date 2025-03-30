/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.info6250.restocker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

/**
 *
 * @author tanmay
 */
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Product name is required")
    private String name;
    
    @NotBlank(message = "Barcode is required")
    private String barcode;
    
    @FutureOrPresent(message = "Expiry date must be in the future")
    private LocalDate expiryDate;
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
}
