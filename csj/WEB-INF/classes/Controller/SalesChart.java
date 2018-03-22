package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import JavaBeans.Sales;
import DataAccess.MySQLDataStoreUtilities;

import com.google.gson.Gson;

public class SalesChart extends HttpServlet {
	MySQLDataStoreUtilities dbObj;
	
	public SalesChart() {
		dbObj = new MySQLDataStoreUtilities();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Sales sales = new Sales();
		List<Sales> salesList = new ArrayList<Sales>();
		HashMap<String, String> dbStatus = new HashMap<String, String>();
		dbStatus = dbObj.checkDBConnection();
		if(dbStatus.get("status").equals("true")) {
			for(Map.Entry<String, Sales> w : dbObj.getSalesMapDB().entrySet()) {	
				sales = w.getValue();
				salesList.add(sales);
				sales = new Sales();
			}
		}
		else {
			Sales i1 = new Sales("DBError","DBError",0.00,0,0.00,"DBError");
			salesList.add(i1);
		}
		Gson gson = new Gson();
		String jsonString = gson.toJson(salesList);
		response.setContentType("application/json");
		response.getWriter().write(jsonString);
	}
}