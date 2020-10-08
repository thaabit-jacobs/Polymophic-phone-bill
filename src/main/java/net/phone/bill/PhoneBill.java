package net.phone.bill;

import net.phone.bill.billing.BillAction;

import java.util.Formatter;
import java.util.HashMap;
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
				bill.accept(new SmsBundle(Double.valueOf(smsCost).intValue(), 0.25));
			else if(dataCost != null)
				bill.accept(new DataBundle(Double.parseDouble(dataCost)));
			else
				bill.accept(new PhoneCall(Double.parseDouble(phoneCost)));

			model.put("total", "R" + bill.total());

			return  render(model, "index.hbs");
		});
	}
}
