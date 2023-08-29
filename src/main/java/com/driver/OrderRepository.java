package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    HashMap<String, Order> orderHashMap= new HashMap<>();
    HashMap<String, DeliveryPartner> partnerHashMap = new HashMap<>();
    HashMap<String, String> order_partner= new HashMap<>();
    HashMap<String, List<String>> partner_orders= new HashMap<>();

    public void addOrder(Order order) {
        orderHashMap.put(order.getId(), order);
    }

    public void addPartner(DeliveryPartner partner) {
        partnerHashMap.put(partner.getId(), partner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        order_partner.put(orderId, partnerId);
        if(partner_orders.containsKey(partnerId)){
            partner_orders.get(partnerId).add(orderId);
        }
        else{
            partner_orders.put(partnerId, new ArrayList<String>());
            partner_orders.get(partnerId).add(orderId);
        }
    }

    public Order getOrderById(String orderId) {
        return orderHashMap.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerHashMap.get(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return partner_orders.get(partnerId);
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orderHashMap.keySet());
    }

    public boolean isOrderAssigned(String orderId) {
        return order_partner.containsKey(orderId);
    }

    public void deletePartnerById(String partnerId) {
        partner_orders.remove(partnerId);
        partnerHashMap.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderHashMap.remove(orderId);
        int idx=0;
        for(String order:partner_orders.get(order_partner.get(orderId))){
            if(orderId.equals(order))break;
            idx++;
        }
        partner_orders.get(order_partner.get(orderId)).remove(idx);
        order_partner.remove(orderId);
    }
}
