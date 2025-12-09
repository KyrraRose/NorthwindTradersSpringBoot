package com.pluralsight.NorthwindTradersSpringBoot;

import java.util.List;

public interface ProductDao {
    public void add(Product product);
    public Product makeProduct();

    public List<Product> getAll();

    public List<Product> getByProductName();
    public List<Product> getByCategory();
    public List<Product> getByPrice();
    public Product getByProductID();
    public void delete();

}
