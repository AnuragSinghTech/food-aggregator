package com.assignment.foodAggregator.pojo;

public class VegetableItem {
	private String productId;
	private String productName;
	private Integer quantity;
	private String price;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public FoodItem toFoodItem() {
		FoodItem foodItem = new FoodItem();
		foodItem.setId(this.productId);
		foodItem.setName(this.productName);
		foodItem.setPrice(this.price);
		foodItem.setQuantity(this.quantity);
		return foodItem;
	}
}
