package com.project.springbootecommerce.service;

import com.project.springbootecommerce.dao.CustomerRepository;
import com.project.springbootecommerce.dto.Purchase;
import com.project.springbootecommerce.dto.PurchaseResponse;
import com.project.springbootecommerce.entity.Customer;
import com.project.springbootecommerce.entity.Order;
import com.project.springbootecommerce.entity.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        //retrieve the order info from dto
        Order order = purchase.getOrder();

        //generate traciking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        //populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        //populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        //polulate customer with order
        Customer customer = purchase.getCustomer();
        customer.add(order);

        //save to the database
        customerRepository.save(customer);

        //return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {

        //generate a random UUID number (UUID version-4)
        //For details see: https://en.wikipedia.org/wiki/Universally_unique_identifier
        //
        return UUID.randomUUID().toString();
    }

}
