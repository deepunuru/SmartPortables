package Controller;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import Controller.HtmlEngine;
import DataAccess.MySQLDataStoreUtilities;
import JavaBeans.*;

public class DeleteOrder extends HttpServlet {
	public static MySQLDataStoreUtilities dbObj = new MySQLDataStoreUtilities();
	public static HashMap<String, Orders> ordersHashMap;
	
	public DeleteOrder() {
		ordersHashMap = new HashMap<String, Orders>();
	}
	
	public void displayLogoutHeader(PrintWriter pWriter) {
		String docType = "<!doctype html>\n";
		pWriter.println(docType + 
		"<html>\n" +
            "<head>\n" +
            "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />\n" +
            "<title>Smart Portables</title>\n" +
            "<link rel='stylesheet' href='/csj/styles.css' type='text/css' />\n" +
            "<link rel='stylesheet' href='/csj/style.css' type='text/css' />\n" +
            "<meta charset='utf-8'>\n" +
            "<meta name='viewport' content='width=device-width, initial-scale=1'>\n" +
            "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>\n" + 
            "<script type='text/javascript' src='/csj/autoCompleteJS.js'></script>\n" +
            "<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>\n" + 
            "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>\n" +
            "<!--[if lt IE 9]>\n" +
            "<script src='http://html5shiv.googlecode.com/svn/trunk/html5.js'></script>\n" +
            "<![endif]-->\n" +
            "<meta name='viewport' content='width=device-width, minimum-scale=1.0, maximum-scale=1.0' />\n" +
            "</head>\n" +
            "<body onload='init()'>\n" +
            "<div id='container'>\n" +
            	"<header>\n" + 
            		"<a href='/csj/Controller/HomeServlet?var=home' method='get' class='topic'>Smart Portables</a>\n" +
            		"<h5>You won't find a better deal anywhere else.</h5>\n" +
            		"<a href='/csj/Controller/LogoutServlet' method='get' class='reg'>Logout</a>\n" +
            	"</header>\n" +
            	"<nav>\n" +
            		"<div class='width'>\n" +
            		"<table id='navTable'>\n" +
            		"<tr>\n" +
						"<td style='width:725px'>\n" +
            			"<ul>\n" +
            				"<li class='start selected'><a href='/csj/Controller/HomeServlet?var=home' method='get'>Home</a></li>\n" +
            				"<li><a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a></li>\n" +
            				"<li><a href='/csj/Controller/HomeServlet?var=accessories' method='get'>Accessories</a></li>\n" +
            				"<li class='end'><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>\n" +
            			"</ul>\n" +
            			"</td>\n" +
            			"<td style='width:75px;'>\n" +
            			"</td>\n" +
            		"</table>\n" +
            		"</div>\n" +
				"</nav>");
	}
	
	public void displayOrderList(PrintWriter pWriter,String action) {
		HashMap<String, String> dbStatus = new HashMap<String, String>();
		dbStatus = dbObj.checkDBConnection();
		if(dbStatus.get("status").equals("true")) {
			ordersHashMap = dbObj.getOrders();
			
			Orders order = new Orders();
			pWriter.println("<div id='body' class='width'>\n" +
					"<section id='content'>\n" +
					"<div style='height:50px;'></div>\n" +
					"<form class='register' action='' method='get'>\n" +
						"<div class='form_container'>\n" +
							"<table id='OrderListTable'>\n" +
								"<tr>\n" +	
									"<th colspan='4'><div align='center'>Order List</div></th>\n" +
								"</tr>\n" +
								"<tr>\n" +	
									"<td colspan='4' style='text-align:center;'>\n" +
									"</td>\n" +
								"</tr>");
			
			if(ordersHashMap == null) {
				pWriter.println("<tr>\n" +	
						"<td colspan='4' style='text-align:center;'>\n" +
							"<label for='orderStatusLbl'>No Orders to Display</label>\n" +
						"</td>\n" +
					"</tr>");
			}
			else {
				for(Map.Entry<String, Orders> o : ordersHashMap.entrySet()) {
					order = o.getValue();
				pWriter.println("<tr style='border: 1px solid #ccc;border-radius: 5px;'>\n" +
						"<td style='text-align:left;'>\n" +
							"<p>OrderID : " + order.getConfirmationNumber() + "</p>\n" +
						"</td>\n" +
						"<td style='text-align:left;'>\n" +
							"<p>Total Price : $" + String.valueOf(order.getTotalPrice()) + "</p>\n" +
						"</td>\n" +
						"<td style='text-align:left;'>\n" +
							"<p>Order Date : " + order.getOrderDate() + "</p>\n" +
						"</td>");
								if(action.equals("updateOrder"))
								{
									pWriter.println("<td style='text-align:left;'>\n" +
										"<a href='/csj/Controller/SalesmanHomeServlet?op=updateOrderForm&id=" + order.getConfirmationNumber() + "' method='get'>Update Order</a>\n" +
									"</td>");
								}
								else {
									pWriter.println("<td style='text-align:left;'>\n" +
										"<a href='/csj/Controller/DeleteOrder?var=" + order.getConfirmationNumber() + "' method='get'>Delete Order</a>\n" +
									"</td>");
								}
					pWriter.println("</tr>");
				}
			}
			
			pWriter.println("<tr>\n" +
									"<td colspan='4' style='text-align:right;'>\n" +
										"<a href='/csj/Controller/LoginServlet?op=SalesmanHome' method='get'>Salesman Home</a>\n" +
									"</td>\n" +
								"</tr>\n" +
							"</table>\n" +
							"</div>\n" +
						"</form>\n" +
					"</section>");
		}
		else {
			pWriter.println("<div id='body' class='width'>\n" +
					"<section id='content'>\n" +
					"<div style='height:50px;'></div>\n" +
					"<form class='register' action='' method='get'>\n" +
						"<div class='form_container'>\n" +
							"<table id='OrderListTable'>\n" +
								"<tr>\n" +	
									"<th><div align='center'>Order List</div></th>\n" +
								"</tr>\n" +
								"<tr>\n" +	
									"<td><p>" + dbStatus.get("msg") + "</p></td>\n" +
								"</tr>\n" +
								"<tr>\n" +
								"<td style='text-align:right;'>\n" +
									"<a href='/csj/Controller/LoginServlet?op=SalesmanHome' method='get'>Salesman Home</a>\n" +
								"</td>\n" +
							"</tr>\n" +
						"</table>\n" +
						"</div>\n" +
					"</form>\n" +
				"</section>");
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String confirmationNumber = request.getParameter("var");
			HashMap<String, String> dbStatus = new HashMap<String, String>();
			dbStatus = dbObj.checkDBConnection();
			if(dbStatus.get("status").equals("true")) {
				dbObj.deleteOrders(confirmationNumber);
				
				response.setContentType("text/html");
				PrintWriter pWriter = response.getWriter();
				HtmlEngine engine = new HtmlEngine(pWriter);
				displayLogoutHeader(pWriter);
				
				displayOrderList(pWriter,"deleteOrder");
				
				engine.generateHtml("LeftNavigationBar",request);
				engine.generateHtml("Footer",request);
			}
			else {
				response.setContentType("text/html");
				PrintWriter pWriter = response.getWriter();
				HtmlEngine engine = new HtmlEngine(pWriter);
				displayLogoutHeader(pWriter);
				
				displayOrderList(pWriter,"deleteOrder");
				
				engine.generateHtml("LeftNavigationBar",request);
				engine.generateHtml("Footer",request);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
}