package com.pluralsight.NorthwindTradersSpringBoot;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.pluralsight.NorthwindTradersSpringBoot.Utility.*;

@Component //bean
public class SimpleProductDao implements ProductDao {
    private List<Product> products;

    public SimpleProductDao(){
        this.products = new ArrayList<>();
        this.products.add(new Product(90,"Red Bull","Beverages",3.59));
        this.products.add(new Product(91,"Chili Lime Popcorn","Snacks",4.99));
        this.products.add(new Product(92,"Buffalo Chicken Bites","Meat",8.45));
        this.products.add(new Product(93,"Goldfish Crackers","Snacks",3.40));
        this.products.add(new Product(94,"Hot Chocolate Mix","Beverages",2.49));
    }



    @Override
    public void add(Product product) {
        this.products.add(product);
    }

    @Override
    public Product makeProduct(){
        return new Product(
                getUserInt("ID: "),
                getUserString("Product Name: "),
                getUserString("Category: "),
                getUserDouble("Price: "));
    }

    @Override
    public List<Product> getAll() {
        return this.products;
    }

    @Override
    public List<Product> getByProductName() {
        return List.of();
    }

    @Override
    public List<Product> getByCategory() {
        return List.of();
    }

    @Override
    public List<Product> getByPrice() {
        return List.of();
    }

    @Override
    public Product getByProductID() {
        return null;
    }

    @Override
    public void delete() {

    }


}
