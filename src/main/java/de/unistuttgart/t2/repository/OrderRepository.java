package de.unistuttgart.t2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.unistuttgart.t2.domain.OrderItem;

public interface OrderRepository extends MongoRepository<OrderItem, String> { 

}
