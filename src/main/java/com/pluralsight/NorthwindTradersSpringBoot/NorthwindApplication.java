package com.pluralsight.NorthwindTradersSpringBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

import static com.pluralsight.NorthwindTradersSpringBoot.Utility.*;

@Component
public class NorthwindApplication implements CommandLineRunner {
    private final ProductDao productDao;

    @Autowired
    public NorthwindApplication(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void run(String... args) throws Exception {
        while(true){
            displayMenu(new ArrayList<>(Arrays.asList(
                    "List Products",
                    "Add Products",
                    "Exit")), "Main Menu");

            switch (getUserInt("Type Here: ")) {
                case 1 -> productDao.getAll().stream().forEach(System.out::println);
                case 2 -> productDao.add(productDao.makeProduct());
                case 0 -> {System.out.println("Exiting."); System.exit(0);}
            }
        }
    }


}
