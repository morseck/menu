package com.example.genietech;

import java.util.ArrayList;
import java.util.stream.Stream;

//import org.apache.el.stream.Stream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.genietech.dao.CategoryRepository;
import com.example.genietech.dao.ProductRepository;
import com.example.genietech.entities.Category;
import com.example.genietech.entities.Product;
//import com.mongodb.connection.Stream;


@SpringBootApplication
public class GenietechApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenietechApplication.class, args);
	}
	
	@Bean
	CommandLineRunner start(CategoryRepository categoryRepository, ProductRepository productRepository) {
		return args->{
			categoryRepository.deleteAll();// suppression de toutes les categories lors de l'exe 
			//creation de categories lors de l'execution
			
			Stream.of("C1 Senegalaise", "C2 Chinoise").forEach(c->{
				categoryRepository.save(new Category(c.split(" ")[0], c.split(" ")[1], new ArrayList<>()));
			});
			//affichage de toutes les categories sur le console
			categoryRepository.findAll().forEach(System.out::println);
			
			productRepository.deleteAll();//suppromer tous les produits lors de l'execution
			
			//creation de produit lors de l'execution
			Category c1 = categoryRepository.findById("C1").get();
			Stream.of("tchep", "maffe", "domoda", "yassa").forEach(name->{
				Product p =  productRepository.save(new Product(null, name, Math.random() * 1000, c1));
				c1.getProducts().add(p);// ajout de produit dans categorie
				categoryRepository.save(c1);
			});
			
			Category c2 = categoryRepository.findById("C2").get();
			Stream.of("crevette", "yoyoyo", "chicha", "guerte").forEach(name->{
				Product p = productRepository.save(new Product(null, name, Math.random() * 1000, c2));
				c2.getProducts().add(p); // on ajoute un produit dans p
				categoryRepository.save(c2);
			});
			
			//affichage des prodruits
			productRepository.findAll().forEach(p->{
				System.out.println(p.toString());
			});
		};
	}
	

}

