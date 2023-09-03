package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class OrderService {

    OrderRepository orderRepository = new OrderRepository();

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }


    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderRepository.addOrderPartnerPair(orderId , partnerId);
    }

    public Order getOrderById(String orderId) {
        Order order = orderRepository.getOrderById(orderId);
        return order;
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
        String[] arr = time.split(":");
        int newTime = Integer.parseInt(arr[0]) * 60 + Integer.parseInt(arr[1]);
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(newTime , partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int time = orderRepository.getLastDeliveryTimeByPartnerId(partnerId);

        String HH = String.valueOf(time / 60);
        String MM = String.valueOf(time % 60);

        if(HH.length() < 2){
            HH = 0 + HH;
        }
        if(MM.length() < 2){
            MM = 0 + MM;
        }

        return HH+':'+MM;
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}