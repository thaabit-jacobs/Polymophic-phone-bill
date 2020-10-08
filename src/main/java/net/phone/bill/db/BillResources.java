package net.phone.bill.db;

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
            
        }
    }

}
