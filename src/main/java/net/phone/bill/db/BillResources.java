package net.phone.bill.db;

import net.phone.bill.PhoneBill;
import net.phone.bill.billing.DataBundle;
import net.phone.bill.billing.PhoneCall;
import net.phone.bill.billing.SmsBundle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class BillResources {

    private final Connection connection;

    private PreparedStatement pstmt;
    private Statement stmt;

    private final String addSmsQuery = "INSERT INTO sms(price) VALUES(?)";
    private final String addDataQuery = "INSERT INTO data(price) VALUES(?)";
    private final String addPhoneQuery = "INSERT INTO phone(price) VALUES(?)";
    private final String clearSmsQuery = "DELETE FROM sms";
    private final String clearDataQuery = "DELETE FROM data";
    private final String clearPhoneQuery = "DELETE FROM phone";

    public BillResources(Connection connection) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Drivers not loaded in BillResources constructor");
            e.printStackTrace();
        }

        this.connection = connection;
    }

    public void addSms(SmsBundle s)
    {
        try
        {
            pstmt = connection.prepareStatement(addSmsQuery);

            pstmt.setDouble(1, s.totalCost());
            pstmt.executeUpdate();
            pstmt.close();
        } catch(SQLException se)
        {
            System.out.println(se + " : Could not add sms price");
            se.printStackTrace();
        }
    }

    public void addData(DataBundle d)
    {
        try
        {
            pstmt = connection.prepareStatement(addSmsQuery);

            pstmt.setDouble(1, d.totalCost());
            pstmt.executeUpdate();
            pstmt.close();
        } catch(SQLException se)
        {
            System.out.println(se + " : Could not add data price");
            se.printStackTrace();
        }
    }

    public void addPhone(PhoneCall p)
    {
        try
        {
            pstmt = connection.prepareStatement(addPhoneQuery);

            pstmt.setDouble(1, p.totalCost());
            pstmt.executeUpdate();
            pstmt.close();
        } catch(SQLException se)
        {
            System.out.println(se + " : Could not add phone price");
            se.printStackTrace();
        }
    }

    public void clearTables()
    {
        try
        {
            stmt = connection.createStatement();
            stmt.execute(clearDataQuery);
            stmt.execute(clearPhoneQuery);
            stmt.execute(clearSmsQuery);
            stmt.close();

        }catch(SQLException se)
        {
            System.out.println(se + " : Could not clear tables");
            se.printStackTrace();
        }
    }

}
