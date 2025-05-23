/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.info6250.restocker.dao;

import com.info6250.restocker.models.DonationCenter;
import com.info6250.restocker.models.Product;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tanmay
 */
@Repository
public class ProductDaoImpl implements ProductDao {

    private final SessionFactory sessionFactory;

    public ProductDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(product);
        session.getTransaction().commit();
    }

    @Override
    public List<Product> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<Product> products = session.createQuery(
                "SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.suggestedCenters",
                Product.class
        ).list();

        session.getTransaction().commit();
        return products;
    }

    @Override
    public Product findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Product product = session.get(Product.class, id);
        session.getTransaction().commit();
        return product;
    }

    @Override
    public void delete(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.remove(product);
        session.getTransaction().commit();
    }

    @Override
    public List<Product> findExpiringProducts(LocalDate threshold) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Product> products = session.createQuery(
                "FROM Product p WHERE p.expiryDate <= :threshold", Product.class)
                .setParameter("threshold", threshold)
                .list();
        session.getTransaction().commit();
        return products;
    }

    @Override
    public void update(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(product);
        session.getTransaction().commit();
    }

    @Override
    public void addDonationSuggestion(Long productId, Long centerId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Product product = session.get(Product.class, productId);
        DonationCenter center = session.get(DonationCenter.class, centerId);

        if (product != null && center != null) {
            product.getSuggestedCenters().add(center);
            session.merge(product);
        }

        session.getTransaction().commit();
    }

    @Override
    public List<Product> findExpiringProductsBetweenDates(LocalDate start, LocalDate end) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Product> products = session.createQuery(
                "FROM Product p WHERE p.expiryDate BETWEEN :start AND :end", Product.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .list();
        session.getTransaction().commit();
        return products;
    }

    @Override
    public Map<LocalDate, List<Product>> findExpiredProductsByDate(LocalDate start, LocalDate end) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Product> products = session.createQuery(
                "FROM Product p WHERE p.expiryDate BETWEEN :start AND :end", Product.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .list();

        Map<LocalDate, List<Product>> trends = products.stream()
                .collect(Collectors.groupingBy(Product::getExpiryDate));

        session.getTransaction().commit();
        return trends;
    }

    @Override
    public Map<DonationCenter, List<Product>> findProductsNeedingDonation() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Product> products = session.createQuery(
                "FROM Product p WHERE size(p.suggestedCenters) > 0", Product.class)
                .list();

        Map<DonationCenter, List<Product>> suggestions = new HashMap<>();
        products.forEach(product -> {
            product.getSuggestedCenters().forEach(center -> {
                suggestions.computeIfAbsent(center, k -> new ArrayList<>()).add(product);
            });
        });

        session.getTransaction().commit();
        return suggestions;
    }

    @Override
    public Product findByBarcode(String barcode) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Product product = session.createQuery("FROM Product WHERE barcode = :barcode", Product.class)
                .setParameter("barcode", barcode)
                .uniqueResult();
        session.getTransaction().commit();
        return product;
    }
    
    @Override
    public List<Product> findAllSortedByExpiry() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Product> products = session.createQuery(
            "FROM Product p ORDER BY p.expiryDate ASC", Product.class)
            .list();
        session.getTransaction().commit();
        return products;
    }
}
