package net.phone.bill;

import net.phone.bill.billing.BillAction;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.*;

public class PhoneBill {
	
	private double totalCost;
	
	public void accept(BillAction bill) {
		totalCost += bill.totalCost();
	}
	
	public double total() {
		return totalCost;
	}

	public static String render(Map<String, Object> model, String hbsPath)
	{
		return new HandlebarsTemplateEngine().render(new ModelAndView(model, hbsPath));
	}

	public static void main(String[] args)
	{
		staticFiles.location("/public");

		get("/", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			//model.put("greet", "Hello world");
			


			return  render(model, "index.hbs");
		});
	}
}
