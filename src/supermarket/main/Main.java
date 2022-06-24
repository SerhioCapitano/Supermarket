package supermarket.main;

import java.text.DecimalFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

import supermarket.exception.NotEnoughChangeException;
import supermarket.exception.PayNotAcceptedException;
import supermarket.exception.SoldOutException;
import supermarket.implementation.SuperMarketServiceImpl;
import supermarket.model.Money;
import supermarket.service.SuperMarketService;


public class Main {
	
	
	public static void main(String[] args) throws NotEnoughChangeException, SoldOutException, PayNotAcceptedException {
		DecimalFormat floatFormat = new DecimalFormat("0.0");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
			SuperMarketService market = SuperMarketServiceImpl.getInstance();
			System.out.println("--------------------" + "\nINITIAL PRODUCT INVENTORY:");
			market.createProduct();
			market.productInventory();
			System.out.println("\nINITIAL CASH INVENTORY:");
			market.addCash();
			market.cashInventory();
			System.out.println("--------------------\n");
			
			while(true) {
				System.out.println("What would you like to buy? Type in the name of the desired product. Click ENTER if you'd like terminate shopping");
				market.getAllProducts();
				System.out.println("");
				String productName = scanner.nextLine();
				if(productName.equals("")) {
					break;
				}
				
				if(productName.isBlank() || !market.productExist(productName)) {
					throw new NoSuchElementException("You have just entered either empty name of the product or incorrect name");
				} else {
					System.out.println("You're trying to buy " + productName + ". You need to pay " + market.getParticularProductPrice(productName) + ".");
					
				}
				
				System.out.println("Provide bill or coin (accepted values: 0,1, 0,5, 1, 2)");
				float givenMonetaryInitial = 0;
				float finalChange = 0;
				while(market.getParticularProductPrice(productName) > givenMonetaryInitial ) {
					float givenMonetary = Float.parseFloat(scanner.nextLine());
					if(market.checkCoinBillValidity(givenMonetary) == false) {
						throw new PayNotAcceptedException("The coin or bill you gave is not acceptable. Dig in the pocket deeper and try find acceptable monetary.");
					}
					givenMonetaryInitial += givenMonetary;
					market.putMoney(givenMonetaryInitial);
					if(givenMonetaryInitial < market.getParticularProductPrice(productName)) {
						System.out.println("You paid " + givenMonetaryInitial + " in total. You still need to pay " + floatFormat.format((market.getParticularProductPrice(productName) - givenMonetary)));
					} else if(givenMonetary == market.getParticularProductPrice(productName)) {
						market.putMoney(givenMonetaryInitial);
						System.out.println("You paid " + givenMonetaryInitial + " in total. No change belongs to you.");
					}
					else {
						finalChange = givenMonetaryInitial - market.getParticularProductPrice(productName);
						System.out.println("You paid " + givenMonetaryInitial + " in total. Your change will be " + floatFormat.format(finalChange) + "\nHere is Your change: ");
					}
				}
				market.changeCount(finalChange);
				market.sellTheProduct(productName);
				System.out.println("Here is your product " + productName + ".");
				System.out.println(finalChange);
				System.out.println("--------------------" + "\nUPDATED PRODUCT INVENTORY:");
				market.productInventory();
				System.out.println("\nUPDATED CASH INVENTORY:");
				market.cashInventory();
				System.out.println("--------------------\n");
			}
			System.out.println("--------------------" + "\nYOUR SHOPPING TERMINATED. SEE UPDATED PRODUCT INVENTORY:");
			market.productInventory();
			System.out.println("\nUPDATED CASH INVENTORY:");
			market.cashInventory();
			System.out.println("--------------------\n");
	}
}
