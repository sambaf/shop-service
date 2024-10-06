package de.cimt.config;

import de.cimt.entities.Product;
import de.cimt.repository.ProductRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbInit {

  @Bean
  CommandLineRunner init(ProductRepository productRepository) {
    return args -> {
      var products = List.of(
          Product.builder().name("product1").description("test product 1").build(),
          Product.builder().name("product2").description("test product 2").build(),
          Product.builder().name("product3").description("test product 3").build(),
          Product.builder().name("product4").description("test product 4").build(),
          Product.builder().name("product5").description("test product 5").build(),
          Product.builder().name("product6").description("test product 6").build(),
          Product.builder().name("product7").description("test product 7").build(),
          Product.builder().name("product8").description("test product 8").build()
      );
      productRepository.saveAll(products);
      productRepository.findAll().forEach(p->{
        System.out.println(p);
      });
    };
  }
}
