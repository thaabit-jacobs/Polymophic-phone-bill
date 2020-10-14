package net.phone.bill.db;

import net.phone.bill.PhoneBill;
import net.phone.bill.billing.DataBundle;
import net.phone.bill.billing.PhoneCall;
import net.phone.bill.billing.SmsBundle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.phone.bill.beneficiary.*;


public class BillResources {

    private final Connection connection;

    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet rs;
    
    private final String addBenQuery = "INSERT INTO beneficiaries(name, number, sms_total, data_total, phone_total, total_bill) values(?, ?, ?, ?, ?, ?)";
    private final String clearBenTableQuery = "DELETE FROM beneficiaries";
    private final String updateSmsQuery = "UPDATE beneficiaries SET sms_total = ? WHERE name = ?";
    private final String  updateDataQuery = "UPDATE beneficiaries SET data_total = ? WHERE name = ?";
    private final String  updatePhoneQuery = "UPDATE beneficiaries SET phone_total = ? WHERE name = ?";
    private final String  updateTotalBillQuery = "UPDATE beneficiaries SET total_bill = ? WHERE name = ?";
    private final String  deleteBenefiaciaryQuery = "DELETE FROM beneficiaries WHERE name = ?";
    private final String  benefiaciariesQuery = "SELECT * FROM beneficiaries WHERE name = ?";
    private final String  getAllBenQuery = "SELECT * FROM beneficiaries";
    private final String  tableReset = "UPDATE beneficiaries SET sms_total=0.00, data_total=0.00, phone_total=0.00, total_bill=0.00";

    public BillResources(Connection connection) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Drivers not loaded in BillResources constructor");
            e.printStackTrace();
        }

        this.connection = connection;
    }

    public Beneficiary getBeneficiary(String name){
        Beneficiary ben = null;

        try{
            pstmt = connection.prepareStatement(benefiaciariesQuery);
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();
            rs.next();

            ben = new Beneficiary(name, rs.getString("number"));
            ben.setSmsTotal(rs.getDouble("sms_total"));
            ben.setDataTotal(rs.getDouble("data_total"));
            ben.setPhoneTotal(rs.getDouble("phone_total"));
            ben.setBillTotal(rs.getDouble("total_bill"));
            rs.close();

            return ben;
        }catch(SQLException se){
            System.out.println("Could not return benefiaiciry");
            se.printStackTrace();
        }

        return null;
    }

    public ArrayList<Beneficiary> getAllBeneficiary(){
        ArrayList<Beneficiary> list  = new ArrayList<>();
        Beneficiary ben = null;

        try{

            stmt = connection.createStatement();
            rs = stmt.executeQuery(getAllBenQuery);

            while(rs.next()){
                ben = new Beneficiary(rs.getString("name"), rs.getString("number"));
                ben.setSmsTotal(rs.getDouble("sms_total"));
                ben.setDataTotal(rs.getDouble("data_total"));
                ben.setPhoneTotal(rs.getDouble("phone_total"));
                ben.setBillTotal(rs.getDouble("total_bill"));

                list.add(ben);
            };

            stmt.close();
            rs.close();

            return list;
        }catch(SQLException se){
            System.out.println("Could not return all beneficiaries");
            se.printStackTrace();
        }

        return list;
    }

    public double getSmsTotal(Beneficiary ben)
    {
        try
        {
            pstmt = connection.prepareStatement(benefiaciariesQuery);
            pstmt.setString(1, ben.getName());
            rs = pstmt.executeQuery();
            rs.next();

            double smsTotal = rs.getDouble("sms_total");

            rs.close();
            pstmt.close();

            return smsTotal;
        } catch(SQLException se){
            System.out.println("Could not returb sms total");
            se.printStackTrace();
        }

        return 0;
    }

    public double getDataTotal(Beneficiary ben)
    {
        try
        {
            pstmt = connection.prepareStatement(benefiaciariesQuery);
            pstmt.setString(1, ben.getName());
            rs = pstmt.executeQuery();
            rs.next();

            double dataTotal = rs.getDouble("data_total");
            
            rs.close();
            pstmt.close();

            return dataTotal;
        } catch(SQLException se){
            System.out.println("Could not returb data total");
            se.printStackTrace();
        }

        return 0;
    }

    public double getPhoneTotal(Beneficiary ben)
    {
        try
        {
            pstmt = connection.prepareStatement(benefiaciariesQuery);
            pstmt.setString(1, ben.getName());
            rs = pstmt.executeQuery();
            rs.next();

            double phoneTotal = rs.getDouble("phone_total");
            
            rs.close();
            pstmt.close();

            return phoneTotal;
        } catch(SQLException se){
            System.out.println("Could not returb phone total");
            se.printStackTrace();
        }

        return 0;
    }

        public double getBillTotal(Beneficiary ben)
    {
        try
        {
            pstmt = connection.prepareStatement(benefiaciariesQuery);
            pstmt.setString(1, ben.getName());
            rs = pstmt.executeQuery();
            rs.next();

            double billTotal = rs.getDouble("total_bill");
            
            rs.close();
            pstmt.close();

            return billTotal;
        } catch(SQLException se){
            System.out.println("Could not returb billTotal total");
            se.printStackTrace();
        }

        return 0;
    }

    public boolean addBeneficiary(Beneficiary ben)
    {
        try{
            pstmt = connection.prepareStatement(addBenQuery);
            pstmt.setString(1, ben.getName());
            pstmt.setString(2, ben.getNumber());
            pstmt.setDouble(3, ben.getSmsTotal());
            pstmt.setDouble(4, ben.getDataTotal());
            pstmt.setDouble(5, ben.getPhoneTotal());
            pstmt.setDouble(6, ben.getBillTotal());
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch(SQLException se){
            System.out.println("n the catch");
            System.out.println("Could not add user");
            se.printStackTrace();
        }

        return false;
    }

    public double updateSmsTotal(Beneficiary ben)
    {
        double updatedSmsTotal = ben.getSmsTotal();
        try{
            pstmt = connection.prepareStatement(updateSmsQuery);
            pstmt.setDouble(1, ben.getSmsTotal());
            pstmt.setString(2, ben.getName());
            pstmt.executeUpdate();
            pstmt.close();
            return updatedSmsTotal;
        } catch(SQLException se){
            System.out.println("n the catch");
            System.out.println("Could not add user");
            se.printStackTrace();
        }

        return 0;
    }

    public double updateDataTotal(Beneficiary ben)
    {
        double updatedDataTotal = ben.getDataTotal();

        try{
            pstmt = connection.prepareStatement(updateDataQuery);
            pstmt.setDouble(1, ben.getDataTotal());
            pstmt.setString(2, ben.getName());
            pstmt.executeUpdate();
            pstmt.close();
            return updatedDataTotal;
        } catch(SQLException se){
            System.out.println("n the catch");
            System.out.println("Could not add user");
            se.printStackTrace();
        }

        return 0;
    }

    public double updatePhoneTotal(Beneficiary ben)
    {
        double updatedPhoneTotal = ben.getPhoneTotal();

        try{
            pstmt = connection.prepareStatement(updatePhoneQuery);
            pstmt.setDouble(1, ben.getPhoneTotal());
            pstmt.setString(2, ben.getName());
            pstmt.executeUpdate();
            pstmt.close();
            return updatedPhoneTotal;
        } catch(SQLException se){
            System.out.println("n the catch");
            System.out.println("Could not add user");
            se.printStackTrace();
        }

        return 0;
    }

    public double updateBillTotal(Beneficiary ben)
    {
        double updatedBillTotal = ben.getBillTotal();

        try{
            pstmt = connection.prepareStatement(updateTotalBillQuery);
            pstmt.setDouble(1, ben.getBillTotal());
            pstmt.setString(2, ben.getName());
            pstmt.executeUpdate();
            pstmt.close();
            return updatedBillTotal;
        } catch(SQLException se){
            System.out.println("n the catch");
            System.out.println("Could not add user");
            se.printStackTrace();
        }

        return 0;
        
    }

        public boolean deleteBenficiary(Beneficiary ben)
    {
        try{
            pstmt = connection.prepareStatement(deleteBenefiaciaryQuery);
            pstmt.setString(1, ben.getName());
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch(SQLException se){
            System.out.println("n the catch");
            System.out.println("Could not add user");
            se.printStackTrace();
        }

        return false;
        
    }

    public boolean clearBenTable(){
        try{
            stmt = connection.createStatement();
            stmt.executeUpdate(clearBenTableQuery);
            stmt.close();

            return true;
        } catch(SQLException se){
            System.out.println("n the catch");
            System.out.println("Could not add user");
            se.printStackTrace();
        }

        return false;
    }

    public boolean resetTable(){
        try{
            stmt = connection.createStatement();
            stmt.executeUpdate(tableReset);
            stmt.close();

            return true;
        } catch(SQLException se){
            System.out.println("n the catch");
            System.out.println("Could not add user");
            se.printStackTrace();
        }

        return false;
    }

}