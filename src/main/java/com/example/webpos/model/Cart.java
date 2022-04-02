package com.example.webpos.model;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart implements Serializable {

    private List<Item> items = new ArrayList<>();

    public boolean addItem(Item item) {
        return items.add(item);
    }

    //TODO:remove item
    public boolean removeItem(Item item){
        return items.remove(item);
    }
    //TODO:empty the items
    public void emptyCart(){
        this.items.clear();
    }
    //TODO:get certain item by id from list of items
    public Item getItemById(String id_){
        for (Item item : items){
            if (item.getProduct().getId().equals(id_)){
                return item;
            }
        }
        return null;
    }

    public double getTotal() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getQuantity() * items.get(i).getProduct().getPrice();
        }
        return total;
    }

}
