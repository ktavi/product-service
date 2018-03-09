package com.example.product;

import com.example.product.data.Product;
import com.example.product.data.ProductRepository;
import com.example.product.data.Rule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ProductRepository productRepository) {
        return (args) -> {
            Product product1 = new Product("Product #1", new BigDecimal("200000"), new BigDecimal("6"), new Integer("36"));
            product1.getRules().add(new Rule("salary > 50000"));
            product1.getRules().add(new Rule("not debtor"));
            Product product2 = new Product("Product #2", null, new BigDecimal("15"), null);
            product2.getRules().add(new Rule("not debtor"));
            Product product3 = new Product("Product #3", new BigDecimal("1000000"), new BigDecimal("12"), new Integer("60"));
            product3.getRules().add(new Rule("salary > 25000"));
            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
        };
    }
}
