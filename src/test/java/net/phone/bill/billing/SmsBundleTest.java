package net.phone.bill.billing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SmsBundleTest {

	@Test
	void totalCostShouldBeZeroForZeroQty() {
		SmsBundle sb = new SmsBundle(0, 0.25);
		assertEquals(0.00, sb.totalCost());
	} 
	
	@Test
	void totalCostShouldBeZeroForZeroPrice() {
		SmsBundle sb = new SmsBundle(0, 0.25);
		assertEquals(0.00, sb.totalCost());
	} 
	
	@Test
	void shouldGetTotalOfSmsBundlePriceTimesTheQty() {
		SmsBundle sb = new SmsBundle(5, 0.25);
		assertEquals(1.25, sb.totalCost());
	} 
}
