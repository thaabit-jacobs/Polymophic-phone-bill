package net.phone.bill.billing;

import java.util.Formatter;

public class DataBundle implements BillAction{
	
	private int data;
	
	public DataBundle(int data) {
		
		if(data > 0)
			this.data = data;
		else
			this.data = 0;
	}
	
	public double totalCost() {
		double totalCost = 0.00;

		if(data < 500) {
			totalCost = data * 0.75;
			
		} else if(data > 500 && data < 1000) {
			totalCost = data * 0.55;
			
		} else {
			totalCost = data * 0.35;
		}
		
		Formatter df2 = new Formatter();
		String doubleStr = df2.format("%.2f", totalCost).toString();
		
		df2.close();
		
		return new Double(doubleStr);
	}

}
