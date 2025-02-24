package me.adityabanerjee.kafkart_backend.utils;

public class CheckoutMessageBody implements Processable {
    public int userId;
    public ProductWithQuantity[] products;
    public int totalPrice;
    public long timestamp;

    public void process() {
        logger.info("Received message: " + this);
    }

    public String toString() {
        StringBuilder productsString = new StringBuilder("[");
        for (ProductWithQuantity product : products) {
            productsString.append("{ productId: ")
                    .append(product.productId())
                    .append(", quantity: ")
                    .append(product.quantity())
                    .append(" }, ");
        }
        // Remove the last comma and space, if any products were added
        if (products.length > 0) {
            productsString.setLength(productsString.length() - 2);
        }
        productsString.append("]");

        return "{ " +
                "userId: " + userId + ", " +
                "products: " + productsString + ", " +
                "totalPrice: " + totalPrice + ", " +
                "timestamp: " + timestamp +
                " }";
    }
}
