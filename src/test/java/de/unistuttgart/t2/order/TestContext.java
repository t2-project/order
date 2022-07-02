package de.unistuttgart.t2.order;

import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import de.unistuttgart.t2.order.repository.OrderRepository;

@Configuration
@Profile("test")
@EnableMongoRepositories(basePackageClasses = { OrderRepository.class })
public class TestContext {

    @Bean
    public OrderService service() {
        return new OrderService();
    }
}
