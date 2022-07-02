package org.example.util;

import org.example.entity.Customer;
import org.example.entity.Orders;

public class DamnClass {
    Orders orders;
    Customer customer;
    TotalPrice totalPrice;

    public DamnClass(Orders orders, Customer customer, TotalPrice totalPrice) {
        this.orders = orders;
        this.customer = customer;
        this.totalPrice = totalPrice;
    }

    public Orders getOrders() {
        return orders;
    }

    public Customer getCustomer() {
        return customer;
    }

    public TotalPrice getTotalPrice() {
        return totalPrice;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setTotalPrice(TotalPrice totalPrice) {
        this.totalPrice = totalPrice;
    }
}
