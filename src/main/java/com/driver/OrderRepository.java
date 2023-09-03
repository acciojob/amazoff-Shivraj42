package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class OrderRepository {
    HashMap<String, Order> orderDB = new HashMap<>();
    HashMap<String, DeliveryPartner> deliveryPartnerDB = new HashMap<>();
    HashMap<String, List<Order>> partnerOrderDB = new HashMap<>();
    public void addOrder(Order order) {
        orderDB.put(order.getId(), order);
    }

    public void addPartner(String partnerId) {
        deliveryPartnerDB.put(partnerId, new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        if(orderDB.containsKey(orderId) && deliveryPartnerDB.containsKey(partnerId)){
            List<Order> list = new ArrayList<>();
            if(partnerOrderDB.containsKey(partnerId)){
                partnerOrderDB.get(partnerId).add(orderDB.get(orderId));
            } else {
                partnerOrderDB.put(partnerId, new ArrayList<>());
                partnerOrderDB.get(partnerId).add(orderDB.get(orderId));
            }
            deliveryPartnerDB.get(partnerId).setNumberOfOrders(1);
        }
    }

    public Order getOrderById(String orderId) {
        if(!orderDB.containsKey(orderId)){
            return null;
        }
        return orderDB.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        if(!deliveryPartnerDB.containsKey(partnerId)){
            return null;
        }
        return deliveryPartnerDB.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        if(deliveryPartnerDB.containsKey(partnerId)){
            return deliveryPartnerDB.get(partnerId).getNumberOfOrders();
        }
        return 0;
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        List<String> list = new ArrayList<>();
        if(partnerOrderDB.containsKey(partnerId)) {
            for (Order order : partnerOrderDB.get(partnerId)) {
                list.add(order.getId());
            }
        }
        return list;
    }

    public List<String> getAllOrders() {
        List<String> orderList = new ArrayList<>();
        for(String orderId : orderDB.keySet()){
            orderList.add(orderId);
        }
        return orderList;
    }

    public Integer getCountOfUnassignedOrders() {
        int totalOrders = orderDB.size();
        int assignedOrders = 0;
        for(String partnerId : partnerOrderDB.keySet()){
            assignedOrders += partnerOrderDB.get(partnerId).size();
        }
        return totalOrders - assignedOrders;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int time, String partnerId){
        int undeliveredOrders = 0;
        for(Order order : partnerOrderDB.get(partnerId)){
            if(order.getDeliveryTime() > time){
                undeliveredOrders++;
            }
        }
        return undeliveredOrders;
    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        int latestDeliveryTime = Integer.MIN_VALUE;
        for(Order order : partnerOrderDB.get(partnerId)){
            latestDeliveryTime = Math.max(latestDeliveryTime, order.getDeliveryTime());
        }
        return latestDeliveryTime;
    }

    public void deletePartnerById(String partnerId) {
        if(deliveryPartnerDB.containsKey(partnerId) && partnerOrderDB.containsKey(partnerId)) {
            deliveryPartnerDB.remove(partnerId);
            partnerOrderDB.remove(partnerId);
        }
    }

    public void deleteOrderById(String orderId) {
        if(orderDB.containsKey(orderId)) {
            orderDB.remove(orderId);
            for (String partnerID : partnerOrderDB.keySet()) {
                List<Order> orderList = partnerOrderDB.get(partnerID);
                String partnerId = "";
                boolean orderRemoved = false;
                for (int i = 0; i < orderList.size(); i++) {
                    if (orderList.get(i).getId().equals(orderId)) {
                        orderList.remove(i);
                        orderRemoved = true;
                        partnerId = partnerID;
                        break;
                    }
                }
                if (orderRemoved) {
                    deliveryPartnerDB.get(partnerId).setNumberOfOrders(-1);
                    break;
                }
            }
        }
    }
}