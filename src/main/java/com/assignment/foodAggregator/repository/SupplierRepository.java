package com.assignment.foodAggregator.repository;

import java.util.HashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.assignment.foodAggregator.pojo.FoodItem;
import com.assignment.foodAggregator.pojo.FruitItem;
import com.assignment.foodAggregator.pojo.GrainItem;
import com.assignment.foodAggregator.pojo.VegetableItem;

@Repository
public class SupplierRepository {
	private static HashMap<String, FoodItem> localRepo = new HashMap<>();

	public static HashMap<String, FoodItem> getLocalRepo() {
		return localRepo;
	}

	public static void setLocalRepo(HashMap<String, FoodItem> localRepo) {
		SupplierRepository.localRepo = localRepo;
	}

	public FruitItem[] getFruitItems() {
		FruitItem[] fruitItems = new RestTemplate()
				.getForEntity("https://run.mocky.io/v3/c51441de-5c1a-4dc2-a44e-aab4f619926b", FruitItem[].class)
				.getBody();
		return fruitItems;

	}

	public VegetableItem[] getVegetableItems() {
		VegetableItem[] veggieItems = new RestTemplate()
				.getForEntity("https://run.mocky.io/v3/4ec58fbc-e9e5-4ace-9ff0-4e893ef9663c", VegetableItem[].class)
				.getBody();
		return veggieItems;
	}

	public GrainItem[] getGrainItems() {
		GrainItem[] grainItems = new RestTemplate()
				.getForEntity("https://run.mocky.io/v3/e6c77e5c-aec9-403f-821b-e14114220148", GrainItem[].class)
				.getBody();
		return grainItems;
	}
}
