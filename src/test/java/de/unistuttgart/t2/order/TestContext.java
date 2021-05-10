package de.unistuttgart.t2.order;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import de.unistuttgart.t2.order.repository.OrderRepository;

@Configuration
@EnableMongoRepositories(basePackageClasses = {OrderRepository.class})
public class TestContext {
	
	@Bean
	public OrderService service() {
		return new OrderService();
	}

}
