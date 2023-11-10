package com.project.springbootecommerce.controller;

import com.project.springbootecommerce.dto.Purchase;
import com.project.springbootecommerce.dto.PurchaseResponse;
import com.project.springbootecommerce.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    private CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {

        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);

        return purchaseResponse;
    }
}
