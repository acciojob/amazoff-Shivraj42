package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    //    @Autowired
    OrderRepository orderRepository = new OrderRepository();

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderRepository.addOrderPartnerPair(orderId, partnerId);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        // convert time
        String Time[] = time.split(":");
        String hour = Time[0];
        String minute = Time[1];
        int deliveryTime = Integer.parseInt(hour) * 60 + Integer.parseInt(minute);
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(deliveryTime, partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int latestDeliveryTime = orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
        // Convert time to String (HH:MM) format before sending
        String hour;
        hour = String.valueOf(latestDeliveryTime / 60);
        String minute;
        minute = String.valueOf(latestDeliveryTime % 60);

        if(hour.length() < 2){
            hour = "0" + hour;
        }
        if(minute.length() < 2){
            minute = "0" + minute;
        }
        return hour + ":" + minute;
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}