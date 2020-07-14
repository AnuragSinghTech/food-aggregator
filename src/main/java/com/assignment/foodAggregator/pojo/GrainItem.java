package com.assignment.foodAggregator.pojo;

public class GrainItem {
	private String itemId;
	private String itemName;
	private Integer quantity;
	private String price;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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
		foodItem.setId(this.itemId);
		foodItem.setName(this.itemName);
		foodItem.setPrice(this.price);
		foodItem.setQuantity(this.quantity);
		return foodItem;
	}
}
