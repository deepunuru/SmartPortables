package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import JavaBeans.Inventory;
import DataAccess.MySQLDataStoreUtilities;

import com.google.gson.Gson;

public class ChartServlet extends HttpServlet {
	MySQLDataStoreUtilities dbObj;
	
	public ChartServlet(){
		dbObj = new MySQLDataStoreUtilities();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Inventory inventory = new Inventory();
		List<Inventory> barChartList = new ArrayList<Inventory>();
		HashMap<String, String> dbStatus = new HashMap<String, String>();
		dbStatus = dbObj.checkDBConnection();
		if(dbStatus.get("status").equals("true")) {
			for(Map.Entry<String, Inventory> w : dbObj.getInventoryMapDB().entrySet()) {	
				inventory = w.getValue();
				barChartList.add(inventory);
				inventory = new Inventory();
			}
		}
		else {
			Inventory i1 = new Inventory("DBError","DBError",0.00,0,true,true);
			barChartList.add(i1);
		}
		/*
		Inventory i1 = new Inventory("Sample1","Sample1",22.32,6,true,true);
		barChartList.add(i1);
		Inventory i2 = new Inventory("Sample2","Sample2",0.00,8,true,true);
		barChartList.add(i2);
		Inventory i3 = new Inventory("Sample3","Sample3",0.00,10,true,true);
		barChartList.add(i3);
		Inventory i4 = new Inventory("Sample4","Sample4",0.00,25,true,true);
		barChartList.add(i4);
		Inventory i5 = new Inventory("Sample5","Sample5",0.00,16,true,true);
		barChartList.add(i5);*/
		Gson gson = new Gson();
		String jsonString = gson.toJson(barChartList);
		response.setContentType("application/json");
		response.getWriter().write(jsonString);
	}
}
