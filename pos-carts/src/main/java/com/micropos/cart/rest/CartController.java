package com.micropos.cart.rest;

import com.micropos.api.CartsApi;
import com.micropos.cart.model.Cart;
import com.micropos.cart.model.Item;
import com.micropos.cart.service.CartService;
import com.micropos.dto.CartDto;
import com.micropos.dto.CartItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartController implements CartsApi {

    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public ResponseEntity<CartDto> addItemToCart(Integer cartId, CartItemDto cartItemDto) {
        return CartsApi.super.addItemToCart(cartId, cartItemDto);
    }

    @Override
    public ResponseEntity<CartDto> createCart(CartDto cartDto) {
        return CartsApi.super.createCart(cartDto);
    }

    @Override
    public ResponseEntity<List<CartDto>> listCarts() {
        return CartsApi.super.listCarts();
    }

    @Override
    public ResponseEntity<CartDto> showCartById(Integer cartId) {
        return CartsApi.super.showCartById(cartId);
    }

    @Override
    public ResponseEntity<Double> showCartTotal(Integer cartId) {

//        Cart cart = new Cart();
//        Item item1 = new Item();
//        item1.productId("a").productName("abc").unitPrice(2).quantity(2);
//        cart.addItem(item1);
//        Item item2 = new Item();
//        item2.productId("b").productName("bcd").unitPrice(3.1).quantity(1);
//        cart.addItem(item2);
//        Double total = cartService.checkout(cart);

        Double total = cartService.checkout(cartId);

        if (total == -1d) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(total);
    }
}
