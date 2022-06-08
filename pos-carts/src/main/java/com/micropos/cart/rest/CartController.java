package com.micropos.cart.rest;

import com.micropos.api.ApiUtil;
import com.micropos.api.CartsApi;
import com.micropos.cart.model.Cart;
import com.micropos.cart.model.Item;
import com.micropos.cart.mapper.CartMapper;
import com.micropos.cart.service.CartService;
import com.micropos.dto.CartDto;
import com.micropos.dto.CartItemDto;
import com.micropos.dto.ProductDto;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/carts")
@EnableDiscoveryClient
public class CartController implements CartsApi {

    private final CartService cartService;
    private final CartMapper cartMapper;

    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartMapper = cartMapper;
        this.cartService = cartService;
    }

    @Override
    @PostMapping("/{cartId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable("cartId") Integer cartId, @RequestBody CartItemDto cartItemDto) {
        Cart cart = cartService.getCart(cartId);
        if(cart == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        CartDto cartDto = cartMapper.toCartDto(cart);
        Item item = cartMapper.toItem(cartItemDto, cartDto);
        System.out.println(item);
        Cart ret = cartService.add(cart, item);
        if(ret == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        cartDto.addItemsItem(cartItemDto);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @Override
    @PostMapping()
    public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto) {
        Cart cart = cartMapper.toCart(cartDto);
        if(cart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cart = cartService.addCart(cart);
        if (cart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartMapper.toCartDto(cart),HttpStatus.OK);
    }

    @Override
    @GetMapping()
    public ResponseEntity<List<CartDto>> listCarts() {
        List<CartDto> carts = new ArrayList<>(cartMapper.toCartDtos(this.cartService.getAllCarts()));
        if (carts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> showCartById(@PathVariable("cartId") Integer cartId) {
        Cart cart = cartService.getCart(cartId);
        if(cart == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        CartDto cartDto = cartMapper.toCartDto(cart);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{cartId}/total")
    public ResponseEntity<Double> showCartTotal(@PathVariable("cartId") Integer cartId) {

        Double total = cartService.checkTotal(cartId);

        if (total == -1d) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(total);
    }



}
