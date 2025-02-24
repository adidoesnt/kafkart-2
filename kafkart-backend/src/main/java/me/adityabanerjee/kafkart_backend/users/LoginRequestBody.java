package me.adityabanerjee.kafkart_backend.users;

public record LoginRequestBody(
        String email,

        String password
) { }
