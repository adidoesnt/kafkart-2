package me.adityabanerjee.kafkart_backend.cart;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("orders")
public record Order(
        @Id Integer id,
        Integer userId,
        Boolean confirmed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        @Version
        Integer version
) {}