package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner partner= new DeliveryPartner(partnerId);
        orderRepository.addPartner(partner);
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

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        List<String> orders= getOrdersByPartnerId(partnerId);
        return orders.size();
    }


    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        int cnt=0;
        for(String orderId: getAllOrders()){
            if(!orderRepository.isOrderAssigned(orderId))cnt++;
        }
        return cnt;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        int cnt=0;
        int  timeInMM;   // HH:MM => HH*60 + MM
        for(String orderId : getOrdersByPartnerId(partnerId)){
            timeInMM= Integer.valueOf(time.substring(0, 2))*60 + Integer.valueOf(time.substring(3));
            if(timeInMM < getOrderById(orderId).getDeliveryTime()){
                cnt++;
            }
        }
        return cnt;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int timeInMM=0;
        for(String orderId: getOrdersByPartnerId(partnerId)){
            timeInMM= Math.max(timeInMM, getOrderById(orderId).getDeliveryTime());
        }
        return ""+timeInMM/60+":"+timeInMM%60;
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);

    }
}
