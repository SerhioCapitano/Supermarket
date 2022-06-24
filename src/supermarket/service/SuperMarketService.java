package supermarket.service;

import supermarket.exception.NotEnoughChangeException;
import supermarket.exception.PayNotAcceptedException;
import supermarket.exception.SoldOutException;


public interface SuperMarketService {
	
	void productInventory();
	void cashInventory();
	void createProduct();
	void addCash();
	void getAllProducts();
	Float getParticularProductPrice(String string);
	boolean productExist(String productName);
	void changeCount(float change) throws NotEnoughChangeException;
	void sellTheProduct(String productName) throws SoldOutException;
	boolean checkCoinBillValidity(float givenValue) throws PayNotAcceptedException;
	void putMoney(float money);
}
	

