package me.adityabanerjee.kafkart_backend.products;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("products")
public record Product(
        @Id
        Integer id,
        String name,
        String shortDescription,
        String description,
        Double price,
        String image,
        Integer stock,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        @Version
        Integer version
        ) { }
