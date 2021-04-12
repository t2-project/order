package de.unistuttgart.t2.repository;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class OrderItem {
	@Id private String orderId;

	private String sessionId;
	private OrderStatus status;
	private Date timestamp;
	

	public OrderItem() { }

	public OrderItem(String sessionId, OrderStatus status, Date timestamp) {
		super();
		this.sessionId = sessionId;
		this.status = status;
		this.timestamp = timestamp;
	}
	
	public OrderItem(String orderId, String sessionId, OrderStatus status, Date timestamp) {
		super();
		this.orderId = orderId;
		this.sessionId = sessionId;
		this.status = status;
		this.timestamp = timestamp;
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
		return orderId + " -> ( " + sessionId + ", " + timestamp.toGMTString() + ")"; 
	}

}
