package me.adityabanerjee.kafkart_backend.orders;

import org.springframework.data.repository.ListCrudRepository;

public interface OrderRepository extends ListCrudRepository<Order, Integer> { }
