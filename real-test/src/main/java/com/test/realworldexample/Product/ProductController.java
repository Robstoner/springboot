package com.test.realworldexample.Product;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/")
    public @ResponseBody Iterable<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping(value = "/{productId}")
    public Product getProduct(@PathVariable("productId") String id) {
        return productService.getProduct(id);
    }

    @GetMapping(value = "/{productId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProductImage(@PathVariable("productId") String id) throws IOException{
        byte[]image = productService.getProductImage(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody Product addProduct(@RequestParam("file") MultipartFile file, @RequestParam Map<String, String> body) {
        Product product = new Product();
        product.setName(body.get("name"));
        product.setDescription(body.get("description"));
        product.setPrice(Double.parseDouble(body.get("price")));
        product.setCategory(body.get("category"));
        product.setSizes(body.get("sizes"));
        
        return productService.addProduct(file, product);
    }

    @PutMapping(value = "/{productId}")
    public Product editProduct(@PathVariable("productId") String id, @RequestBody Product entity) {
        return productService.editProduct(id, entity);
    }

    @DeleteMapping(value = "/{productId}")
    public void deleteProduct(@PathVariable("productId") String id) {
        productService.deleteProduct(id);
    }

}
