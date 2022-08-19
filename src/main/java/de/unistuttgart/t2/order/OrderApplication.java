package de.unistuttgart.t2.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import de.unistuttgart.t2.common.BaseScan;
import de.unistuttgart.t2.order.repository.OrderRepository;
import de.unistuttgart.t2.order.saga.OrderCommandHandler;
import io.eventuate.tram.sagas.participant.*;
import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;

@Import({ SagaParticipantConfiguration.class, TramMessageProducerJdbcConfiguration.class,
    EventuateTramKafkaMessageConsumerConfiguration.class,
    OptimisticLockingDecoratorConfiguration.class })
@EnableJpaRepositories
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackageClasses = { BaseScan.class, OrderApplication.class })
@EnableMongoRepositories(basePackageClasses = OrderRepository.class)
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Bean
    public OrderService orderService() {
        return new OrderService();
    }

    @Bean
    public OrderCommandHandler orderCommandHandler() {
        return new OrderCommandHandler();
    }

    @Bean
    public SagaCommandDispatcher orderCommandDispatcher(OrderCommandHandler target,
        SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {
        return sagaCommandDispatcherFactory.make("orderCommandDispatcher",
            target.commandHandlers());
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components())
            .info(new Info().title("Order service API")
                .description("API of the T2-Project's order service."));
    }
}
