/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.info6250.restocker.config;

import com.info6250.restocker.models.Notification;
import com.info6250.restocker.services.NotificationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author tanmay
 */
@ControllerAdvice
public class GlobalControllerAdvice {
    
    @Autowired
    private NotificationService notificationService;

    @ModelAttribute("notifications")
    public List<Notification> getNotifications() {
        return notificationService.getUnacknowledgedNotifications();
    }
}
