package de.unistuttgart.t2.order.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderItem, String> { 

}
