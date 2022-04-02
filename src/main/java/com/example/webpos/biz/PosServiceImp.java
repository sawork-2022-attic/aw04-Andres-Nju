package com.example.webpos.biz;

import com.example.webpos.db.PosDB;
import com.example.webpos.model.Cart;
import com.example.webpos.model.Item;
import com.example.webpos.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PosServiceImp implements PosService, Serializable {

    private PosDB posDB;

    @Autowired
    public void setPosDB(PosDB posDB) {
        this.posDB = posDB;
    }


    @Override
    public Product randomProduct() {
        return products().get(ThreadLocalRandom.current().nextInt(0, products().size()));
    }

    @Override
    public void checkout(Cart cart) {

    }

    @Override
    public Cart add(Cart cart, Product product, int amount) {
        return add(cart, product.getId(), amount);
    }

    @Override
    public Cart add(Cart cart, String productId, int amount) {

        Product product = posDB.getProduct(productId);
        if (product == null) return cart;

        //TODO: product maybe already exist in the list ; amount maybe negative --> maybe remove the item
        Item item = cart.getItemById(productId);
        if (item == null) cart.addItem(new Item(product, amount));
        else {//product already exist
            int newAmount = amount + item.getQuantity();
            if (newAmount > 0) {
                cart.removeItem(item);
                cart.addItem(new Item(product, amount + item.getQuantity()));
                return cart;
            }
            else if(newAmount == 0) cart.removeItem(item);
            else return cart;//newAmount < 0, failed
        }
        return cart;
    }

    @Override
    public List<Product> products() {
        return posDB.getProducts();
    }
}
