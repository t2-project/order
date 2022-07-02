package de.unistuttgart.t2.order;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.unistuttgart.t2.order.repository.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestContext.class)
@ActiveProfiles("test")
public class OrderTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    String orderid;

    @BeforeEach
    public void setup() {
        orderid = orderRepository.save(new OrderItem("sessionId")).getOrderId();
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
        orderService.rejectOrder(orderid);

        // assert
        assertTrue(orderRepository.existsById(orderid));
        assertTrue(orderRepository.findById(orderid).isPresent());

        OrderItem item = orderRepository.findById(orderid).get();
        assertEquals("sessionId", item.getSessionId());
        assertEquals(OrderStatus.FAILURE, item.getStatus());
    }
}
