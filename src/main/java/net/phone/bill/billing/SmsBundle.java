package net.phone.bill.billing;

import java.util.Formatter;

public class SmsBundle implements BillAction{

	private int qty;
	
	private double price;
	
	public SmsBundle(int qty, double price) {
		
		if(qty > 0)
			this.qty = qty;
		else
			this.qty = 0;
		
		if(price > 0.00)
			this.price  = price;
		else
			this.price = 0.00;
		
	}
	
	public double totalCost() {
		Formatter df2 = new Formatter();
		
		double totalCost = qty * price;
		String doubleStr = df2.format("%.2f", totalCost).toString();
		
		df2.close();
		
		return new Double(doubleStr);
	}

}
