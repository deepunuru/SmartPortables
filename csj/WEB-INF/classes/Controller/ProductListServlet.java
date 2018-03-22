package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.Gson;
import DataAccess.MySQLDataStoreUtilities;
import JavaBeans.*;

public class ProductListServlet extends HttpServlet {
	
	MySQLDataStoreUtilities dbObj;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		try {
			dbObj = new MySQLDataStoreUtilities();
			String type = request.getParameter("typeVal");
			List<String> list = new ArrayList<String>();
			String json = null;
			switch(type) {
				case "selectType": {
					list.add("select Product");
					break;
				}
				case "smartwatches":{
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					SmartWatch watch = new SmartWatch();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
						for(Map.Entry<String, SmartWatch> w : dbObj.getWatchMapDB().entrySet()) {	
							watch = w.getValue();
							list.add(watch.getName());
						}
					}
					else {
						list.add("select Product");
					}
					break;
				}
				case "speakers":{
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					Speakers speaker = new Speakers();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
						for(Map.Entry<String, Speakers> w : dbObj.getSpeakerMapDB().entrySet()) {	
							speaker = w.getValue();
							list.add(speaker.getName());
						}
					}
					else {
						list.add("select Product");
					}
				}
				case "headphones":{
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					Headphones headphone = new Headphones();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
						for(Map.Entry<String, Headphones> w : dbObj.getHeadphoneMapDB().entrySet()) {	
							headphone = w.getValue();
							list.add(headphone.getName());
						}
					}
					else {
						list.add("select Product");
					}
				}
				case "smartphones":{
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					Phones phone = new Phones();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
						for(Map.Entry<String, Phones> w : dbObj.getPhoneMapDB().entrySet()) {	
							phone = w.getValue();
							list.add(phone.getName());
						}
					}
					else {
						list.add("select Product");
					}
				}
				case "laptops":{
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					Laptops laptop = new Laptops();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
						for(Map.Entry<String, Laptops> w : dbObj.getLaptopMapDB().entrySet()) {	
							laptop = w.getValue();
							list.add(laptop.getName());
						}
					}
					else {
						list.add("select Product");
					}
				}
				case "externalstorage":
				{
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					ExternalStorage storage = new ExternalStorage();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
						for(Map.Entry<String, ExternalStorage> w : dbObj.getStorageMapDB().entrySet()) {	
							storage = w.getValue();
							list.add(storage.getName());
						}
					}
					else {
						list.add("select Product");
					}
					
					break;
				}
				case "accessories":{
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					Accessories accessory = new Accessories();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
						for(Map.Entry<String, Accessories> w : dbObj.getAccessoryMapDB().entrySet()) {	
							accessory = w.getValue();
							list.add(accessory.getName());
						}
					}
					else {
						list.add("select accesssory");
					}
					break;
				}
				default:{
					list.add("select Product");
					break;
				}
			}
			json = new Gson().toJson(list);
			response.setContentType("application/json");
			response.getWriter().write(json);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}