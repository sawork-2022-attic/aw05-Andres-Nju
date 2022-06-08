package com.micropos.cart.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropos.cart.mapper.CartMapper;
import com.micropos.cart.model.Cart;
import com.micropos.cart.model.Item;
import com.micropos.cart.repository.CartRepository;
import com.micropos.dto.CartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;

    private final String COUNTER_URL = "http://POS-COUNTER/counter";

    private final String ORDER_URL = "http://POS-ORDERS/orders";

    private CartMapper cartMapper;

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Double checkTotal(Cart cart) {
        CartDto cartDto = cartMapper.toCartDto(cart);
        ObjectMapper mapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = null;
        try {
            request = new HttpEntity<>(mapper.writeValueAsString(cartDto), headers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Double total = restTemplate.postForObject(COUNTER_URL+ "/checkout", request, Double.class);
        return total;
    }

    public Integer test() {

        Integer test = restTemplate.getForObject(COUNTER_URL + "/test", Integer.class);
        return test;
    }

    @Override
    public Double checkTotal(Integer cartId) {
        Cart cart = (Cart) this.cartRepository.findCartById(cartId);

        if (cart == null) return Double.valueOf(-1);

        return this.checkTotal(cart);
    }

    @Override
    public Cart add(Cart cart, Item item) {
        if (cart.addItem(item))
            //return cartRepository.save(cart);
            return cart;
        return null;
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAllCarts();
    }

    @Override
    public Cart getCart(Integer cartId) {
        return (Cart) cartRepository.findCartById(cartId);
    }

    @Override
    public Cart addCart(Cart cart) {
        return cartRepository.saveCart(cart);
    }


}
