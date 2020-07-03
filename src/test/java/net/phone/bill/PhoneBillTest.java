package net.phone.bill;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import net.phone.bill.billing.DataBundle;
import net.phone.bill.billing.PhoneCall;
import net.phone.bill.billing.SmsBundle;

class PhoneBillTest {

	@Test
	void shouldAcceptTypeOfPhoneCallAndIncrementTotal() {
		PhoneCall pc = new PhoneCall(30.25);
		PhoneBill pb = new PhoneBill();
		
		pb.accept(pc);
		
		assertEquals(30.25, pb.total());
	}
	
	@Test
	void shouldAcceptTypeOfSmsBundleAndIncrementTotal() {
		SmsBundle pc = new SmsBundle(5, 0.25);
		PhoneBill pb = new PhoneBill();
		
		pb.accept(pc);
		
		assertEquals(1.25, pb.total());
	}
	
	@Test
	void shouldAcceptTypeOfDataBundleAndIncrementTotal() {
		DataBundle pc = new DataBundle(100);
		PhoneBill pb = new PhoneBill();
		
		pb.accept(pc);
		
		assertEquals(75, pb.total());
	}

}
