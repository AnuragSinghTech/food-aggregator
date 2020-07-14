package com.assignment.foodAggregator.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.assignment.foodAggregator.exception.CustomException;
import com.assignment.foodAggregator.pojo.FoodItem;
import com.assignment.foodAggregator.service.FoodService;

@Controller
public class FoodServiceController {
	@Autowired
	private FoodService foodServ;

	/*
	 * Endpoint 1
	 */
	@GetMapping("/buy-item/item/{item}")
	public ResponseEntity<FoodItem> getItems(@PathVariable String item) throws CustomException {
		FoodItem foodItem = foodServ.getItemIfExists(item);
		return new ResponseEntity<FoodItem>(foodItem, HttpStatus.OK);
	}

	/*
	 * Endpoint 2
	 */
	@GetMapping("/buy-item-qty/item/{item}/qty/{qty}")
	public ResponseEntity<FoodItem> getItemsIfQtyAvailable(@PathVariable String item, @PathVariable Integer qty)
			throws CustomException {
		FoodItem foodItem = foodServ.getItemIfQtyAvailable(item, qty);
		return new ResponseEntity<FoodItem>(foodItem, HttpStatus.OK);
	}

	/*
	 * Endpoint 3
	 */

	@GetMapping("/buy-item-qty-price/item/{item}/qty/{qty}/price/{price}")
	public ResponseEntity<FoodItem> getItemsIfAvailableForPrice(@PathVariable String item, @PathVariable Integer qty,
			@PathVariable Double price) throws CustomException {
		FoodItem foodItem = foodServ.getItemIfEnoughQtyAvailable(item, qty, price);
		return new ResponseEntity<FoodItem>(foodItem, HttpStatus.OK);
	}

	/*
	 * Endpoint 4
	 */

	@GetMapping("/show-summary")
	public ResponseEntity<List<FoodItem>> getSummary() throws CustomException {
		List<FoodItem> foodItem = foodServ.getSummary();
		return new ResponseEntity<List<FoodItem>>(foodItem, HttpStatus.OK);
	}

	/*
	 * Endpoint 5
	 */

	@GetMapping("/fast-buy-item/item/{item}")
	public ResponseEntity<FoodItem> getItemFastBuy(@PathVariable String item)
			throws CustomException, ExecutionException, InterruptedException {
		FoodItem foodItem = foodServ.getItemIfExistsParallel(item);
		return new ResponseEntity<FoodItem>(foodItem, HttpStatus.OK);
	}

}
