package net.phone.bill.billing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PhoneCallTest {

	@Test
	void shouldSetPhoneCallPriceTozeroForInvalidAmount() {
		PhoneCall pc = new PhoneCall(-25.20);
		assertEquals(0.00, pc.totalCost());
	}
	
	@Test
	void shouldReturnAmountPassedInToConstructor() {
		PhoneCall pc = new PhoneCall(50.25);
		assertEquals(50.25, pc.totalCost());
	}
}
