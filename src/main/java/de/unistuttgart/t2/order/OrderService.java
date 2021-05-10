package de.unistuttgart.t2.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.unistuttgart.t2.order.repository.OrderItem;
import de.unistuttgart.t2.order.repository.OrderRepository;
import de.unistuttgart.t2.order.repository.OrderStatus;

/**
 * 
 * creates and updates orders.
 * 
 * @author maumau
 *
 */
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * create a new Order and save it to the repository.
     * 
     * the status of the new order is {@link OrderStatus#SUCCESS SUCCESS}.
     * 
     * @param sessionId id of session to create order for
     * @return orderId of created order
     */
    public String createOrder(String sessionId) {

        OrderItem item = new OrderItem(sessionId);
        return orderRepository.save(item).getOrderId();
    }

    /**
     * Set the state of an order to {@link OrderStatus#FAILURE FAILURE}.
     * 
     * This operation is idempotent, as a order may never change from
     * {@link OrderStatus#FAILURE FAILURE} to any other status.
     * 
     * @param orderId id of order that is to be rejected
     * 
     * @throws NoSuchElementException if the id is in the db but retrieval fails
     *                                anyway.
     */
    public void rejectOrder(String orderId) {

        OrderItem item = orderRepository.findById(orderId).get();
        item.setStatus(OrderStatus.FAILURE);
        orderRepository.save(item);
    }
}
