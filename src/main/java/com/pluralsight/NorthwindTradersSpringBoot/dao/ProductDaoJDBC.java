package com.pluralsight.NorthwindTradersSpringBoot.dao;

import com.pluralsight.NorthwindTradersSpringBoot.model.Product;

import javax.sql.DataSource;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import static com.pluralsight.NorthwindTradersSpringBoot.util.Utility.*;

public class ProductDaoJDBC implements ProductDao{

    private List<Product> products;
    private DataSource dataSource;

    public ProductDaoJDBC(DataSource dataSource){
        this.products = new ArrayList<>();
        this.dataSource = dataSource;
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
        this.products.clear();
        String sql = "SELECT ProductID, ProductName, CategoryName, UnitPrice FROM products p JOIN categories c ON p.CategoryId = c.CategoryId ;";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rows = statement.executeQuery();
            while(rows.next()){
                this.products.add(new Product (rows.getInt(1),rows.getString(2),rows.getString(3),rows.getDouble(4)));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return this.products;
    }

    @Override
    public List<Product> getByProductName(String name) {
        this.products.clear();
        String sql = "SELECT ProductID, ProductName, CategoryName, UnitPrice FROM products p JOIN categories c ON p.CategoryId = c.CategoryId WHERE ProductName LIKE ?;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setString(1,name);

            try(ResultSet rows = statement.executeQuery();) {
                while (rows.next()) {
                    this.products.add(new Product(rows.getInt(1), rows.getString(2), rows.getString(3), rows.getDouble(4)));
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return this.products;
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

