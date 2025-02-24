package me.adityabanerjee.kafkart_backend.utils;

import com.solacesystems.jms.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.*;
import java.util.concurrent.CompletableFuture;

public class SolaceConsumer {

    private static final Logger logger = LoggerFactory.getLogger("SolaceConsumer");
    private Session session;

    @Autowired
    private SolaceConfig solaceConfig;

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

                connectionFactory.setHost(solaceConfig.getHost());
                connectionFactory.setVPN(solaceConfig.getVpnName());
                connectionFactory.setUsername(solaceConfig.getUsername());
                connectionFactory.setPassword(solaceConfig.getPassword());

                Connection connection = connectionFactory.createConnection();
                connection.start();

                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                Topic topic = session.createTopic(solaceConfig.getTopic());
                MessageConsumer consumer = session.createConsumer(topic);

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

                logger.info("Successfully connected to Solace.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        });
    }
}
