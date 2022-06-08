package com.micropos.cart.repository;

import com.micropos.cart.model.Cart;
import com.micropos.cart.model.Item;
import com.micropos.dto.ProductDto;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CartRepository <Cart, Integer> {

    List<Cart> findAllCarts();
    Cart findCartById(int id);
    com.micropos.cart.model.Cart saveCart(com.micropos.cart.model.Cart cart);
    List<Item> findItemsOfCart(int id);
    boolean addItemToCart(int cartId, Item item);
    boolean ModifyItem(int cartId, Item item);
    boolean DeleteItem(int cartId, Item item);

    boolean DeleteCart(com.micropos.cart.model.Cart cart);
}
