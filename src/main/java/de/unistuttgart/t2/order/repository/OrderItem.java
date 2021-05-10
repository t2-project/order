package de.unistuttgart.t2.order.repository;

import java.time.Instant;
import java.util.Date;

import org.springframework.data.annotation.Id;

/**
 * represents an order.
 * 
 * each oder has a unique orderId to find it in the repository, a sessionId to
 * correlate it to other things and a timestamp because of reasons.
 * 
 * @author sarah sophie stieß
 *
 */
public class OrderItem {
    @Id
    private String orderId;

    private String sessionId;
    private OrderStatus status;
    private Date timestamp;

    /**
     * used (and required) by spring framework.
     */
    public OrderItem() {
    }

    /**
     * create a new order.
     * 
     * there is no {@code orderId} because it gets set by repository. {@code status}
     * of a new order is always {@link OrderStatus#SUCCESS}, {@code timestamp} is
     * always the current time.
     * 
     * @param sessionId
     */
    public OrderItem(String sessionId) {
        super();
        this.sessionId = sessionId;
        this.status = OrderStatus.SUCCESS;
        this.timestamp = Date.from(Instant.now());
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("%s : %s , %s ", orderId, sessionId, timestamp.toString());
    }
}
