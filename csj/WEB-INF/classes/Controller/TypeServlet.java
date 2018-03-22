package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.Gson;
import DataAccess.MySQLDataStoreUtilities;
import JavaBeans.Accessories;

public class TypeServlet extends HttpServlet {
	
	MySQLDataStoreUtilities dbObj;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		try {
			dbObj = new MySQLDataStoreUtilities();
			Accessories accessory = new Accessories();
			String type = request.getParameter("typeVal");
			List<String> list = new ArrayList<String>();
			String json = null;
			switch(type) {
				case "selectType": {
					list.add("select accesssory");
					break;
				}
				case "smartwatches":
				case "speakers":
				case "headphones":
				case "smartphones":
				case "laptops":
				case "externalstorage":
				{
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
						for(Map.Entry<String, Accessories> w : dbObj.getAccessoryMapDB().entrySet()) {	
							accessory = w.getValue();
							if(type.equals(accessory.getType())) {
								list.add(accessory.getId());
							}
						}
					}
					else {
						list.add("select accesssory");
					}
					
					break;
				}
				case "accessories":{
					list.add("Not Applicable");
					break;
				}
				default:{
					list.add("select accessory");
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