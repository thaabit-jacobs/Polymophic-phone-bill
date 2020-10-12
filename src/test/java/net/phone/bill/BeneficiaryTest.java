package net.phone.bill;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.DriverManager;

import org.junit.jupiter.api.Test;

import net.phone.bill.beneficiary.Beneficiary;
import net.phone.bill.db.BillResources;

public class BeneficiaryTest {
    
    private Beneficiary ben = new Beneficiary("Thaabit", "0123456789");

    private BillResources br ;

    BeneficiaryTest() {

        try{
            Class.forName("org.postgresql.Driver");
            br = new BillResources(DriverManager.getConnection("jdbc:postgresql://localhost:5432/biller", "postgres", "1234"));
        }catch(Exception e)
        {
            System.out.println("Test constructor");
            e.printStackTrace();
        }

    }

    @Test
    void shouldReturnTrueWhenAddingNewBen() {
        assertTrue(br.addBeneficiary(ben));
        br.clearBenTable();
    }

    @Test
    void shoudlUpdateSmsTotalAndReturnIt(){
        ben.setSmsTotal(0.25);
        br.addBeneficiary(ben);
        assertEquals(0.25, br.updateSmsTotal(ben));
        br.clearBenTable();
    }

    @Test
    void shoudlUpdateDataTotalAndReturnIt(){
        ben.setDataTotal(50);
        br.addBeneficiary(ben);
        assertEquals(50, br.updateDataTotal(ben));
        br.clearBenTable();
    }

    @Test
    void shoudlUpdatePhoneTotalAndReturnIt(){
        ben.setPhoneTotal(100);
        br.addBeneficiary(ben);
        assertEquals(100, br.updatePhoneTotal(ben));
        br.clearBenTable();
    }

    @Test
    void shoudlUpdateBillTotalAndReturnIt(){
        ben.setBillTotal(150.25);
        br.addBeneficiary(ben);
        assertEquals(150.25, br.updateBillTotal(ben));
        br.clearBenTable();
    }

    @Test
    void shoudlDeleteBenReturnTrue(){
        br.addBeneficiary(ben);
        assertTrue(br.deleteBenficiary(ben));
        br.clearBenTable();
    }

    @Test
    void shoudlGetSmsTotalFromDb(){
        ben.setSmsTotal(25.25);
        br.addBeneficiary(ben);
        assertEquals(25.25, br.getSmsTotal(ben));
        br.clearBenTable();
    }

    @Test
    void shoudlGetDataTotalFromDb(){
        ben.setDataTotal(50);
        br.addBeneficiary(ben);
        assertEquals(50, br.getDataTotal(ben));
        br.clearBenTable();
    }

        @Test
    void shoudlGetPhoneTotalFromDb(){
        ben.setPhoneTotal(50);
        br.addBeneficiary(ben);
        assertEquals(50, br.getPhoneTotal(ben));
        br.clearBenTable();
    }

    @Test
    void shoudlGetBillTotalFromDb(){
        ben.setBillTotal(50);
        br.addBeneficiary(ben);
        assertEquals(50, br.getBillTotal(ben));
        br.clearBenTable();
    }
}
