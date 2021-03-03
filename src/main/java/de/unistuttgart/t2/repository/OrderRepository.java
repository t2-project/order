package de.unistuttgart.t2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.unistuttgart.t2.domain.Order;

public interface OrderRepository extends MongoRepository<Order, String> { 

}
