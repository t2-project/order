package de.unistuttgart.t2.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import de.unistuttgart.t2.order.repository.OrderRepository;
import de.unistuttgart.t2.order.saga.OrderCommandHandler;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;

@Import({SagaParticipantConfiguration.class,
	TramMessageProducerJdbcConfiguration.class,
    EventuateTramKafkaMessageConsumerConfiguration.class, OptimisticLockingDecoratorConfiguration.class})
@EnableJpaRepositories
@EnableAutoConfiguration
@SpringBootApplication
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
		return sagaCommandDispatcherFactory.make("orderCommandDispatcher", target.commandHandlers());
	}

}
