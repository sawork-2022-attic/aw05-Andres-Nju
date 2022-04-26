package com.micropos.cart.service;

import com.micropos.cart.model.Cart;
import com.micropos.cart.model.Item;

import java.util.List;
import java.util.Optional;

public interface CartService {

    Double checkout(Cart cart);

    Double checkout(Integer cartId);

    Cart add(Cart cart, Item item);

    List<Cart> getAllCarts();

    Optional<Cart> getCart(Integer cartId);

    Integer test();
}
