package me.adityabanerjee.kafkart_backend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solacesystems.jms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CompletableFuture;

@Component
public class SolaceConsumer {

    private static final Logger logger = LoggerFactory.getLogger("SolaceConsumer");
    private Session session;

    // TODO: Move config to application properties
    private static final String HOST = "ws://localhost:8008";
    private static final String USERNAME = "backend_user";
    private static final String PASSWORD = "password";
    private static final String VPN_NAME = "default";
    private static final String PRODUCT_VIEW_TOPIC = "product/view";
    private static final String ADD_TO_CART_TOPIC = "product/add-to-cart";
    private static final String CHECKOUT_TOPIC = "checkout";

    private ObjectMapper objectMapper;

    public SolaceConsumer() {

        this.session = null;
        this.objectMapper = new ObjectMapper();
    }

    private String getRawJson(Message message) throws UnsupportedEncodingException, JMSException {
        BytesMessage bytesMessage = (BytesMessage) message;
        byte[] data = new byte[(int) bytesMessage.getBodyLength()];
        bytesMessage.readBytes(data);

        String json = new String(data, "UTF-8");
        logger.info("Received raw JSON message " + json);

        return json;
    }

    public CompletableFuture<Session> connect() {
        return CompletableFuture.supplyAsync(() -> {
            if (session != null) {
                logger.info("Session available, returning...");

                return session;
            }

            try {
                logger.info("Connecting to Solace...");

                SolConnectionFactory connectionFactory = SolJmsUtility.createConnectionFactory();

                connectionFactory.setHost(HOST);
                connectionFactory.setVPN(VPN_NAME);
                connectionFactory.setUsername(USERNAME);
                connectionFactory.setPassword(PASSWORD);

                Connection connection = connectionFactory.createConnection();
                connection.start();

                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                logger.info("Successfully connected to Solace.");

                Topic productViewTopic = session.createTopic(PRODUCT_VIEW_TOPIC);
                MessageConsumer productViewConsumer = session.createConsumer(productViewTopic);

                logger.info("Setting \"product view\" message listener...");
                productViewConsumer.setMessageListener(message -> {
                    try {
                        String json = getRawJson(message);
                        ProductViewMessageBody parsedData = objectMapper.readValue(json, ProductViewMessageBody.class);

                        parsedData.process();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

                Topic addToCartTopic = session.createTopic(ADD_TO_CART_TOPIC);
                MessageConsumer addToCartConsumer = session.createConsumer(addToCartTopic);

                logger.info("Setting \"add to cart\" message listener...");
                addToCartConsumer.setMessageListener(message -> {
                    try {
                        String json = getRawJson(message);
                        ProductAddToCartMessageBody parsedData = objectMapper.readValue(json, ProductAddToCartMessageBody.class);

                        parsedData.process();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

                Topic checkoutTopic = session.createTopic(CHECKOUT_TOPIC);
                MessageConsumer checkoutConsumer = session.createConsumer(checkoutTopic);

                logger.info("Setting \"checkout\" message listener...");
                checkoutConsumer.setMessageListener(message -> {
                    try {
                        String json = getRawJson(message);
                        CheckoutMessageBody parsedData = objectMapper.readValue(json, CheckoutMessageBody.class);

                        parsedData.process();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

                logger.info("Successfully set message listeners.");
                return session;
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        });
    }
}
