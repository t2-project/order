package de.unistuttgart.t2.order;

import org.springframework.beans.factory.annotation.Autowired;

import de.unistuttgart.t2.repository.OrderRepository;

//@Transactional // TODO ?? 
public class OrderService {
	
	//@Autowired
	//private OrderRepository orderRepository;
	
	/**
	 * Create a new Order and save it to the DB.
	 * 
	 * @param productId
	 * @param amount
	 * @param total
	 * @return Id of the new Order
	 */
	public String createOrder(String productId, int amount, double total) {
		
		// create new Order
		
		// save order to DB
		
		//return generated order id
		return null;
	}

	/**
	 * Set state of given order to "FAILURE".
	 * 
	 * If orderId does not match any order nothing happens. 
	 * 
	 * @param orderId - id of order that is to be rejected 
	 */
	public void rejectOrder(String orderId) {
		
		// find order
		
		// set status to failure and  re save order to DB
	}
}
