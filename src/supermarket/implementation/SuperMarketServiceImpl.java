package supermarket.implementation;

import java.util.stream.Collectors;

import supermarket.exception.NotEnoughChangeException;
import supermarket.exception.PayNotAcceptedException;
import supermarket.exception.SoldOutException;
import supermarket.model.CashRegister;
import supermarket.model.Money;
import supermarket.model.Product;
import supermarket.model.ProductStorage;
import supermarket.service.SuperMarketService;

public class SuperMarketServiceImpl implements SuperMarketService {

	private static final SuperMarketServiceImpl INSTANCE = new SuperMarketServiceImpl();
	
//	********* PRODUCT MANAGEMENT ********************
	private ProductStorage productStorage = new ProductStorage();
	private Product soda = new Product("SODA", 2.3f);
	private Product bread = new Product("BREAD", 1.5f);
	private Product coffee = new Product("COFFEE", 3.4f);
	private Product milk = new Product("MILK", 0.9f);
	
	
//	******** CASH MANAGEMENT **************************
	private CashRegister cashRegistry = new CashRegister();
	
	public static SuperMarketService getInstance() {
		return INSTANCE;
	}
	
	private SuperMarketServiceImpl() {
	}
	
	@Override
	public void createProduct() {
		productStorage.storeProduct(soda, 2);
		productStorage.storeProduct(soda, 8);
		productStorage.storeProduct(soda, 52);
		productStorage.storeProduct(milk, 15);
		productStorage.storeProduct(bread, 18);
		productStorage.storeProduct(coffee, 8);
		productStorage.storeProduct(milk, 6);
	}

	@Override
	public void productInventory() {
		System.out.println(productStorage);
	}
	
	@Override
	public void addCash() {
		cashRegistry.storeMoney(Money.BILL_2, 20);
		cashRegistry.storeMoney(Money.BILL_2, 30);
		cashRegistry.storeMoney(Money.BILL_1, 10);
		cashRegistry.storeMoney(Money.BILL_1, 40);
		cashRegistry.storeMoney(Money.COIN_50, 50);
		cashRegistry.storeMoney(Money.COIN_10, 50);
	}

	@Override
	public void cashInventory() {
		System.out.println(cashRegistry);
	}

	@Override
	public void getAllProducts() {
		productStorage.getAllProducts().stream().forEach(System.out::print);
	}

	@Override
	public Float getParticularProductPrice(String productName) {
		return productStorage.getProductPrice(productName);
	}

	@Override
	public boolean productExist(String productName) {
		return productStorage.productExists(productName);
	}

	@Override
	public void changeCount(float change) throws NotEnoughChangeException {
		System.out.println( cashRegistry.getChange(change).entrySet().stream().map(s ->"Value " + s.getKey().getValue() + ", quantity: " + s.getValue())
				.collect(Collectors.joining("\n")));
		}
	
	@Override
	public void putMoney(float money) {
		cashRegistry.addMoney(money);
	}
 
	@Override
	public void sellTheProduct(String productName) throws SoldOutException {
		ProductStorage.sellTheProduct(productName);
	}

	@Override
	public boolean checkCoinBillValidity(float givenValue) throws PayNotAcceptedException {
		return cashRegistry.checkMoneyValidity(givenValue);
		
	}

	

	
}
