package net.phone.bill.billing;

public class PhoneCall implements BillAction{
	
	private double phoneCallPrice;
	
	public PhoneCall(double phoneCallPrice) {
		
		if(phoneCallPrice > 0.00) 
			this.phoneCallPrice = phoneCallPrice;
		else
			this.phoneCallPrice = 0.00;
	}
	
	public double totalCost() {
		return phoneCallPrice;
	}
	
}
