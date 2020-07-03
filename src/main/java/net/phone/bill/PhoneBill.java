package net.phone.bill;

import net.phone.bill.billing.BillAction;

public class PhoneBill {
	
	private double totalCost;
	
	public void accept(BillAction bill) {
		totalCost += bill.totalCost();
	}
	
	public double total() {
		return totalCost;
	}
}
