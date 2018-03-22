package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import DataAccess.MySQLDataStoreUtilities;
import Controller.HtmlEngine;
import JavaBeans.*;

public class DealMatches extends HttpServlet {
	MySQLDataStoreUtilities dbObj;
	
	public DealMatches() {
		dbObj = new MySQLDataStoreUtilities();
	}
	
	@SuppressWarnings({"unchecked"})
	public HashMap<String, Product> readDealMatches(String pathName){
		try {
			//ArrayList<String> dealsList = new ArrayList<String>();
			HashMap<String, Product> selectedProducts = new HashMap<String, Product>();
			
			HashMap<String, String> dbStatus = new HashMap<String, String>();
			dbStatus = dbObj.checkDBConnection();
			if(dbStatus.get("status").equals("true")) {
				Product tempProduct = new Product();
				HashMap<String, Product> productMap = dbObj.getAjaxProductMapDB();
				if(productMap!=null) {
					for(Map.Entry<String, Product> p : productMap.entrySet()) {
						tempProduct = p.getValue();
						File fN = new File(pathName);
						if(fN.exists()==true) {
							BufferedReader reader = new BufferedReader(new FileReader(fN));
							try {
								String line = reader.readLine();
								if(line==null) {
									//dealsList.add("No Offers Found");
									selectedProducts.put("No Offers Found", null);
									break;
								}
								else {
									do {
										if(selectedProducts.size()<2 && !selectedProducts.containsKey(line) && !selectedProducts.containsValue(tempProduct)) {
											if(line.contains(tempProduct.getName())) {
												//dealsList.add(line);
												selectedProducts.put(line, tempProduct);
											}
										}
									}
									while((line = reader.readLine()) != null);
								}
							}
							catch(Exception ex) {
								ex.printStackTrace();
								selectedProducts.put("Invalid File Path", null);
								break;
							}
						}
						else {
							System.out.println("Invalid File Path");
							//dealsList.add("Invalid File Path");
							selectedProducts.put("Invalid File Path", null);
							break;
						}
					}
				}
				else {
					//dealsList.add("Product List is null");
					selectedProducts.put("Product List is null", null);
				}
			}
			else {
				//dealsList.add("MySQL server is not up and running");
				selectedProducts.put("MySQL server is not up and running", null);
			}
			return selectedProducts;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}