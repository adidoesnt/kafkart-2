package me.adityabanerjee.kafkart_backend.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Processable {
    static final Logger logger = LoggerFactory.getLogger("Processable");
    abstract void process();

    @Override
    abstract String toString();
}
