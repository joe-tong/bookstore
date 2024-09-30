package com.demo.boostore.controller;

import com.demo.boostore.entity.ShoppingCart;
import com.demo.boostore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/create")
    public ShoppingCart createCart() {
        return shoppingCartService.createCart();
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long cartId, @RequestParam Long bookId, @RequestParam Integer quantity) {
        shoppingCartService.addToCart(cartId, bookId, quantity);
        return "Added to cart";
    }

    @GetMapping("/total")
    public Double getTotal(@RequestParam Long cartId) {
        return shoppingCartService.calculateTotal(cartId);
    }
}