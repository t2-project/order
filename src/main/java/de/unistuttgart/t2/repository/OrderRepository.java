package de.unistuttgart.t2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderItem, String> { 

}
