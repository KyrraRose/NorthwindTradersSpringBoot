package com.pluralsight.NorthwindTradersSpringBoot;

import com.pluralsight.NorthwindTradersSpringBoot.dao.ProductDao;
import com.pluralsight.NorthwindTradersSpringBoot.dao.ProductDaoJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

import static com.pluralsight.NorthwindTradersSpringBoot.util.Utility.*;

@Component
public class NorthwindApplication implements CommandLineRunner {
    private final ProductDaoJDBC productDao;

    @Autowired
    public NorthwindApplication(ProductDaoJDBC productDao) {
        this.productDao = productDao;
    }

    @Override
    public void run(String... args) throws Exception {
        while(true){
            displayMenu(new ArrayList<>(Arrays.asList(
                    "List Products",
                    "Add Products",
                    "Search by Name",
                    "Search by Product ID",
                    "Search by Category",
                    "Search by Price",
                    "Delete Product",
                    "Exit")), "Main Menu");

            switch (getUserInt("Type Here: ")) {
                case 1 -> {System.out.println("\n-------------\nAll Products -------------------");
                    productDao.getAll().stream().forEach(System.out::println);}
                case 2 -> {System.out.println("\n-------------\nAdd Products -------------------");
                    productDao.add(productDao.makeProduct());
                }
                case 3 -> {System.out.println("-------------\n\nProducts Found -----------------");
                    productDao.getByProductName().stream().forEach(System.out::println);
                }
                case 4 -> {System.out.println("-------------\n\nProduct Found ------------------");
                    System.out.println(productDao.getByProductID());
                }
                case 5 -> {System.out.println("-------------\n\nProducts Found -----------------");
                    productDao.getByCategory().stream().forEach(System.out::println);
                }
                case 6 -> {System.out.println("-------------\n\nProducts Found -----------------");
                    productDao.getByPrice().stream().forEach(System.out::println);
                }
                case 7 -> {System.out.println("-------------\n\nDelete Product -----------------");
                    productDao.delete();
                }
                case 0 -> {System.out.println("Exiting."); System.exit(0);}
            }
        }
    }


}
