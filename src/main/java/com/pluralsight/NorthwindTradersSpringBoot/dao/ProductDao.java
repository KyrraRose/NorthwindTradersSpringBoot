package com.pluralsight.NorthwindTradersSpringBoot.dao;

import com.pluralsight.NorthwindTradersSpringBoot.model.Product;

import java.util.List;

public interface ProductDao {
    public void add(Product product);
    public Product makeProduct();

    public List<Product> getAll();

    public List<Product> getByProductName(String name);
    public List<Product> getByCategory(String category);
    public List<Product> getByPrice(double price);
    public Product getByProductID();
    public void delete();

}
