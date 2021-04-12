package de.unistuttgart.t2.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.unistuttgart.t2.common.saga.SagaData;
import de.unistuttgart.t2.repository.OrderItem;
import de.unistuttgart.t2.repository.OrderRepository;
import de.unistuttgart.t2.repository.OrderStatus;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestContext.class)
@ActiveProfiles("test")
public class OrderTest {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	OrderRepository orderRepository;
	
	SagaData data; 
	
	String orderid;
	
	@BeforeEach
	public void setup() {
		orderid = orderRepository.save(new OrderItem("sessionId", OrderStatus.SUCCESS, Date.from(Instant.now()))).getOrderId();
	}
	
	@Test
	public void testCreateOrder() {
		// execute	
		String id = orderService.createOrder("sessionId");
		
		//assert
		assertTrue(orderRepository.existsById(id));
		assertTrue(orderRepository.findById(id).isPresent());
		
		OrderItem item = orderRepository.findById(id).get();
		assertEquals("sessionId", item.getSessionId());
		assertEquals(OrderStatus.SUCCESS, item.getStatus());
	}
	
	@Test
	public void testCreateOrder_failOnNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {	
			orderService.createOrder(null);
		});
	}
	
	@Test
	public void testRejectOrder() {
		// execute	
		orderService.rejectOrder(orderid);
		
		//assert
		assertTrue(orderRepository.existsById(orderid));
		assertTrue(orderRepository.findById(orderid).isPresent());
		
		OrderItem item = orderRepository.findById(orderid).get();
		assertEquals("sessionId", item.getSessionId());
		assertEquals(OrderStatus.FAILURE, item.getStatus());
	}
	
	@Test
	public void testRejectOrder_failOnNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			orderService.rejectOrder(null);
		});
	}
	
	@Test
	public void testRejectOrder_failWrongOrderId() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			orderService.rejectOrder("id_that_does_not_match_any_order");
		});
	}
}