package net.phone.bill.billing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DataBundleTest {

	@Test
	void shouldReturnZeroForNegativeNumberPassedToConstructor() {
		DataBundle db = new DataBundle(-455);
		assertEquals(0.00, db.totalCost());
	}
	
	@Test
	void shouldReturn374And25CentsForAmountOfData499() {
		DataBundle db = new DataBundle(499);
		assertEquals(374.25, db.totalCost());
	}
	
	@Test
	void shouldReturn549And45CentsForAmountOfData999() {
		DataBundle db = new DataBundle(999);
		assertEquals(549.45, db.totalCost());
	}
	
	@Test
	void shouldReturn350ForAmountOfData1000() {
		DataBundle db = new DataBundle(1000);
		assertEquals(350, db.totalCost());
	}
}
