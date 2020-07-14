package com.assignment.foodAggregator.pojo;

public class FruitItem {
	private String id;
	private String name;
	private Integer quantity;
	private String price;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPrice() {
		return price;
	}

	public FoodItem toFoodItem() {
		FoodItem foodItem = new FoodItem();
		foodItem.setId(this.id);
		foodItem.setName(this.name);
		foodItem.setPrice(this.price);
		foodItem.setQuantity(this.quantity);
		return foodItem;
	}
}
