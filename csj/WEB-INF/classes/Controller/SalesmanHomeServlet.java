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

public class SalesmanHomeServlet extends HttpServlet {
	public static MySQLDataStoreUtilities dbObj = new MySQLDataStoreUtilities();
	public static HashMap<String, Orders> ordersHashMap;
	Orders order;
	public SalesmanHomeServlet() {
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
			
			order = new Orders();
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
									"<td style='text-align:center;'>\n" +
										"<p>" + dbStatus.get("msg") + "</p>\n" +	
									"</td>\n" +
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
		try {
			String op = request.getParameter("op");
			switch(op) {
				case "createAccount":{
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					displayLogoutHeader(pWriter);
					
					pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
							"<div style='height:50px;'></div>\n" +
							"<form class='register' action='/csj/Controller/SalesmanHomeServlet?op=createAccount' method='POST'>\n" +
								"<div class='form_container'>\n" +
									"<table id='CustRegTable'>\n" +
										"<tr>\n" +	
											"<th colspan='2'><div align='center'>Registration Form</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td colspan='2' style='text-align:center;'>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td style='text-align:right;'>\n" +
												"<label for='usertypelbl'>User Type : </label>\n" +
											"</td>\n" +
											"<td style='text-align:left;'>\n" +
												"<input type='text' id='userType' style='width:200px;background-color:#bfbfbf;' name='userType' value='Customer' readonly/>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td style='text-align:right;'>\n" +
												"<label class='username' for='username'>Username : </label>\n" +
											"</td>\n" +
											"<td style='text-align:left;'>\n" +
												"<input type='text' id='username' style='width:200px' name='username' required/>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td style='text-align:right;'>\n" +
												"<label class='firstName' for='firstName'>First Name : </label>\n" +
											"</td>\n" +
											"<td style='text-align:left;'>\n" +
												"<input type='text' id='firstName' style='width:200px' name='firstName' required/>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td style='text-align:right;'>\n" +
												"<label class='lastName' for='lastName'>Last Name : </label>\n" +
											"</td>\n" +
											"<td style='text-align:left;'>\n" +
												"<input type='text' id='lastName' style='width:200px' name='lastName' required/>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td style='text-align:right;'>\n" +
												"<label class='pwdLabel' for='password'>Password : </label>\n" +
											"</td>\n" +
											"<td style='text-align:left;'>\n" +
												"<input type='password' id='pwd' style='width:200px' name='password' required/>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td style='text-align:right;'>\n" +
												"<label class='emailIDLabel' for='emailID'>E-mail : </label>\n" +
											"</td>\n" +
											"<td style='text-align:left;'>\n" +
												"<input type ='text' id='emailID' style='width:200px' name='email' required/>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td style='text-align:right;'>\n" +
												"<label class='dateOfBirthLabel' for='dateOfBirth'>Date of Birth : </label>\n" +
											"</td>\n" +
											"<td style='text-align:right;'>\n" +
												"<input type='date' id='DOB' style='width:200px' name='bday' required/>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td style='text-align:right;'>\n" +
												"<label class='addressLabel' for='address'>Address : </label>\n" +
											"</td>\n" +
											"<td style='text-align:right;'>\n" +
												"<textarea name='address' id='address' rows='4' cols='35'></textarea>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td style='text-align:right;'>\n" +
												"<label class='zipCodeLabel' for='zipCode'>Zip Code : </label>\n" +
											"</td>\n" +
											"<td style='text-align:left;'>\n" +
												"<input type='text' id='zipCode' style='width:200px' name='zipcode' required/>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td colspan='2' align='center'>\n" +
												"<button name='sbmtButton' type='submit' style='width:80px;'>Submit</button>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td colspan='2' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/LoginServlet?op=SalesmanHome' method='get'>Salesman Home</a>\n" +
											"</td>\n" +
										"</tr>\n" +
									"</table>\n" +
									"</div>\n" +
								"</form>\n" +
							"</section>");
					
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				case "addOrder":{
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					displayLogoutHeader(pWriter);
					
					pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
							"<div style='height:50px;'></div>\n" +
							"<form class='register' action='/csj/Controller/SalesmanHomeServlet?op=addOrder' method='POST'>\n" +
							"<div class='form_container'>\n" +
									"<table id='AddOrderTable'>\n" +
											"<tr>\n" +	
												"<th colspan='2'><div align='center'>Add Order Form</div></th>\n" +
											"</tr>\n" +
											"<tr>\n" +	
												"<td colspan='2'><p></p></td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='ProductType'>Product Type : </label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<select name='productType' style='width:200px;'>\n" + 
													"  <option value='smartwatches'>smartwatches</option>\n" + 
													"  <option value='speakers'>speakers</option>\n" + 
													"  <option value='headphones'>headphones</option>\n" +
													"  <option value='smartphones'>smartphones</option>\n" +
													"  <option value='laptops'>laptops</option>\n" + 
													"  <option value='externalstorage'>externalstorage</option>\n" + 
													"  <option value='accessories'>accessories</option>\n" + 
													"</select>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='ProductID'>Product ID : </label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='productID' style='width:200px;' name='productID' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='Quantity'>Quantity : </label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='quantity' style='width:200px;' name='quantity' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='ProductPrice'>Product Price : </label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='productprice' style='width:200px;' name='productprice' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='customerName'>Customer Name : </label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='customerName' style='width:200px;' name='customerName' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='warranty'>Warranty : </label>\n" +
												"</td>\n" +
												"<td style='text-align:left;vertical-align: text-top;'>\n" +
													"  <input type='radio' name='warranty' value='NoWarranty' checked>NoWarranty<br>\n" + 
													"  <input type='radio' name='warranty' value='OneYearWarranty'>OneYearWarranty - $24.99<br>\n" + 
													"  <input type='radio' name='warranty' value='TwoYearWarranty'>TwoYearWarranty - $49.99<br>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='Country'>Country : </label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='country' style='width:200px;' name='country' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='State'>State : </label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='state' style='width:200px' name='state' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
											"<td style='text-align:right;'>\n" +
													"<label for='Address1'>Address 1 : </label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='address1' style='width:200px' name='address1' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
											"<td style='text-align:right;'>\n" +
													"<label for='Address2' style='font-size:small'>Address 2 : </label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='address2' style='width:200px' name='address2'/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
											"<td style='text-align:right;'>\n" +
													"<label for='City'>City : </label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='city' style='width:200px' name='city' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
											"<td style='text-align:right;'>\n" +
													"<label for='PostalCode'>Postal Code : </label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='postalcode' style='width:200px' name='postalcode' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
											"<td style='text-align:right;'>\n" +
													"<label for='MobileNumber'>Mobile Number : </label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='mobilenumber' style='width:200px' name='mobilenumber' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
											"<td style='text-align:right;'>\n" +
													"<label for='EmailAddress'>Email Address : </label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='emailaddress' style='width:200px' name='emailaddress' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:center;'>\n" +
													"<button name='sbmtButton' type='submit' style='width:80px;'>Submit</button>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=SalesmanHome' method='get'>Salesman Home</a>\n" +
												"</td>\n" +
											"</tr>\n" +
										"</table>\n" +
									"</div>\n" +
									"</form>\n" +
								"</section>");
					
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				case "updateOrder":{
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					displayLogoutHeader(pWriter);
					displayOrderList(pWriter,"updateOrder");
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				case "updateOrderForm":{
					String confirmationNumber = request.getParameter("id");
					Orders order = new Orders();
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					displayLogoutHeader(pWriter);
					
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
						order = dbObj.getOrder(confirmationNumber);
						pWriter.println("<div id='body' class='width'>\n" +
								"<section id='content'>\n" +
								"<div style='height:50px;'></div>\n" +
								"<form class='register' action='/csj/Controller/SalesmanHomeServlet?op=updateOrder&id=" + confirmationNumber + "' method='POST'>\n" +
										"<table id='UpdateOrderTable'>\n" +
												"<tr>\n" +	
													"<th colspan='2'><div align='center'>Update Order Form</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='2'><p></p></td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td style='text-align:right;'>\n" +
														"<label for='ProductPrice'>Product Price : </label>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +
														"<input type='text' id='productprice' style='width:200px;' name='productprice' value='" + String.valueOf(order.getTotalPrice()) + "' required/>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td style='text-align:right;'>\n" +
														"<label for='customerName'>Customer Name : </label>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +
														"<input type='text' id='customerName' style='width:200px;' name='customerName' value='" + order.getCustomerName() + "' required/>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td style='text-align:right;'>\n" +
														"<label for='warranty'>Warranty : </label>\n" +
													"</td>\n" +
													"<td style='text-align:left;vertical-align: text-top;'>");
														if(order.getProductWarranty()=="NoWarranty") {
											pWriter.println("<input type='radio' name='warranty' value='NoWarranty' checked>NoWarranty<br>\n" + 
															"<input type='radio' name='warranty' value='OneYearWarranty'>OneYearWarranty - $24.99<br>\n" + 
															"<input type='radio' name='warranty' value='TwoYearWarranty'>TwoYearWarranty - $49.99<br>");
														}else if(order.getProductWarranty()=="OneYearWarranty") {
											pWriter.println("<input type='radio' name='warranty' value='NoWarranty'>NoWarranty<br>\n" + 
															"<input type='radio' name='warranty' value='OneYearWarranty' checked>OneYearWarranty - $24.99<br>\n" + 
															"<input type='radio' name='warranty' value='TwoYearWarranty'>TwoYearWarranty - $49.99<br>");
														}else if(order.getProductWarranty()=="TwoYearWarranty"){
											pWriter.println("<input type='radio' name='warranty' value='NoWarranty'>NoWarranty<br>\n" + 
															"<input type='radio' name='warranty' value='OneYearWarranty'>OneYearWarranty - $24.99<br>\n" + 
															"<input type='radio' name='warranty' value='TwoYearWarranty' checked>TwoYearWarranty - $49.99<br>");
														}else {
											pWriter.println("<input type='radio' name='warranty' value='NoWarranty' checked>NoWarranty<br>\n" + 
															"<input type='radio' name='warranty' value='OneYearWarranty'>OneYearWarranty - $24.99<br>\n" + 
															"<input type='radio' name='warranty' value='TwoYearWarranty'>TwoYearWarranty - $49.99<br>");
														}
									pWriter.println("</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td style='text-align:right;'>\n" +
														"<label for='Country'>Country : </label>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +
														"<input type='text' id='country' style='width:200px;' name='country' value='" + order.getCountry() + "' required/>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td style='text-align:right;'>\n" +
														"<label for='State'>State : </label>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +
														"<input type='text' id='state' style='width:200px' name='state' value='" + order.getState() + "' required/>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td style='text-align:right;'>\n" +
														"<label for='Address1'>Address 1 : </label>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +
														"<input type='text' id='address1' style='width:200px' name='address1' value='" + order.getAddress1() + "' required/>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td style='text-align:right;'>\n" +
														"<label for='Address2'>Address 2 : </label>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +
														"<input type='text' id='address2' style='width:200px' name='address2' value='" + order.getAddress2() + "'/>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td style='text-align:right;'>\n" +
														"<label for='City'>City : </label>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +
														"<input type='text' id='city' style='width:200px' name='city' value='" + order.getCity() + "' required/>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td style='text-align:right;'>\n" +
														"<label for='PostalCode'>Postal Code : </label>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +
														"<input type='text' id='postalcode' style='width:200px' name='postalcode' value='" + order.getZipCode() + "' required/>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td style='text-align:right;'>\n" +
														"<label for='MobileNumber'>Mobile Number : </label>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +
														"<input type='text' id='mobilenumber' style='width:200px' name='mobilenumber' value='" + order.getMobileNumber() + "' required/>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td style='text-align:right;'>\n" +
														"<label for='EmailAddress'>Email Address : </label>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +
														"<input type='text' id='emailaddress' style='width:200px' name='emailaddress' value='" + order.getEmailAddress() + "' required/>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:center;'>\n" +
														"<button name='sbmtButton' type='submit' style='width:80px;'>Submit</button>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:right;'>\n" +
														"<a href='/csj/Controller/LoginServlet?op=SalesmanHome' method='get'>Salesman Home</a>\n" +
													"</td>\n" +
												"</tr>\n" +
											"</table>\n" +
										"</form>\n" +
									"</section>");
					}
					else {
						pWriter.println("<div id='body' class='width'>\n" +
								"<section id='content'>\n" +
								"<div style='height:50px;'></div>\n" +
								"<form class='register' action='' method=''>\n" +
										"<table id='UpdateOrderTable'>\n" +
												"<tr>\n" +	
													"<th><div align='center'>Update Order Form</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td><p></p></td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td style='text-align:center;'>\n" +
														"<p>" + dbStatus.get("msg") + "</p>\n" +	
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=SalesmanHome' method='get'>Salesman Home</a>\n" +
												"</td>\n" +
											"</tr>\n" +
										"</table>\n" +
									"</form>\n" +
								"</section>");
					}
					
					
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				case "deleteOrder":{
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					displayLogoutHeader(pWriter);
					
					
					displayOrderList(pWriter,"deleteOrder");
					
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				default:{
					break;
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void displayOrderDetails(PrintWriter pWriter,Orders order) {
		String[] prodID = order.getProductID();
		String[] prodType = order.getProductType();
		int[] qnty = order.getQuantity();
		//content
		pWriter.println("<div id='body' class='width'>\n" +
				"<section id='content'>\n" +
				"<div style='height:50px;'></div>\n" +
				"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;'>\n" +
					"<div class='form_container'>\n" +
						"<br />\n" +
						"<table id='OrderDetails'>\n" +
							"<tr>\n" +	
								"<th colspan='3'><div align='center'>Order Details</div></th>\n" +
							"</tr>\n" +
							"<tr>\n" +	
								"<td colspan='3'><p></p></td>\n" +
							"</tr>\n" +
							"<tr>\n" +
								"<td style='text-align:left;vertical-align: text-top;border: 1px solid #ccc;border-radius: 5px;'>\n" +
									"<p><label for='ShippingAddress' style='color: #fff;background: #799AC0 none repeat-x scroll left top;border-radius: 5px;'>Shipping Address</label>\n" +
									"<br />\n" +
									"<label for='Address1' style='font-size:small'>" + order.getAddress1() + "</label>\n" +
									"<br />\n" +
									"<label for='Address2' style='font-size:small'>" + order.getAddress2() + "</label>\n" +
									"<br />\n" +
									"<label for='City' style='font-size:small'>" + order.getCity() + "</label>\n" +
									"<br />\n" +
									"<label for='State' style='font-size:small'>" + order.getState() + "</label>\n" +
									"<br />\n" +
									"<label for='PostalCode' style='font-size:small'>" + String.valueOf(order.getZipCode()) + "</label>\n" +
									"<br />\n" +
									"<label for='Country' style='font-size:small'>" + order.getCountry() + "</label></p>\n" +
									"<br />\n" +
									"<p><label for='MobileNumberlbl' style='font-size:small'>MobileNumber :</label>\n" +
									"<label for='MobileNumber' style='font-size:small'> " + String.valueOf(order.getMobileNumber()) + "</label></p>\n" +
								"</td>\n" +
								"<td style='text-align:left;vertical-align: text-top;border: 1px solid #ccc;border-radius: 5px;'>\n" +
									"<label for='confirmationNumberlbl' style='font-size:small'>Confirmation Number : " + order.getConfirmationNumber() + "</label>\n" +
									"<br />\n" +
									"<label for='orderDatelbl' style='font-size:small'>Order Date : " + order.getOrderDate() + "</label>\n" +
									"<br />\n" +
									"<label for='deliveryDatelbl' style='font-size:small'>Delivery Date : " + order.getDeliveryDate() + "</label>\n" +
									"<br />\n" +
									"<label for='orderStatuslbl' style='font-size:small'>Order Status : In Progress</label>\n" +
									"<br />\n" +
									"<label for='emailAddresslbl' style='font-size:small'>Email Address : " + order.getEmailAddress() + "</label>\n" +
								"</td>\n" +
								"<td style='text-align:left;vertical-align: text-top;border: 1px solid #ccc;border-radius: 5px;'>\n" +
									"<p><label for='OrderTotals' style='color: #fff;background: #799AC0 none repeat-x scroll left top;border-radius: 5px;'>Order Totals</label></p>\n" +
									"<br />\n" +
									"<label for='Shipping'>Shipping : $" + String.valueOf(order.getShippingPrice()) + "</label>\n" +
									"<br />\n" +
									"<label for='Tax'>Tax : $ 0.00</label>\n" +
									"<br />\n" +
									"<label for='Total'>Total : $" + order.getTotalPrice() + "</label>\n" +
								"</td>\n" +
							"</tr>\n" +
							"<tr>\n" +
								"<td style='text-align:left;vertical-align: text-top;border: 1px solid #ccc;border-radius: 5px;'>");
									for(int i=0;i<prodType.length;i++) {
										if(prodType[i]!=null) {
											pWriter.println("<p>Product Type : " + prodType[i] + "</p>\n" +
													"<br />");
										}
									}
									pWriter.println("</td>\n" +
								"<td style='text-align:left;vertical-align: text-top;border: 1px solid #ccc;border-radius: 5px;'>");
									for(int i=0;i<prodID.length;i++) {
										if(prodID[i]!=null) {
											pWriter.println("<p>Product ID : " + prodID[i] + "</p>\n" +
													"<br />");
										}
									}
									pWriter.println("</td>\n" +
								"<td style='text-align:left;vertical-align: text-top;border: 1px solid #ccc;border-radius: 5px;'>");
									for(int i=0;i<qnty.length;i++) {
										if(qnty[i]!=0) {
											pWriter.println("<p>Quantity : " + String.valueOf(qnty[i]) + "</p>\n" +
													"<br />");
										}
									}
									pWriter.println("</td>\n" +
							"</tr>\n" +
							"<tr>\n" +
								"<td colspan='3' style='text-align:right;'>\n" +
									"<a href='/csj/Controller/LoginServlet?op=SalesmanHome' method='get'>Salesman Home</a>\n" +
								"</td>\n" +
							"</tr>\n" +
						"</table>\n" +
					"</div>\n" +
				"</form>\n" +
				"</section>");
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		try {
			String op = request.getParameter("op");
			switch(op) {
				case "createAccount": {
					try {
						String username = request.getParameter("username");
						String userType = request.getParameter("userType");
						HashMap<String, String> dbStatus = new HashMap<String, String>();
						dbStatus = dbObj.checkDBConnection();
						if(dbStatus.get("status").equals("true")) {
							Users user = dbObj.getUserProfile(username);
							if(user == null)
							{
								user = new Users(userType,username,request.getParameter("firstName"),request.getParameter("lastName"),request.getParameter("address"),Integer.parseInt(request.getParameter("zipcode")),request.getParameter("email"),request.getParameter("bday"),request.getParameter("password"));
								dbObj.writeUserProfile(user);
								response.setContentType("text/html");
								PrintWriter pWriter = response.getWriter();
								HtmlEngine engine = new HtmlEngine(pWriter);
								displayLogoutHeader(pWriter);
								
								pWriter.println("<div id='body' class='width'>\n" +
										"<section id='content'>\n" +
										"<div style='height:50px;'></div>\n" +
										"<form class='register' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='CustRegTable'>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:center;'>\n" +
														"<p>User - " + username +" successfully registered.</p>\n" +
														"<p>Please use the link below to go back to Home Page.</p>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='2' style='text-align:right;'>\n" +
														"<a href='/csj/Controller/LoginServlet?op=SalesmanHome' method='get'>Salesman Home</a>\n" +
													"</td>\n" +
												"</tr>\n" +
											"</table>\n" +
										"</div>\n" +
										"</form>\n" +
										"</section>");
								
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
							}
							else if((user.getUsername()).equals(username))
							{
								response.setContentType("text/html");
								PrintWriter pWriter = response.getWriter();
								HtmlEngine engine = new HtmlEngine(pWriter);
								displayLogoutHeader(pWriter);
								
								pWriter.println("<div id='body' class='width'>\n" +
										"<section id='content'>\n" +
										"<div style='height:50px;'></div>\n" +
										"<form class='register' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='CustRegTable'>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:center;'>\n" +
														"<p>User - " + username +" already registered.</p>\n" +
														"<p>Please use the link below to go back to Home Page.</p>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='2' style='text-align:right;'>\n" +
														"<a href='/csj/Controller/LoginServlet?op=SalesmanHome' method='get'>Salesman Home</a>\n" +
													"</td>\n" +
												"</tr>\n" +
											"</table>\n" +
										"</div>\n" +
										"</form>\n" +
										"</section>");
								
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
							}
						}
						else {
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter);
							
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form class='register' action='' method='POST'>\n" +
									"<div class='form_container'>\n" +
										"<table id='CustRegTable'>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:center;'>\n" +
													"<p>" + dbStatus.get("msg") + "</p>\n" +	
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +	
												"<td colspan='2' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=SalesmanHome' method='get'>Salesman Home</a>\n" +
												"</td>\n" +
											"</tr>\n" +
										"</table>\n" +
									"</div>\n" +
									"</form>\n" +
									"</section>");
							
							engine.generateHtml("LeftNavigationBar",request);
							engine.generateHtml("Footer",request);
						}
					}
					catch(EOFException ex) {
						System.out.println(ex.getMessage());
						ex.printStackTrace();
						
					}
					catch(Exception ex) {
						System.out.println(ex.getMessage());
						ex.printStackTrace();
						
					}
					break;
				}
				case "addOrder": {
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					displayLogoutHeader(pWriter);
					
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
						Orders order = new Orders();
						int quantity = Integer.parseInt(request.getParameter("quantity"));
						int[] qnty = new int[] {quantity};
						String id = request.getParameter("productID");
						String[] prodID = new String[] {id};
						String type = request.getParameter("productType");
						String[] prodType = new String[] {type};
						double totPrice = Double.parseDouble(request.getParameter("productprice"));
						int zipCode = Integer.parseInt(request.getParameter("postalcode"));
						DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Random rand = new Random();
						Date date = new Date();
						SimpleDateFormat sdfOrderDate = new SimpleDateFormat("yyyy/MM/dd");
						int  confirmationNumber = rand.nextInt(999999999) + 99999999;
						
						int noOfDays = 14; //i.e two weeks
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);            
						calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
						Date deliveryDate = calendar.getTime();
						
						double shippingPrice = 5.60;
						HttpSession userSession = request.getSession(true);
						String username = (String)userSession.getAttribute("salesmanKey");
						order = new Orders(username,request.getParameter("customerName"),prodType,prodID,qnty,sdfOrderDate.format(date),confirmationNumber,sdf.format(deliveryDate),shippingPrice,totPrice,request.getParameter("warranty"),request.getParameter("country"),request.getParameter("state"),request.getParameter("address1"),request.getParameter("address2"),request.getParameter("city"),zipCode,request.getParameter("mobilenumber"),request.getParameter("emailaddress"));
						dbObj.writeOrders(order);
						displayOrderDetails(pWriter,order);
					}
					else {
						pWriter.println("<div id='body' class='width'>\n" +
								"<section id='content'>\n" +
								"<div style='height:50px;'></div>\n" +
								"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;'>\n" +
									"<div class='form_container'>\n" +
										"<br />\n" +
										"<table id='OrderDetails'>\n" +
											"<tr>\n" +	
												"<th colspan='3'><div align='center'>Order Details</div></th>\n" +
											"</tr>\n" +
											"<tr>\n" +	
												"<td colspan='3'>\n" +
												"<p>" + dbStatus.get("msg") + "</p>\n" +	
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
											"<td colspan='3' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/LoginServlet?op=SalesmanHome' method='get'>Salesman Home</a>\n" +
											"</td>\n" +
										"</tr>\n" +
									"</table>\n" +
								"</div>\n" +
							"</form>\n" +
							"</section>");
					}
					
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				
				case "updateOrder": {
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					displayLogoutHeader(pWriter);
					
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
						String confirmationNumber = request.getParameter("id");
						Orders order = dbObj.getOrder(confirmationNumber);
						
						order.setCustomerName(request.getParameter("customerName"));
						double totPrice = Double.parseDouble(request.getParameter("productprice"));
						order.setTotalPrice(totPrice);
						order.setProductWarranty(request.getParameter("warranty"));
						order.setCountry(request.getParameter("country"));
						order.setState(request.getParameter("state"));
						order.setAddress1(request.getParameter("address1"));
						order.setAddress2(request.getParameter("address2"));
						order.setCity(request.getParameter("city"));
						int zipCode = Integer.parseInt(request.getParameter("postalcode"));
						order.setZipCode(zipCode);
						order.setMobileNumber(request.getParameter("mobilenumber"));
						order.setEmailAddress(request.getParameter("emailaddress"));
						
						dbObj.writeOrders(order);
						
						//Display Order list again
						displayOrderList(pWriter,"updateOrder");
					}
					else {
						pWriter.println("<div id='body' class='width'>\n" +
								"<section id='content'>\n" +
								"<div style='height:50px;'></div>\n" +
								"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;'>\n" +
									"<div class='form_container'>\n" +
										"<br />\n" +
										"<table id='OrderDetails'>\n" +
											"<tr>\n" +	
												"<th colspan='3'><div align='center'>Order Details</div></th>\n" +
											"</tr>\n" +
											"<tr>\n" +	
												"<td colspan='3'>\n" +
												"<p>" + dbStatus.get("msg") + "</p>\n" +	
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
											"<td colspan='3' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/LoginServlet?op=SalesmanHome' method='get'>Salesman Home</a>\n" +
											"</td>\n" +
										"</tr>\n" +
									"</table>\n" +
								"</div>\n" +
							"</form>\n" +
							"</section>");
					}
					
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}