package net.phone.bill;

import net.phone.bill.beneficiary.Beneficiary;
import net.phone.bill.billing.BillAction;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.phone.bill.billing.DataBundle;
import net.phone.bill.billing.PhoneCall;
import net.phone.bill.billing.SmsBundle;
import net.phone.bill.db.BillResources;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.*;

public class PhoneBill {

	public static String purchaseName;
	public static String billingName;
	
	private double totalCost;
	
	public void accept(BillAction bill) {
		totalCost += bill.totalCost();
	}
	
	public double total() {
		Formatter df2 = new Formatter();
		String doubleStr = df2.format("%.2f", totalCost).toString().replace(",", ".");

		df2.close();

		return Double.parseDouble(doubleStr);
	}

	public static String render(Map<String, Object> model, String hbsPath)
	{
		return new HandlebarsTemplateEngine().render(new ModelAndView(model, hbsPath));
	}

	public static void main(String[] args) throws SQLException {
		staticFiles.location("/public");

		PhoneBill bill = new PhoneBill();
		BillResources br = new BillResources(DriverManager.getConnection("jdbc:postgresql://localhost:5432/biller", "thaabit", "1234"));

		br.resetTable();

		try{
			Class.forName("org.postgresql.Driver");

		}catch(Exception e)
		{
			System.out.println("Test constructor");
			e.printStackTrace();
		}

		Map<String, Double> map = new HashMap<>();

		map.put("sms", 0.00);
		map.put("data", 0.00);
		map.put("phone", 0.00);

		get("/", (request, response) -> {
			Map<String, Object> model = new HashMap<>();

			ArrayList<String> benNameList = new ArrayList<>();
			ArrayList<Beneficiary> beneficiaries = br.getAllBeneficiary();

			for(Beneficiary b: beneficiaries)
				benNameList.add(b.getName() + " : " + b.getNumber());

			model.put("greeted", benNameList);

			return  render(model, "dashboard.hbs");
		});

		post("/", (request, response) -> {
			Map<String, Object> model = new HashMap<>();

			ArrayList<String> benNameList = new ArrayList<>();
			ArrayList<Beneficiary> beneficiaries = br.getAllBeneficiary();

			for(Beneficiary b: beneficiaries)
				benNameList.add(b.getName() + " " + b.getNumber());

			model.put("greeted", benNameList);

			return  render(model, "dashboard.hbs");
		});

		get("/purchaseF", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			ArrayList<String> benNameList = new ArrayList<>();
			ArrayList<Beneficiary> beneficiaries = br.getAllBeneficiary();

			for(Beneficiary b: beneficiaries)
				benNameList.add(b.getName() + " : " + b.getNumber());

			model.put("greeted", benNameList);
			return  render(model, "purchaseForm.hbs");
		});


		post("/purchaseF", (request, response) -> {
			purchaseName = request.queryParams("name");
			System.out.println(purchaseName);

			response.redirect("/purchase");

			return "";
		});

		get("/purchase", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("total", "R" + br.getBillTotal(br.getBeneficiary(purchaseName)));

			return  render(model, "purchase.hbs");
		});

		post("/purchase", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			Beneficiary ben = br.getBeneficiary(purchaseName);

			String smsCost = request.queryParams("smsCost");
			String dataCost = request.queryParams("dataCost");
			String phoneCost = request.queryParams("phoneCost");

			if(smsCost != null)
			{
				BillAction purchase = new SmsBundle(Double.valueOf(smsCost).intValue(), 0.25);
				bill.accept(purchase);

				double currentCost = map.get("sms");
				currentCost += purchase.totalCost();

				ben.addSmsTot(purchase);
				ben.addBillTot(purchase);
				br.updateSmsTotal(ben);
				br.updateBillTotal(ben);

				map.put("sms", currentCost);
			}
			else if(dataCost != null)
			{
				BillAction purchase = new DataBundle(Double.parseDouble(dataCost));
				bill.accept(purchase);

				double currentCost = map.get("data");
				currentCost += purchase.totalCost();

				ben.addDataTotal(purchase);
				ben.addBillTot(purchase);
				br.updateDataTotal(ben);
				br.updateBillTotal(ben);

				map.put("data", currentCost);
			}
			else
			{
				BillAction purchase = new PhoneCall(Double.parseDouble(phoneCost));
				bill.accept(purchase);

				double currentCost = map.get("phone");
				currentCost += purchase.totalCost();

				ben.addPhoneTot(purchase);
				ben.addBillTot(purchase);
				br.updatePhoneTotal(ben);
				br.updateBillTotal(ben);

				map.put("phone", currentCost);
			}


			model.put("total", "R" + bill.total());
			br.updateBillTotal(ben);

			response.redirect("/");

			return  render(model, "purchase.hbs");
		});

		get("/billingF", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			ArrayList<String> benNameList = new ArrayList<>();
			ArrayList<Beneficiary> beneficiaries = br.getAllBeneficiary();

			for(Beneficiary b: beneficiaries)
				benNameList.add(b.getName() + " : " + b.getNumber());

			model.put("greeted", benNameList);
			return  render(model, "billingForm.hbs");
		});

		post("/billingF", (request, response) -> {
			billingName = request.queryParams("name");
			System.out.println(billingName);

			response.redirect("/billing");

			return "";
		});

		get("/billing", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			Beneficiary ben = br.getBeneficiary(billingName);

			String dataArray = "[" + br.getSmsTotal(ben) + ", " + br.getDataTotal(ben) + ", " + br.getPhoneTotal(ben) + "]";

			model.put("data",  dataArray);

			return  render(model, "chart.hbs");
		});

		get("/add", (request, response) -> {
			Map<String, Object> model = new HashMap<>();

			ArrayList<String> benNameList = new ArrayList<>();
			ArrayList<Beneficiary> beneficiaries = br.getAllBeneficiary();

			for(Beneficiary b: beneficiaries)
				benNameList.add(b.getName() + " : " + b.getNumber());

			model.put("greeted", benNameList);

			return  render(model, "addForm.hbs");
		});

		post("/add", (request, response) -> {
			String name = request.queryParams("name");
			String number = request.queryParams("number");

			br.addBeneficiary(new Beneficiary(name, number));

			response.redirect("/");

			return  "";
		});

		get("/delete", (request, response) -> {
			Map<String, Object> model = new HashMap<>();

			ArrayList<String> benNameList = new ArrayList<>();
			ArrayList<Beneficiary> beneficiaries = br.getAllBeneficiary();

			for(Beneficiary b: beneficiaries)
				benNameList.add(b.getName());

			model.put("greeted", benNameList);

			return  render(model, "deleteForm.hbs");
		});

		post("/delete", (request, response) -> {
			Map<String, Object> model = new HashMap<>();

			String name = request.queryParams("name");

			br.deleteBenficiary(br.getBeneficiary(name));

			response.redirect("/");

			return  "";
		});

		get("/purchase", (request, response) -> {
			Map<String, Object> model = new HashMap<>();

			return  render(model, "purchase.hbs");
		});

		get("/form", (request, response) -> {
			Map<String, Object> model = new HashMap<>();

			ArrayList<String> benNameList = new ArrayList<>();
			ArrayList<Beneficiary> beneficiaries = br.getAllBeneficiary();

			for(Beneficiary b: beneficiaries)
				benNameList.add(b.getName());

			model.put("greeted", benNameList);

			return  render(model, "benForm.hbs");
		});

	}
}
