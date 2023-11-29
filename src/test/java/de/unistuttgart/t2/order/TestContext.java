package de.unistuttgart.t2.order;

import de.unistuttgart.t2.order.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@Profile("test")
@EnableMongoRepositories(basePackageClasses = { OrderRepository.class })
public class TestContext {

    @Bean
    public OrderService service() {
        return new OrderService();
    }
}
