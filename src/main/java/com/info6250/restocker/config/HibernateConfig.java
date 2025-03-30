/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.info6250.restocker.config;

import com.info6250.restocker.models.Notification;
import com.info6250.restocker.models.DonationCenter;
import com.info6250.restocker.models.Product;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author tanmay
 */



@Configuration
public class HibernateConfig {
    @Bean
    public SessionFactory sessionFactory() {
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "org.postgresql.Driver");
        settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/restocker_db");
        settings.put(Environment.USER, "restocker_user");
        settings.put(Environment.PASS, "restockerDB@126");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        settings.put(Environment.HBM2DDL_AUTO, "update");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(settings)
                .build();
        
        return new org.hibernate.cfg.Configuration()
                .addPackage("com.info6250.restocker.models")
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(DonationCenter.class)
                .addAnnotatedClass(Notification.class)
                .buildSessionFactory(registry);
    }

}
