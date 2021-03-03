package de.unistuttgart.t2.domain;

import org.springframework.data.annotation.Id;

public class Order {
	@Id private String id;

	private String productId;
	private int productAmount;
	private OrderState state;
	
	private double total;
	
	
	public Order() {
	}
	
	public Order(String id, String productId, int productAmount, double total) {
		//TODO : check amount > 0
		this.id = id;
		this.productId = productId;
		this.productAmount = productAmount;
		this.total = total;
		
		// successfull until proven failed 
		this.state = OrderState.SUCCESS;
		
	}
	
	@Override
	public String toString() {
		return id + " -> ( " + productId + " [" + productAmount + " units] : " + total + ")"; 
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(int productAmount) {
		this.productAmount = productAmount;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
