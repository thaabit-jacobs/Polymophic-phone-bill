package net.phone.bill;

import net.phone.bill.billing.BillAction;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.phone.bill.billing.DataBundle;
import net.phone.bill.billing.PhoneCall;
import net.phone.bill.billing.SmsBundle;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.*;

public class PhoneBill {
	
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

	public static void main(String[] args)
	{
		staticFiles.location("/public");

		PhoneBill bill = new PhoneBill();

		Map<String, Double> map = new HashMap<>();

		map.put("sms", 0.00);
		map.put("data", 0.00);
		map.put("phone", 0.00);

		get("/", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("total", "R" + bill.total());

			return  render(model, "index.hbs");
		});

		post("/", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("total", "R" + bill.total());

			String smsCost = request.queryParams("smsCost");
			String dataCost = request.queryParams("dataCost");
			String phoneCost = request.queryParams("phoneCost");

			if(smsCost != null)
			{
				BillAction purchase = new SmsBundle(Double.valueOf(smsCost).intValue(), 0.25);
				bill.accept(purchase);

				double currentCost = map.get("sms");
				currentCost += purchase.totalCost();
				map.put("sms", currentCost);
			}
			else if(dataCost != null)
			{
				BillAction purchase = new DataBundle(Double.parseDouble(dataCost));
				bill.accept(purchase);

				double currentCost = map.get("data");
				currentCost += purchase.totalCost();
				map.put("data", currentCost);
			}
			else
			{
				BillAction purchase = new PhoneCall(Double.parseDouble(phoneCost));
				bill.accept(purchase);

				double currentCost = map.get("phone");
				currentCost += purchase.totalCost();
				map.put("phone", currentCost);
			}


			model.put("total", "R" + bill.total());

			return  render(model, "index.hbs");
		});

		get("/billing", (request, response) -> {
			Map<String, Object> model = new HashMap<>();

			String dataArray = "[" + map.get("sms") + ", " + map.get("data") + ", " + map.get("phone") + "]";

			model.put("data",  dataArray);

			return  render(model, "chart.hbs");
		});

		get("/dashboard", (request, response) -> {
			Map<String, Object> model = new HashMap<>();

			List<String> beneficiaries = new ArrayList<>();
			beneficiaries.add("Thaabit");
			beneficiaries.add("Ayapha");

			model.put("greeted", beneficiaries);

			return  render(model, "dashboard.hbs");
		});

	}
}
