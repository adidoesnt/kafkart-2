package me.adityabanerjee.kafkart_backend.utils;

public class ProductViewMessageBody implements Processable {
    public int userId;
    public int productId;
    public long timestamp;

    public void process() {
        logger.info("Received message: " + this.toString());
    }

    public String toString() {
        return "{ " + "userId: " + userId + ", " + "productId: " + productId + ", " + "timestamp: " + timestamp + " }";
    }
}
