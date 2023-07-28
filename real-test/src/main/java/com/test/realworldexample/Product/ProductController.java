package com.test.realworldexample.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @PostMapping(value = "/")
    public @ResponseBody Product addProduct(@RequestBody Product entity) {

        productRepository.save(entity);

        return entity;
    }

    @GetMapping(value = "/")
    public @ResponseBody Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping(value = "/{productId}")
    public Product getProduct(@PathVariable("productId") String id) {
        return productRepository.findById(id).get();
    }

    @PutMapping(value = "/{productId}")
    public Product editProduct(@PathVariable("productId") String id, @RequestBody Product entity) {
        Product product = productRepository.findById(id).get();
        if (product == null || entity == null) {
            return null;
        }

        if (entity.getName() != null)
            product.setName(entity.getName());

        if (entity.getDescription() != null)
            product.setDescription(entity.getDescription());

        if (entity.getPrice() != null)
            product.setPrice(entity.getPrice());

        if (entity.getImageUrl() != null)
            product.setImageUrl(entity.getImageUrl());

        if (entity.getCategory() != null)
            product.setCategory(entity.getCategory());

        if (entity.getSizes() != null)
            product.setSizes(entity.getSizes());

        productRepository.save(product);

        return entity;
    }

}
