package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {

    Map<String , Order> ordersDB = new HashMap<>();
    Map<String , DeliveryPartner> partnersDB = new HashMap<>();
    Map<String , String> orderPartnerDB = new HashMap<>();
    Map<String , List<String>> partnerOrdersDB = new HashMap<>();

    public void addOrder(Order order) {
        ordersDB.put(order.getId() , order);
    }

    public void addPartner(String partnerId) {
        partnersDB.put(partnerId , new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {

        if(ordersDB.containsKey(orderId) && partnersDB.containsKey(partnerId)){
            orderPartnerDB.put(orderId , partnerId);

            List<String> orders = new ArrayList<>();
            if(partnerOrdersDB.containsKey(partnerId)){
                orders = partnerOrdersDB.get(partnerId);
            }

            orders.add(orderId);
            partnerOrdersDB.put(partnerId , orders);

            // Increase the number of orders of Delivery partner
            DeliveryPartner deliveryPartner = partnersDB.get(partnerId);
            deliveryPartner.setNumberOfOrders(orders.size());
        }
    }

    public Order getOrderById(String orderId) {
        return ordersDB.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partnersDB.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return partnersDB.get(partnerId).getNumberOfOrders();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return partnerOrdersDB.get(partnerId);
    }

    public List<String> getAllOrders() {
        List<String> orders = new ArrayList<>();
        for(String orderId : ordersDB.keySet()){
            orders.add(orderId);
        }

        return orders;
    }

    public Integer getCountOfUnassignedOrders() {
        return ordersDB.size() - orderPartnerDB.size();
    }


    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int time, String partnerId) {
        int count = 0;
        List<String> orders = partnerOrdersDB.get(partnerId);

        for(String orderId : orders){
            if(ordersDB.get(orderId).getDeliveryTime() > time){
                count++;
            }
        }

        return count;
    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {

        int maxTime = 0;
        List<String> orders = partnerOrdersDB.get(partnerId);

        for(String orderId : orders){
            int deliveryTime = ordersDB.get(orderId).getDeliveryTime();
            maxTime = Math.max(maxTime , deliveryTime);
        }

        return maxTime;
    }

    public void deletePartnerById(String partnerId) {

        List<String> orders = partnerOrdersDB.get(partnerId);
        for(String orderId : orders){
            orderPartnerDB.remove(orderId);
        }

        partnersDB.remove(partnerId);
        partnerOrdersDB.remove(partnerId);


    }

    public void deleteOrderById(String orderId) {

        ordersDB.remove(orderId);

        String partner = orderPartnerDB.get(orderId);
        orderPartnerDB.remove(orderId);

        partnerOrdersDB.get(partner).remove(orderId);
        partnersDB.get(partner).setNumberOfOrders(partnerOrdersDB.get(partner).size());
    }
}