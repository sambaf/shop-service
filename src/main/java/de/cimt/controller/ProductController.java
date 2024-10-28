package de.cimt.controller;

import de.cimt.entities.Product;
import de.cimt.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('USER')")
  public Product getProductById(@PathVariable Long id) {
    return productService.findById(id);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('USER')")
  public List<Product> getAllProducts() {
    return productService.findAll();
  }
  @PostMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public Product createProduct(@RequestBody Product product) {
    return productService.save(product);
  }
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public void deleteProduct(@PathVariable Long id) {
    productService.deleteById(id);
  }
}
