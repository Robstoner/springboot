package com.test.realworldexample.Product;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String description;
    private Float price;
    private String imageUrl;
    private String category;
    private String[] sizes;
    private final String[] availableSizes = { "XS", "S", "M", "L", "XL", "XXL" };
    private final Date createdAt = new Date(System.currentTimeMillis());
}
