package com.test.realworldexample.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.realworldexample.exceptions.ItemNotFoundException;
import com.test.realworldexample.files.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileService fileService;

    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Product not found"));
    }

    public byte[] getProductImage(String id) {
        Product product = getProduct(id);

        try {
            Resource image = fileService.load(product.getImageUrl());

            return image.getContentAsByteArray();
        } catch (Exception e) {
            throw new ItemNotFoundException("Product image not found or corrupted");
        }
    }

    public Product addProduct(Product entity) {
        return addProduct(entity, null);
    }

    public Product addProduct(Product entity, MultipartFile file) {
        if (file != null) {
            String fileName = fileService.save(file, "products");
            entity.setImageUrl(fileName);
        }

        productRepository.save(entity);

        return entity;
    }

    public Product editProduct(String id, Product entity) {
        return editProduct(id, entity, null);
    }

    public Product editProduct(String id, Product entity, MultipartFile file) {
        Product product = getProduct(id);

        if (entity == null) {
            return product;
        }

        if (entity.getName() != null)
            product.setName(entity.getName());

        if (entity.getDescription() != null)
            product.setDescription(entity.getDescription());

        if (entity.getPrice() != null)
            product.setPrice(entity.getPrice());

        if (entity.getCategory() != null)
            product.setCategory(entity.getCategory());

        if (entity.getSizes() != null)
            product.setSizes(entity.getSizes());

        if (file != null) {
            if (product.getImageUrl() != null) {
                fileService.delete(product.getImageUrl());
            }

            String fileName = fileService.save(file, "products");
            product.setImageUrl(fileName);
        }

        productRepository.save(product);

        return product;
    }

    public void deleteProduct(String id) {
        Product product = getProduct(id);

        if (product.getImageUrl() != null) {
            fileService.delete(product.getImageUrl());
        }

        productRepository.delete(product);
    }
}
