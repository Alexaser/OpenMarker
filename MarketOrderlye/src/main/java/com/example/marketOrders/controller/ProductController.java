package com.example.marketOrders.controller;

import com.example.marketOrders.entity.Product;
import com.example.marketOrders.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {


    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/registrationProduct")
    public void createProduct(@RequestBody Product product) {
        productService.createProduct(product);
    }

    @GetMapping("/getAll")
    public List<Product> getProducts() {
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    public Product getProductInId(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/search")
    public Product getProductInName(@RequestParam String name) {
        return productService.findByName(name);
    }

    @PutMapping("{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody Product product) {
        productService.updateProduct(product, id);
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    @GetMapping("/filter")
    public List<Product> filterToPrice(@RequestParam Integer minPrice, @RequestParam Integer maxPrice){
        return productService.filterProductInPrice(minPrice,maxPrice);
    }

}
