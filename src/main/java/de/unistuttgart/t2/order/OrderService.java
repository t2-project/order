package de.unistuttgart.t2.order;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import de.unistuttgart.t2.domain.OrderItem;
import de.unistuttgart.t2.domain.OrderStatus;
import de.unistuttgart.t2.repository.OrderRepository;

//@Transactional // TODO ?? 
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * Create a new Order and save it to the DB.
	 * 
	 * @param productId
	 * @param amount
	 * @param total
	 * @return Id of the new Order
	 */
	public String createOrder(String sessionId) {
		
		// create new Order
		OrderItem item = new OrderItem(sessionId, OrderStatus.SUCCESS, Date.from(Instant.now()));
		
		// save order to DB
		String orderId = orderRepository.save(item).getOrderId();
		
		//return generated order id
		return orderId;
	}

	/**
	 * Set the state of an order to "FAILURE".
	 * 
	 * If the given id does not match any order nothing happens. 
	 * This operation is idempotent, as a order may never change from "failure" to any other status.
	 * 	
	 * @param orderId - id of order that is to be rejected
	 * 
	 * @throws NoSuchElementException if the id is in the db but retrieval fails anyway. 
	 */
	public void rejectOrder(String orderId) {

		// missing order 
		if (!orderRepository.existsById(orderId)) {
			return; 
		}
		
		// update order
		OrderItem item = orderRepository.findById(orderId).get();
		item.setStatus(OrderStatus.FAILURE);
		orderRepository.save(item);
	}
}
