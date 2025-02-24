package me.adityabanerjee.kafkart_backend.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("users")
public record User(
        @Id
        Integer id,
        String firstName,
        String middleName,
        String lastName,
        String email,
        String passwordHash,
        String phone,
        String address,
        String city,
        String state,
        String zip,
        String country,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        @Version
        Integer version
) { }

