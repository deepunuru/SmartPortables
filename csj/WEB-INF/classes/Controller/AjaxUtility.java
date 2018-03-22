package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import DataAccess.MySQLDataStoreUtilities;
import Controller.HtmlEngine;
import JavaBeans.*;

public class AjaxUtility extends HttpServlet {
	MySQLDataStoreUtilities dbObj;
	
	public AjaxUtility() {
		dbObj = new MySQLDataStoreUtilities();
	}
	
	public StringBuffer readAjaxMapData(String searchId) {
		try {
			HashMap<String, String> dbStatus = new HashMap<String, String>();
			StringBuffer sb = new StringBuffer();
			dbStatus = dbObj.checkDBConnection();
			if(dbStatus.get("status").equals("true")) {
				HashMap<String, Product>  productMap = dbObj.getAjaxProductMapDB();
				if(productMap == null || productMap.isEmpty()) {
					sb.append("<product>Inventory is empty</product>");
				}
				else {
					Product product = new Product();
					for(Map.Entry<String, Product> w : productMap.entrySet()) {
						product = w.getValue();
						if(product.getName().toLowerCase().startsWith(searchId)) {
							sb.append("<product>");
							sb.append("<id>" + product.getId() + "</id>");
							sb.append("<productName>" + product.getName() + "</productName>");
							sb.append("<productType>" + product.getType() + "</productType>");
							sb.append("</product>");
						}
					}
				}
			}
			else {
				sb.append("<product>" + dbStatus.get("msg") + "</product>");
			}
			return sb;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String searchId = request.getParameter("searchId");
			String action = request.getParameter("action");
			StringBuffer sb = new StringBuffer();
			boolean flag = false;
			if(action.equals("complete")) {
				if(!searchId.equals("")) {
					sb = readAjaxMapData(searchId);
					if(sb!=null || !sb.equals("")) {
						flag = true;
					}
					if(flag) {
						response.setContentType("text/xml");
						response.getWriter().write("<products>" + sb.toString() + "</products>");
					}
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}