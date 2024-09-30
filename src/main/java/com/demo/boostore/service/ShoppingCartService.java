package com.demo.boostore.service;

import com.demo.boostore.dao.BookRepository;
import com.demo.boostore.dao.CartItemRepository;
import com.demo.boostore.dao.ShoppingCartRepository;
import com.demo.boostore.entity.Book;
import com.demo.boostore.entity.CartItem;
import com.demo.boostore.entity.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BookRepository bookRepository;

    public ShoppingCart createCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.setCartItems(new HashSet<>());
        return shoppingCartRepository.save(cart);
    }

    public void addToCart(Long cartId, Long bookId, Integer quantity) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);
        if (cart != null && book != null) {
            CartItem item = new CartItem();
            item.setBook(book);
            item.setQuantity(quantity);
            item.setShoppingCart(cart);
            cartItemRepository.save(item);
            cart.getCartItems().add(item);
            shoppingCartRepository.save(cart);
        }
    }

    public Double calculateTotal(Long cartId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            return cart.getCartItems().stream()
                    .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
                    .sum();
        }
        return 0.0;
    }
}