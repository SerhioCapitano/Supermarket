package supermarket.model;

import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import supermarket.exception.PayNotAcceptedException;
import supermarket.exception.NotEnoughChangeException;

public class CashRegister {
	private Map<Money, Integer> cashRegistry = new HashMap<Money, Integer>();

	public CashRegister() {

	}

	public void storeMoney(Money money, Integer amount) {
		if (!cashRegistry.containsKey(money)) {
			cashRegistry.putIfAbsent(money, amount);
		} else {
			cashRegistry.computeIfPresent(money, (key, val) -> val += amount);
		}
	}

	public boolean checkMoneyValidity(Float money) throws PayNotAcceptedException {
		return Arrays.stream(Money.values()).anyMatch(c -> c.getValue()==money);
	}

	public Map<Money, Integer> getChange(float change) throws NotEnoughChangeException {
		Map<Money, Integer> changeToReturn = new HashMap<Money, Integer>();
		float changeAmount = change;
		for (Money money : Money.values()) {
			if(money.getValue() <= changeAmount && cashRegistry.get(money) >= (int)(changeAmount / money.getValue())) {
				int countOfCoinBill = (int)(changeAmount / money.getValue());
				changeAmount = (float) (Math.round((changeAmount - money.getValue()*countOfCoinBill) * 10) /10.0);
				changeToReturn.put(money, countOfCoinBill);
				cashRegistry.computeIfPresent(money, (key, val) -> val - countOfCoinBill);	

			}				
		}
		if(changeAmount > 0) {
			throw new NotEnoughChangeException(
					"Apology, there is not sufficient amount of monetary to return you an exact change. Therefore transaction will be terminated. Please select other item.");
		}
		
	
		
		return changeToReturn;
	}

		
	
	public void addMoney(float m) {
		for(Money money : Money.values()) {
			if(money.getValue() == m) {
				cashRegistry.put(money, cashRegistry.get(money)+1);
			}
		}
				
			}
	

	
	

	public String toString() {
		return cashRegistry.entrySet().stream().map(s -> s.getKey().getValue() + " Quantity: " + s.getValue())
				.collect(Collectors.joining("\n"));
	}
	
	

}