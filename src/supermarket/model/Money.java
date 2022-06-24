package supermarket.model;

public enum Money {
	BILL_2(2.0f),
	BILL_1(1.0f),
	COIN_50(0.5f),
	COIN_10(0.1f);
	
	
	private float moneyValue;
	
	Money(float value){
		this.moneyValue = value;
	}
	
	public float getValue() {
		return this.moneyValue;
	}
	

}