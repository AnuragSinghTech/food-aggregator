package com.assignment.foodAggregator.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.foodAggregator.exception.CustomException;
import com.assignment.foodAggregator.pojo.FoodItem;
import com.assignment.foodAggregator.pojo.FruitItem;
import com.assignment.foodAggregator.pojo.GrainItem;
import com.assignment.foodAggregator.pojo.VegetableItem;
import com.assignment.foodAggregator.repository.SupplierRepository;

@Service
public class FoodService {
	@Autowired
	private SupplierRepository suppRepo;

	/*
	 * below three methods to fetch the items from supplier
	 */
	public Optional<FruitItem> getFruitItemFromSuppliers(String item) throws CustomException {
		FruitItem[] fruits = suppRepo.getFruitItems();
		Optional<FruitItem> fruitItem = Arrays.stream(fruits).filter(s -> s.getName().equals(item)).findAny();
		return fruitItem;
	}

	public Optional<VegetableItem> getVeggieItemFromSuppliers(String item) throws CustomException {
		VegetableItem[] vegetables = suppRepo.getVegetableItems();
		Optional<VegetableItem> vegItems = Arrays.stream(vegetables).filter(s -> s.getProductName().equals(item))
				.findAny();
		return vegItems;
	}

	public Optional<GrainItem> getGrainItemFromSuppliers(String item) throws CustomException {
		GrainItem[] grains = suppRepo.getGrainItems();
		Optional<GrainItem> grainItems = Arrays.stream(grains).filter(s -> s.getItemName().equals(item)).findAny();
		return grainItems;
	}

	public FoodItem getItemIfExists(String item) {
		Optional<FruitItem> fruitItem = getFruitItemFromSuppliers(item);
		if (fruitItem.isPresent()) {
			return fruitItem.get().toFoodItem();
		}
		Optional<VegetableItem> veggieItem = getVeggieItemFromSuppliers(item);
		if (veggieItem.isPresent()) {
			return veggieItem.get().toFoodItem();
		}
		Optional<GrainItem> grainItem = getGrainItemFromSuppliers(item);
		if (grainItem.isPresent()) {
			return grainItem.get().toFoodItem();
		}
		throw new CustomException("Item Not Found");
	}

	public FoodItem getItemIfQtyAvailable(String item, Integer qty) {
		FoodItem foodItem = getItemIfExists(item);
		if (foodItem.getQuantity() < qty) {
			throw new CustomException("Not Found");
		}
		return foodItem;
	}

	/* check if enough quantity is available for the given amount */
	public FoodItem getItemIfEnoughQtyAvailable(String item, Integer qty, Double price) {
		FoodItem foodItem = checkIfAvailableInCacheOrSupplier(item);
		String itemPrice = foodItem.getPrice().replace("$", "").trim();
		if (foodItem.getQuantity() > qty && qty * Double.valueOf(itemPrice) <= price) {
			return foodItem;
		}
		throw new CustomException("Item Not Found");
	}

	public FoodItem checkIfLocallyAvailable(String item) {
		FoodItem foodItem = SupplierRepository.getLocalRepo().get(item);
		return foodItem;
	}

	/*
	 * add to local repo if item is not available in it but available from supplier
	 */
	public FoodItem checkIfAvailableInCacheOrSupplier(String item) {
		FoodItem foodItem = checkIfLocallyAvailable(item);
		if (foodItem == null) {
			foodItem = getItemIfExists(item);
			SupplierRepository.getLocalRepo().put(foodItem.getName(), foodItem);
			return foodItem;
		}
		return foodItem;
	}

	public List<FoodItem> getSummary() {
		if (SupplierRepository.getLocalRepo().isEmpty()) {
			throw new CustomException("No Item Found in cache");
		}
		return SupplierRepository.getLocalRepo().entrySet().stream().map(s -> s.getValue())
				.collect(Collectors.toList());
	}

	/*
	 * parallel execution to fetch items from supplier and return the item back
	 */
	public FoodItem getItemIfExistsParallel(String item)
			throws CustomException, ExecutionException, InterruptedException {
		ExecutorService execSer = Executors.newFixedThreadPool(5);
		Future<Optional<FruitItem>> fruitFut = execSer.submit(() -> {
			return getFruitItemFromSuppliers(item);
		});
		Future<Optional<VegetableItem>> veggieFut = execSer.submit(() -> {
			return getVeggieItemFromSuppliers(item);
		});
		Future<Optional<GrainItem>> grainFut = execSer.submit(() -> {
			return getGrainItemFromSuppliers(item);
		});

		Optional<FruitItem> fruitItem = fruitFut.get();
		if (fruitItem.isPresent()) {
			return fruitItem.get().toFoodItem();
		}
		Optional<VegetableItem> veggieItem = veggieFut.get();
		if (veggieItem.isPresent()) {
			return veggieItem.get().toFoodItem();
		}
		Optional<GrainItem> grainItem = grainFut.get();
		if (grainItem.isPresent()) {
			return grainItem.get().toFoodItem();
		}
		throw new CustomException("Item Not Found");

	}
}
