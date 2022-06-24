package supermarket.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import supermarket.exception.SoldOutException;

public class ProductStorage {
	private static Map<Product, Integer> storage = new HashMap<Product, Integer>();

	public ProductStorage() {

	}

	public static Integer getAmount(String product) {
		for (Product productElement : storage.keySet()) {
			if (productElement.getProductName().equals(product)) {
				return storage.get(productElement);
			}
		}
		throw new NullPointerException("There is no such product in storage.");
	}

	public void storeProduct(Product product, Integer amount) {
		if(!storage.containsKey(product)) {
			storage.putIfAbsent(product, amount);
			} else {
				storage.computeIfPresent(product, (key, val) -> val += amount);
				}
			}
	

	public static void sellTheProduct(String product) throws SoldOutException {
		for (Product productItem : storage.keySet()) {
			if (productItem.getProductName().equals(product) && storage.get(productItem) > 0) {
				storage.computeIfPresent(productItem, (key, value) -> value - 1);
			} else if(productItem.getProductName().equals(product) && storage.get(productItem) == 0) {
				throw new SoldOutException(
				"Sorry, the product you want either has been already sold or amount is insufficient.");
			}
		}
	}

	public String toString() {
		return storage.entrySet().stream().map(s -> s.getKey().getProductName() + " Quantity: " + s.getValue())
				.collect(Collectors.joining("\n"));
	}
	
	public Collection<Product> getAllProducts(){
		return storage.keySet();
	}
	
	public Float getProductPrice(String productName) {
			return storage.keySet().stream().filter(p -> p.getProductName().equals(productName)).findFirst().get().getPrice();
		
	}
	
	public boolean productExists(String productName) {
		return storage.keySet().stream().filter(p -> p.getProductName().equals(productName)).findFirst().isPresent();
	}
}
