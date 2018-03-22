package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Controller.HtmlEngine;
import DataAccess.MySQLDataStoreUtilities;
import JavaBeans.Orders;
import JavaBeans.ShoppingCart;
import JavaBeans.Users;

public class LoginServlet extends HttpServlet {
	public LoginServlet() {
		super();
	}
	
	public void displayLoginPage(HttpServletRequest request,HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			PrintWriter pWriter = response.getWriter();
			HtmlEngine engine = new HtmlEngine(pWriter);
			engine.generateHtml("Header",request);
			
			pWriter.println("<div id='body' class='width'>\n" +
					"<section id='content'>\n" +
						"<div style='height:50px;'></div>\n" +
							"<form class='register' action='/csj/Controller/LoginServlet' method='POST'>\n" +
								"<input type='hidden' id='form' name='form' value='login'/>\n" +
								"<div class='form_container'>\n" +
								"<br />\n" +
								"<table id='regTable'>\n" +
									"<tr>\n" +	
										"<th colspan='2'><div align='center'>Login Form</div></th>\n" +
									"</tr>\n" +
									"<tr>\n" +
										"<td align='right' style='font-size:small'>\n" +
											"<label class='username' for='username' style='font-size:small'>Username : </label>\n" +
										"</td>\n" +
										"<td align='left' style='font-size:small'>\n" +
											"<input type='text' id='username' style='width:200px' name='username' required/>\n" +
										"</td>\n" +
									"</tr>\n" +	
									"<tr>\n" +
										"<td align='right' style='font-size:small'>\n" +
											"<label class='pwdLabel' for='password' style='font-size:small'>Password : </label>\n" +
										"</td>\n" +
										"<td align='left' style='font-size:small'>\n" +
											"<input type='password' id='password' style='width:200px' name='password' required/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +
										"<td align='right'>\n" +
											"<label for='usertypelbl' style='font-size:small'>User Type : </label>\n" +
										"</td>\n" +
										"<td align='left'>\n" +
											"<select name='userType'>\n" + 
											"  <option value='Customer'>Customer</option>\n" + 
											"  <option value='Salesman'>Salesman</option>\n" + 
											"  <option value='Manager'>Manager</option>\n" + 
											"</select>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +
										"<td colspan='2' align='center'>\n" +
											"<button name='sbmtButton' type='submit' style='width:80px;'>Submit</button>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td colspan='2'><p></p></td>\n" +
									"</tr>\n" +	
									"<tr style='background-color:#bfbfbf;'>\n" +
										"<td colspan='2' style='text-align:center;'>\n" +
											"<p>Dont have an Account?<a href='/csj/Controller/RegisterServlet' method='get'>Create One</a></p>\n" +
										"</td>\n" +
									"</tr>\n" +
								"</table>\n" +
								"</div>\n" +
							"</form>\n" +		
					"</section>");
			
			engine.generateHtml("LeftNavigationBar",request);
			engine.generateHtml("Footer",request);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void displayLogoutHeaderNonUser(PrintWriter pWriter) {
		
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
	
	public void displayLogoutHeader(PrintWriter pWriter,HttpServletRequest request){
		try {
			HttpSession userSession = request.getSession(true);
			String username = (String)userSession.getAttribute("customerKey");
			if(username!=null) {
				ShoppingCart cart = (ShoppingCart) userSession.getAttribute("cart");
				int size = cart.getCartSize();
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
		            			"<ul>\n" +
		            				"<li style='text-align:right;'><a href='/csj/Controller/CartServlet' method='get'>Cart(" + String.valueOf(size) + ")</a></li>\n" +
		            			"</ul>\n" +
		            			"</td>\n" +
		            		"</table>\n" +
		            		"</div>\n" +
						"</nav>");
			}
			else {
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
		            		"<a href='/csj/Controller/RegisterServlet' method='get' class='reg'>New Customer? Start here</a>\n" +
		            		"<a href='/csj/Controller/LoginServlet' method='get' class='login'> Login    </a>\n" +
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
		            			"<ul>\n" +
		            				"<li style='text-align:right;'><a href='/csj/Controller/CartServlet' method='get'>Cart(0)</a></li>\n" +
		            			"</ul>\n" +
		            			"</td>\n" +
		            		"</table>\n" +
		            		"</div>\n" +
						"</nav>");
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void customerHomePage(PrintWriter pWriter,String username) {
		HashMap<String, Orders> ordersHashMap = new HashMap<String, Orders>();
		MySQLDataStoreUtilities dbObj = new MySQLDataStoreUtilities();
		HashMap<String, String> dbStatus = new HashMap<String, String>();
		dbStatus = dbObj.checkDBConnection();
		if(dbStatus.get("status").equals("true")) {
			ordersHashMap = dbObj.getOrders();	
			String confirmationNumber;
			HashMap<String, Orders> mapTmp = new HashMap<String, Orders>();
			Orders order = new Orders();
			
			pWriter.println("<div id='body' class='width'>\n" +
					"<section id='content'>\n" +
					"<div style='height:50px;'></div>\n" +
					"<form style='width: 90%;margin: 0 auto;height: 50%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
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
					if(username.equals(order.getUsername())) {
						confirmationNumber = String.valueOf(order.getConfirmationNumber());
						mapTmp.put(confirmationNumber, order);
					}
				}
				
				if(mapTmp == null) {
					pWriter.println("<tr>\n" +	
										"<td colspan='4' style='text-align:center;'>\n" +
											"<label for='orderStatusLbl'>No Orders to Display</label>\n" +
										"</td>\n" +
									"</tr>");
				}
				else {
					for(Map.Entry<String, Orders> o : mapTmp.entrySet()) {
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
								"</td>\n" +
								"<td style='text-align:left;'>\n" +
									"<a href='/csj/Controller/CustomerHomeServlet?id=" + order.getConfirmationNumber() + "' method='get'>Delete Order</a>\n" +
								"</td>\n" +
								"</tr>");
					}
				}
			}
			pWriter.println("<tr>\n" +
									"<td colspan='4' style='text-align:right;'>\n" +
										"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
									"<td  style='text-align:center;'>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +	
									"<td style='text-align:center;'>\n" +
										"<label for='dbInvalidLbl'>" + dbStatus.get("msg") + "</label>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
									"</td>\n" +
								"</tr>\n" +
							"</table>\n" +
							"</div>\n" +
						"</form>\n" +
					"</section>");
		}
	}
	
	public void salesmanHomePage(PrintWriter pWriter) {
		pWriter.println("<div id='body' class='width'>\n" +
				"<section id='content'>\n" +
				"<div style='height:50px;'></div>\n" +
				"<form class='register' action='' method='get'>\n" +
					"<div class='form_container'>\n" +
						"<table id='SalesmanHomeTable'>\n" +
							"<tr>\n" +
								"<td colspan='2' style='text-align:center;'>\n" +
									"<p><h2>Salesman Home Page</h2></p>\n" +
								"</td>\n" +
							"</tr>\n" +
							"<tr>\n" +	
								"<td colspan='2' style='text-align:center;'>\n" +
								"</td>\n" +
							"</tr>\n" +
							"<tr>\n" +
							"<td colspan='2' style='text-align:center;'>\n" +
								"<p><a href='/csj/Controller/SalesmanHomeServlet?op=createAccount' method='get'>Create Customer Account</a></p>\n" +
								"<br />\n" +
								"<p><a href='/csj/Controller/SalesmanHomeServlet?op=addOrder' method='get'>Add Customer Order</a></p>\n" +
								"<br />\n" +
								"<p><a href='/csj/Controller/SalesmanHomeServlet?op=updateOrder' method='get'>Update Customer Order</a></p>\n" +
								"<br />\n" +
								"<p><a href='/csj/Controller/SalesmanHomeServlet?op=deleteOrder' method='get'>Delete Customer Order</a></p>\n" +
							"</td>\n" +
						"</tr>\n" +
						"</table>\n" +
					"</div>\n" +
				"</form>\n" + 
				"</section>");
	}
	
	public void managerHomePage(PrintWriter pWriter) {
		pWriter.println("<div id='body' class='width'>\n" +
				"<section id='content'>\n" +
				"<div style='height:50px;'></div>\n" +
				"<form class='register' action='' method='get'>\n" +
					"<div class='form_container'>\n" +
						"<table id='ManagerHomeTable'>\n" +
							"<tr>\n" +
								"<td colspan='2' style='text-align:center;'>\n" +
									"<p><h2>Manager Home Page</h2></p>\n" +
								"</td>\n" +
							"</tr>\n" +
							"<tr>\n" +	
								"<td colspan='2' style='text-align:center;'>\n" +
								"</td>\n" +
							"</tr>\n" +
							"<tr>\n" +
							"<td colspan='2' style='text-align:center;'>\n" +
								"<p><a href='/csj/Controller/ManagerHomeServlet?op=addProduct' method='get'>Add Product</a></p>\n" +
								"<br />\n" +
								"<p><a href='/csj/Controller/ManagerDisplayProducts?task=updateProduct' method='get'>Update Product</a></p>\n" +
								"<br />\n" +
								"<p><a href='/csj/Controller/ManagerDisplayProducts?task=deleteProduct' method='get'>Delete Product</a></p>\n" +
							"</td>\n" +
						"</tr>\n" +
						"</table>\n" +
					"</div>\n" +
				"</form>\n" + 
				"</section>");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String op = request.getParameter("op");
			HttpSession userSession = request.getSession(true);
			
			if(op==null) {
				displayLoginPage(request,response);
			}
			else
			{
				switch(op) {
					case "CustomerHome":{
						String username = (String)userSession.getAttribute("customerKey");
						if(username != null) {
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter,request);
							customerHomePage(pWriter,username);
							engine.generateHtml("LeftNavigationBar",request);
							engine.generateHtml("Footer",request);
						}
						else {
							response.sendRedirect("/csj/Controller/LoginServlet");
						}
						
						break;
					}
					case "SalesmanHome":{
						String username = (String) userSession.getAttribute("salesmanKey");
						
						if(username!=null) {
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeaderNonUser(pWriter);
							salesmanHomePage(pWriter);
							engine.generateHtml("LeftNavigationBar",request);
							engine.generateHtml("Footer",request);
						}
						else {
							response.sendRedirect("/csj/Controller/LoginServlet");
						}
						break;
					}
					case "ManagerHome":{
						
						String username = (String) userSession.getAttribute("managerKey");
						if(username != null) {
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
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
					            				"<li><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>\n" +
					            				"<li><a href='/csj/Controller/DataAnalyticsServlet?op=home' method='get'>Data Analytics</a></li>\n" +
					            				"<li><a href='/csj/Controller/DataExplorationServlet' method='get'>Data Exploration</a></li>\n" +
					            				"<li><a href='/csj/Controller/DataAnalyticsServlet?op=inventory' method='get'>Inventory</a></li>\n" +
					            				"<li class='end'><a href='/csj/Controller/DataAnalyticsServlet?op=salesreports' method='get'>Sales Reports</a></li>\n" +
					            			"</ul>\n" +
					            			"</td>\n" +
					            			"<td style='width:75px;'>\n" +
					            			"</td>\n" +
					            		"</table>\n" +
					            		"</div>\n" +
									"</nav>");
							managerHomePage(pWriter);
							engine.generateHtml("LeftNavigationBar",request);
							engine.generateHtml("Footer",request);
						}
						else {
							response.sendRedirect("/csj/Controller/LoginServlet");
						}
						break;
					}
					default:{
						displayLoginPage(request,response);
						break;
					}
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void displayInvalidCredentials(HttpServletRequest request,HttpServletResponse response,String message) {
		try {
			response.setContentType("text/html");
			PrintWriter pWriter = response.getWriter();
			HtmlEngine engine = new HtmlEngine(pWriter);
			engine.generateHtml("Header",request);
			
			pWriter.println("<div id='body' class='width'>\n" +
					"<section id='content'>\n" +
						"<div style='height:50px;'></div>\n" +
							"<form class='register' action='/csj/Controller/LoginServlet' method='POST'>\n" +
								"<input type='hidden' id='form' name='form' value='login'/>\n" +
								"<div class='form_container'>\n" +
								"<br />\n" +
								"<table id='regTable'>\n" +
									"<tr>\n" +	
										"<th colspan='2'><div align='center'>Login From</div></th>\n" +
									"</tr>\n" +
									"<tr>\n" +
										"<td colspan='2' align='center' style='font-size:small'>\n" +
										"<label for='msg' style='font-size:small;color: red;'>" + message + "</label>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +
										"<td align='right' style='font-size:small'>\n" +
											"<label class='username' for='username' style='font-size:small'>Username : </label>\n" +
										"</td>\n" +
										"<td align='left' style='font-size:small'>\n" +
											"<input type='text' id='username' style='width:200px' name='username' required/>\n" +
										"</td>\n" +
									"</tr>\n" +	
									"<tr>\n" +
										"<td align='right' style='font-size:small'>\n" +
											"<label class='pwdLabel' for='password' style='font-size:small'>Password : </label>\n" +
										"</td>\n" +
										"<td align='left' style='font-size:small'>\n" +
											"<input type='password' id='pwd' style='width:200px' name='password' required/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +
										"<td align='right'>\n" +
											"<label for='usertypelbl' style='font-size:small'>User Type : </label>\n" +
										"</td>\n" +
										"<td align='left'>\n" +
											"<select name='userType'>\n" + 
											"  <option value='Customer'>Customer</option>\n" + 
											"  <option value='Salesman'>Salesman</option>\n" + 
											"  <option value='Manager'>Manager</option>\n" + 
											"</select>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +
										"<td colspan='2' align='center'>\n" +
											"<button name='sbmtButton' type='submit' style='width:80px;font-size:small;line-height:11px;'>Submit</button>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
									"<td colspan='2'><p></p></td>\n" +
								"</tr>\n" +	
								"<tr style='background-color:#bfbfbf;'>\n" +
									"<td colspan='2' style='text-align:center;'>\n" +
										"<p>Dont have an Account?<a href='/csj/Controller/RegisterServlet' method='get'>Create One</a></p>\n" +
									"</td>\n" +
								"</tr>\n" +
								"</table>\n" +
								"</div>\n" +
							"</form>\n" +		
					"</section>");
			
			engine.generateHtml("LeftNavigationBar",request);
			engine.generateHtml("Footer",request);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			MySQLDataStoreUtilities dbObj = new MySQLDataStoreUtilities();
			String username = request.getParameter("username");
			String pwd = request.getParameter("password");
			String userType = request.getParameter("userType");
			
			if(username != null && pwd !=null)
			{
				String welcomeMsg;
				Users user = new Users();
				HashMap<String, String> dbStatus = new HashMap<String, String>();
				dbStatus = dbObj.checkDBConnection();
				if(dbStatus.get("status").equals("true")) {
					System.out.println("Successfully connected to database");
					user = dbObj.getUserProfile(username);
					if(user!=null) {
						String actualUsername = user.getUsername();
						String actualPassword = user.getPWD();
						String actualUserType = user.getUserType();
						System.out.println("actual usertype - " + actualUserType + "userTyp - " + userType);
						System.out.println("Actual Username - " + actualUsername + "\nActual Password - " + actualPassword + "\nGiven Username - " + username + "\nGiven Password - " + pwd + "\n");
						if(actualUsername.equals(username) && actualPassword.equals(pwd) && actualUserType.equals(userType))
						{
							switch(userType) {
								case "Customer":{
									HttpSession userSession = request.getSession(true);
									userSession.setAttribute("customerKey", username);
									ShoppingCart cart = (ShoppingCart) userSession.getAttribute("cart");
									if(cart == null) {
										cart = new ShoppingCart();
										cart.setCartSize(0);
										cart.setUsername(username);
										userSession.setAttribute("cart", cart);
									}
									//Display Customer Home Page
									
									response.setContentType("text/html");
									PrintWriter pWriter = response.getWriter();
									HtmlEngine engine = new HtmlEngine(pWriter);
									displayLogoutHeader(pWriter,request);
									customerHomePage(pWriter,username);
									engine.generateHtml("LeftNavigationBar",request);
									engine.generateHtml("Footer",request);
									break;
								}
								case "Salesman":{
									HttpSession userSession = request.getSession(true);
									userSession.setAttribute("salesmanKey", username);
									//Display Salesman Home Page
									response.setContentType("text/html");
									PrintWriter pWriter = response.getWriter();
									HtmlEngine engine = new HtmlEngine(pWriter);
									displayLogoutHeaderNonUser(pWriter);
									salesmanHomePage(pWriter);
									engine.generateHtml("LeftNavigationBar",request);
									engine.generateHtml("Footer",request);
									
									break;
								}
								case "Manager":{
									HttpSession userSession = request.getSession(true);
									userSession.setAttribute("managerKey", username);
									//Display Manager Home Page
									response.setContentType("text/html");
									PrintWriter pWriter = response.getWriter();
									HtmlEngine engine = new HtmlEngine(pWriter);
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
									            				"<li><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>\n" +
									            				"<li><a href='/csj/Controller/DataAnalyticsServlet?op=home' method='get'>Data Analytics</a></li>\n" +
									            				"<li><a href='/csj/Controller/DataExplorationServlet' method='get'>Data Exploration</a></li>\n" +
									            				"<li><a href='/csj/Controller/DataAnalyticsServlet?op=inventory' method='get'>Inventory</a></li>\n" +
									            				"<li class='end'><a href='/csj/Controller/DataAnalyticsServlet?op=salesreports' method='get'>Sales Reports</a></li>\n" +
									            			"</ul>\n" +
									            			"</td>\n" +
									            			"<td style='width:75px;'>\n" +
									            			"</td>\n" +
									            		"</table>\n" +
									            		"</div>\n" +
													"</nav>");
									managerHomePage(pWriter);
									engine.generateHtml("LeftNavigationBar",request);
									engine.generateHtml("Footer",request);
									break;
								}
								default:{
									
								}
							}
							
						}
						else
						{
							//Display Invalid username or password
							displayInvalidCredentials(request,response,"Invalid Username or Password");
						}
					}
					else {
						//Display Invalid username or password
						displayInvalidCredentials(request,response,"Invalid Username or Password");
					}
				}
				else {
					System.out.println(dbStatus.get("msg"));
					displayInvalidCredentials(request,response,dbStatus.get("msg"));
				}
			}
			else
			{
				//Display Invalid username or password
				displayInvalidCredentials(request,response,"Invalid Username or Password");
			}
		}
		catch(EOFException ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
	}
}