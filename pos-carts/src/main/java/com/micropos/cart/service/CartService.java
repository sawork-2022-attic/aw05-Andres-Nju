package com.micropos.cart.service;

import com.micropos.cart.model.Cart;
import com.micropos.cart.model.Item;
import java.util.List;
import java.util.Optional;

public interface CartService {

    Double checkTotal(Cart cart);

    Double checkTotal(Integer cartId);


    Cart add(Cart cart, Item item);

    List<Cart> getAllCarts();

    Cart getCart(Integer cartId);

    Integer test();

    public Cart addCart(Cart cart);
}
