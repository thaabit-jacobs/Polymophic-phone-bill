package net.phone.bill.beneficiary;

import net.phone.bill.PhoneBill;
import net.phone.bill.billing.BillAction;

public class Beneficiary{

    private String name;
    private String number;
    private double smsTotal;
    private double dataTotal;
    private double phoneTotal;
    private double billTotal;
    private final PhoneBill bill = new PhoneBill();

    public Beneficiary(String name, String number){
        this.name = name;
        this.number = number;
    }

    public String getName(){
        return name;
    }

    public String getNumber(){
        return number;
    }

    public double getSmsTotal(){
        return smsTotal;
    }

    public double getDataTotal(){
        return dataTotal;
    }

    public double getPhoneTotal(){
        return phoneTotal;
    }

    public double getBillTotal(){
        return this.billTotal;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public void setSmsTotal(double smsTotal)
    {
        this.smsTotal = smsTotal;
    }

    public void setDataTotal(double dataTotal)
    {
        this.dataTotal = dataTotal;
    }

    public void setPhoneTotal(double phoneTotal)
    {
        this.phoneTotal = phoneTotal;
    }

    public void setBillTotal(double billTotal)
    {
        this.billTotal = billTotal;
    }

    public void addSmsTot(BillAction smsCost)
    {
        smsTotal += smsCost.totalCost();
    }

    public void addDataTotal(BillAction dataCost)
    {
        dataTotal += dataCost.totalCost();
    }

    public void addPhoneTot(BillAction phoneCost)
    {
        phoneTotal+= phoneCost.totalCost();
    }

    public void addBillTot(BillAction purchase)
    {
        bill.accept(purchase);
    }

}