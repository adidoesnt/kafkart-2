package me.adityabanerjee.kafkart_backend.utils;

public class ProductAddToCartMessageBody implements Processable {
    public int userId;
    public int productId;
    public int quantity;
    public long timestamp;

    public void process() {
        logger.info("Received message: " + this);
    }

    public String toString() {
        return "{ " + "userId: " + userId + ", " + "productId: " + productId + ", " + "quantity: " + quantity + ", " + "timestamp: " + timestamp + " }";
    }
}
