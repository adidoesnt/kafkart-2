package me.adityabanerjee.kafkart_backend;

import me.adityabanerjee.kafkart_backend.utils.SolaceConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KafkartBackendApplication {

	private static final Logger logger = LoggerFactory.getLogger("main");

	public static void main(String[] args) {

		SpringApplication.run(KafkartBackendApplication.class, args);
		logger.info("KafKaRT backend server running on port 3001");
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			new Thread(() -> {
				SolaceConsumer consumer = new SolaceConsumer();
				consumer.connect();
			}).start();
		};
	}
}
