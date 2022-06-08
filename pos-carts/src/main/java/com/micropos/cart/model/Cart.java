package com.micropos.cart.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "carts")
@Accessors(fluent = true, chain = true)
public class Cart implements Serializable {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Integer id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "items", joinColumns = @JoinColumn(name = "cart_id"))
    @Setter
    @Getter
    private List<Item> items = new ArrayList<>();

    public boolean addItem(Item item) {
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item item1 = it.next();
            if (item1.productId().equals(item.productId())) {
                int newAmount = item1.quantity() + item.quantity();
                if (newAmount <= 0) {
                    it.remove();
                }
                else {
                    item1.quantity(newAmount);
                }

                System.out.println("has el: " + this);
                return true;
            }
        }
        if (item.quantity() <= 0) return false;
        System.out.println("new has el: " + this);
        return items.add(item);
    }

    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
