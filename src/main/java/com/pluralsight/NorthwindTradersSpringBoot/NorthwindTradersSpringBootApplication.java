package com.pluralsight.NorthwindTradersSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.*;

import static com.pluralsight.NorthwindTradersSpringBoot.Utility.*;

@SpringBootApplication
public class NorthwindTradersSpringBootApplication {
	private static ProductDao productDAO;
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(NorthwindTradersSpringBootApplication.class, args);

		displayMenu(mainMenu(),"Main Menu");

		productDAO = context.getBean(SimpleProductDao.class);

		switch (getUserInt("Type Here: ")){
			case 1 -> productDAO.getAll().stream().forEach(System.out::println);
			case 2 -> productDAO.add(new Product (
					getUserInt("ID: "),
					getUserString("Product Name: "),
					getUserString("Category: "),
					getUserDouble("Price: ")));
		}

	}

	public static ArrayList<String> mainMenu(){
		return new ArrayList<>(Arrays.asList("List Products", "Add Products"));
	}



}

