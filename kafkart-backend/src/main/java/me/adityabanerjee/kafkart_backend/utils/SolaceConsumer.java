package me.adityabanerjee.kafkart_backend.utils;

import com.solacesystems.jms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;
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
    private static final String TOPIC = "product/events";

    public SolaceConsumer() {
        this.session = null;
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

                Topic topic = session.createTopic(TOPIC);
                MessageConsumer consumer = session.createConsumer(topic);

                logger.info("Setting message listener...");
                consumer.setMessageListener(message -> {
                    if (message instanceof TextMessage) {
                        try {
                            String data = ((TextMessage) message).getText();

                            logger.info("Received product view: " + data);

                            // TODO: process product event
                        } catch (JMSException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                logger.info("Successfully set message listener.");
                return session;
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        });
    }
}
