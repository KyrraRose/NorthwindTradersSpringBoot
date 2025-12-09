package com.pluralsight.NorthwindTradersSpringBoot.dao;

import com.pluralsight.NorthwindTradersSpringBoot.model.Product;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.pluralsight.NorthwindTradersSpringBoot.util.Utility.*;
@Component
public class ProductDaoJDBC implements ProductDao{

    private List<Product> products;
    private DataSource dataSource;

    public ProductDaoJDBC(DataSource dataSource){
        this.products = new ArrayList<>();
        this.dataSource = dataSource;
    }

    @Override
    public void add(Product product) {
        String sql = "INSERT INTO `northwind`.`products` (`ProductID`,`ProductName`,`CategoryID`,`UnitPrice`,)VALUES " +
                "(?,?,?,?);\n";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setInt(1,product.getProductId());
            statement.setString(2,product.getName());
            statement.setInt(3,getCatId(product.getCategory()));
            statement.setDouble(4,product.getPrice());

            int added = statement.executeUpdate();

            if (added > 0) {
                System.out.println("Product added successfully.");
            } else {
                System.out.println("Product not found.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public int getCatId(String cat){
        String sql = "SELECT * FROM categories;";
        HashMap<String,Integer> categories = new HashMap<>();
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rows = statement.executeQuery();
            System.out.println("Categories:");
            while(rows.next()){
                categories.put(rows.getString("CategoryName"),rows.getInt("CategoryId"));
            }
            return categories.get(cat);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
       return 0;

    }

    @Override
    public Product makeProduct(){
        Product product = new Product(
                getUserInt("ID: "),
                getUserString("Product Name: "),
                getUserString("Category: "),
                getUserDouble("Price: "));
        System.out.println("Preview:%n"+product);
        return product;

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
    public List<Product> getByProductName() {
        this.products.clear();
        String sql = "SELECT ProductID, ProductName, CategoryName, UnitPrice FROM products p JOIN categories c ON p.CategoryId = c.CategoryId WHERE ProductName LIKE ?;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setString(1,"%"+getUserString("Product Name: ")+"%");

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
        this.products.clear();
        String sql = "SELECT ProductID, ProductName, CategoryName, UnitPrice FROM products p JOIN categories c ON p.CategoryId = c.CategoryId WHERE CategoryName LIKE ?;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setString(1,"%"+getUserString("Category: ")+"%");

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
    public List<Product> getByPrice() {
        this.products.clear();
        String sql = "SELECT ProductID, ProductName, CategoryName, UnitPrice FROM products p JOIN categories c ON p.CategoryId = c.CategoryId WHERE UnitPrice BETWEEN ? AND ?;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setDouble(1,getUserDouble("Lowest Price: "));
            statement.setDouble(2,getUserDouble("Highest Price: "));
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
    public Product getByProductID() {
        String sql = "SELECT ProductID, ProductName, CategoryName, UnitPrice FROM products p JOIN categories c ON p.CategoryId = c.CategoryId WHERE ProductId = ?;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setInt(1,getUserInt("Product ID: "));

            try(ResultSet rows = statement.executeQuery();) {
                if (rows.next()) {
                    return new Product(rows.getInt(1), rows.getString(2), rows.getString(3), rows.getDouble(4));
                }else{
                    System.out.println("No results found.");
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete() {
        String sql = "DELETE FROM products WHERE ProductId = ?;";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);){
            statement.setInt(1,getUserInt("Product ID: "));
            int product = statement.executeUpdate();

            if (product > 0) {
                System.out.println(product + " product(s) deleted successfully.");
            } else {
                System.out.println("Product not found.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}

