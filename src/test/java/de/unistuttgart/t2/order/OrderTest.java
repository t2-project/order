package de.unistuttgart.t2.order;

import de.unistuttgart.t2.order.repository.OrderItem;
import de.unistuttgart.t2.order.repository.OrderRepository;
import de.unistuttgart.t2.order.repository.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestContext.class)
@ActiveProfiles("test")
public class OrderTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    String orderId;

    @BeforeEach
    public void setup() {
        orderId = orderRepository.save(new OrderItem("sessionId")).getOrderId();
    }

    @Test
    public void testCreateOrder() {
        // execute
        String id = orderService.createOrder("sessionId");

        // assert
        assertTrue(orderRepository.existsById(id));
        assertTrue(orderRepository.findById(id).isPresent());

        OrderItem item = orderRepository.findById(id).get();
        assertEquals("sessionId", item.getSessionId());
        assertEquals(OrderStatus.SUCCESS, item.getStatus());
    }

    @Test
    public void testRejectOrder() {
        // execute
        orderService.rejectOrder(orderId);

        // assert
        assertTrue(orderRepository.existsById(orderId));
        assertTrue(orderRepository.findById(orderId).isPresent());

        OrderItem item = orderRepository.findById(orderId).get();
        assertEquals("sessionId", item.getSessionId());
        assertEquals(OrderStatus.FAILURE, item.getStatus());
    }
}
