package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import DataAccess.MySQLDataStoreUtilities;
import DataAccess.MongoDBDataStoreUtilities;
import Controller.HtmlEngine;
import JavaBeans.*;

public class InventorySalesServlet extends HttpServlet {
	
	MySQLDataStoreUtilities dbObj;
	MongoDBDataStoreUtilities mDBObj;
	
	public InventorySalesServlet() {
		dbObj = new MySQLDataStoreUtilities();
		mDBObj = new MongoDBDataStoreUtilities();
	}
	
	public void displayLogoutHeader(PrintWriter pWriter,String selected) {
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
            "<script type='text/javascript' src='/csj/accessoryScript.js'></script>\n" +  
            "<script type='text/javascript' src='https://www.google.com/jsapi'></script>\n" +
            "<script type='text/javascript' src='/csj/dataVisualization.js'></script>\n" +
            "<script type='text/javascript' src='/csj/salesChart.js'></script>\n" +
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
            				"<li class='start'><a href='/csj/Controller/HomeServlet?var=home' method='get'>Home</a></li>\n" +
            				"<li><a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a></li>\n" +
            				"<li><a href='/csj/Controller/HomeServlet?var=accessories' method='get'>Accessories</a></li>\n" +
            				"<li><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>\n");
							if(selected.equals("Inventory")) {
								pWriter.println("<li><a href='/csj/Controller/DataAnalyticsServlet?op=home' method='get'>Data Analytics</a></li>\n" +
										"<li><a href='/csj/Controller/DataExplorationServlet' method='get'>Data Exploration</a></li>\n" +
										"<li class='selected'><a href='/csj/Controller/DataAnalyticsServlet?op=inventory' method='get'>Inventory</a></li>\n" +
			            				"<li class='end'><a href='/csj/Controller/DataAnalyticsServlet?op=salesreports' method='get'>Sales Reports</a></li>");
							}
							if(selected.equals("SalesReports")) {
								pWriter.println("<li><a href='/csj/Controller/DataAnalyticsServlet?op=home' method='get'>Data Analytics</a></li>\n" +
										"<li><a href='/csj/Controller/DataExplorationServlet' method='get'>Data Exploration</a></li>\n" +
										"<li><a href='/csj/Controller/DataAnalyticsServlet?op=inventory' method='get'>Inventory</a></li>\n" +
			            				"<li class='end selected'><a href='/csj/Controller/DataAnalyticsServlet?op=salesreports' method='get'>Sales Reports</a></li>");
							}
            			pWriter.println("</ul>\n" +
            			"</td>\n" +
            			"<td style='width:75px;'>\n" +
            			"</td>\n" +
            		"</table>\n" +
            		"</div>\n" +
				"</nav>");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession userSession = request.getSession(true);
			String manager = (String)userSession.getAttribute("managerKey");
			if(manager!=null) {
				String var = request.getParameter("var");
				switch(var) {
					case "inventory":{
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						displayLogoutHeader(pWriter,"Inventory");
						
						HashMap<String, String> dbStatus = new HashMap<String, String>();
						dbStatus = dbObj.checkDBConnection();
						if(dbStatus.get("status").equals("true")) {
							String type = request.getParameter("type");
							if(type.equals("list")) {
								pWriter.println("<div id='body' class='width'>\n" +
										"<section id='content'>\n" +
										"<div style='height:50px;'></div>\n" +
										"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
											"<div class='form_container'>\n" +
												"<table id='ProductInventoryTable'>\n" +
													"<tr>\n" +	
														"<th colspan='3'><div align='center'>Product Inventory List</div></th>\n" +
													"</tr>\n" +
													"<tr>\n" +	
														"<th><div align='center'>Product Name</div></th>\n" +
														"<th><div align='center'>Price</div></th>\n" +
														"<th><div align='center'>Quantity Available</div></th>\n" +
													"</tr>");
								
								HashMap<String, Inventory> inventoryMap = dbObj.getInventoryMapDB();
								if(inventoryMap==null || inventoryMap.isEmpty()) {
									pWriter.println("<tr>\n" +	
											"<td colspan='3' style='text-align:center;'>\n" +
												"<label for='orderStatusLbl'>Inventory is Empty</label>\n" +
											"</td>\n" +
										"</tr>");
								}
								else {
									Inventory inventory = new Inventory();
									for(Map.Entry<String, Inventory> w : inventoryMap.entrySet()) {
										inventory = w.getValue();
										pWriter.println("<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
												"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
													"<p>" + inventory.getProductName() + "</p>\n" +
												"</td>\n" +
												"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
													"<p>$" + String.valueOf(inventory.getPrice()) + "</p>\n" +
												"</td>\n" +
												"<td style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
													"<p>" + String.valueOf(inventory.getQnty()) + "</p>\n" +
												"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td colspan='3'>\n" +
														"<p></p>\n" +
													"</td>\n" +
												"</tr>");		
									}
								}
										pWriter.println("<tr>\n" +
												"<td colspan='3' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/DataAnalyticsServlet?op=inventory' method='get'>Inventory Home</a>\n" +
												"</td>\n" +
										"</tr>\n" +
									"</table>\n" +
									"</div>\n" +
								"</form>\n" +
							"</section>");
							}
							else if(type.equals("barchart")) {
								pWriter.println("<div id='body' class='width'>\n" +
										"<section id='content'>\n" +
										"<div style='height:50px;'></div>\n" +
										"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
											"<div class='form_container'>\n" +
												"<table id='ProductListTable'>\n" +
													"<tr>\n" +	
														"<th><div align='center'>Product Inventory Bar Chart</div></th>\n" +
													"</tr>\n" +
													"<tr>\n" +	
														"<td style='text-align:center;'>\n" +
															"<div id='productBarChart'></div>\n" +
														"</td>\n" +
													"</tr>\n" +
													"<tr>\n" +
													"<td style='text-align:right;'>\n" +
														"<a href='/csj/Controller/DataAnalyticsServlet?op=inventory' method='get'>Inventory Home</a>\n" +
													"</td>\n" +
											"</tr>\n" +
										"</table>\n" +
										"</div>\n" +
									"</form>\n" +
								"</section>");
							}
						}
						else {
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
										"<div class='form_container'>\n" +
											"<table id='ProductListTable'>\n" +
												"<tr>\n" +	
													"<th colspan='4'><div align='center'>Product Inventory</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='4' style='text-align:center;'>\n" +
														"<p>" + dbStatus.get("msg") + "</p>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td colspan='4' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/DataAnalyticsServlet?op=inventory' method='get'>Inventory Home</a>\n" +
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
					case "productsOnSale":{
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						displayLogoutHeader(pWriter,"Inventory");
						
						HashMap<String, String> dbStatus = new HashMap<String, String>();
						dbStatus = dbObj.checkDBConnection();
						if(dbStatus.get("status").equals("true")) {
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
										"<div class='form_container'>\n" +
											"<table id='ProductInventoryTable'>\n" +
												"<tr>\n" +	
													"<th colspan='4'><div align='center'>ProductsOnSale List</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<th><div align='center'>Product Name</div></th>\n" +
													"<th><div align='center'>Price</div></th>\n" +
													"<th><div align='center'>ProductOnSale</div></th>\n" +
													"<th><div align='center'>Rebate</div></th>\n" +
												"</tr>");
							
							HashMap<String, Inventory> productOnSaleMap = dbObj.getProductOnSaleMapDB();
							if(productOnSaleMap==null || productOnSaleMap.isEmpty()) {
								pWriter.println("<tr>\n" +	
										"<td colspan='4' style='text-align:center;'>\n" +
											"<label for='orderStatusLbl'>Product List is Empty</label>\n" +
										"</td>\n" +
									"</tr>");
							}
							else {
								Inventory inventory = new Inventory();
								for(Map.Entry<String, Inventory> w : productOnSaleMap.entrySet()) {
									inventory = w.getValue();
									String onSaleStatus = null;
									if(inventory.getProductOnSale()) {
										onSaleStatus = "Yes";
									}
									else {
										onSaleStatus = "No";
									}
									String rebateStatus = null;
									if(inventory.getRebate()) {
										rebateStatus = "Yes";
									}
									else {
										rebateStatus = "No";
									}
									pWriter.println("<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
											"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
												"<p>" + inventory.getProductName() + "</p>\n" +
											"</td>\n" +
											"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
												"<p>$" + String.valueOf(inventory.getPrice()) + "</p>\n" +
											"</td>\n" +
											"<td style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
												"<p>" + onSaleStatus + "</p>\n" +
											"</td>\n" +
											"<td style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
												"<p>" + rebateStatus + "</p>\n" +
											"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='4'>\n" +
													"<p></p>\n" +
												"</td>\n" +
											"</tr>");		
								}
							}
									pWriter.println("<tr>\n" +
											"<td colspan='4' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/DataAnalyticsServlet?op=inventory' method='get'>Inventory Home</a>\n" +
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
									"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
										"<div class='form_container'>\n" +
											"<table id='ProductListTable'>\n" +
												"<tr>\n" +	
													"<th colspan='4'><div align='center'>ProductsOnSale List</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='4' style='text-align:center;'>\n" +
														"<p>" + dbStatus.get("msg") + "</p>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td colspan='4' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/DataAnalyticsServlet?op=inventory' method='get'>Inventory Home</a>\n" +
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
					case "rebate":{
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						displayLogoutHeader(pWriter,"Inventory");
						
						HashMap<String, String> dbStatus = new HashMap<String, String>();
						dbStatus = dbObj.checkDBConnection();
						if(dbStatus.get("status").equals("true")) {
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
										"<div class='form_container'>\n" +
											"<table id='ProductInventoryTable'>\n" +
												"<tr>\n" +	
													"<th colspan='4'><div align='center'>Rebate List</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<th><div align='center'>Product Name</div></th>\n" +
													"<th><div align='center'>Price</div></th>\n" +
													"<th><div align='center'>ProductOnSale</div></th>\n" +
													"<th><div align='center'>Rebate</div></th>\n" +
												"</tr>");
							
							HashMap<String, Inventory> rebateMap = dbObj.getRebateMapDB();
							if(rebateMap==null || rebateMap.isEmpty()) {
								pWriter.println("<tr>\n" +	
										"<td colspan='4' style='text-align:center;'>\n" +
											"<label for='orderStatusLbl'>Product Rebate List is Empty</label>\n" +
										"</td>\n" +
									"</tr>");
							}
							else {
								Inventory inventory = new Inventory();
								for(Map.Entry<String, Inventory> w : rebateMap.entrySet()) {
									inventory = w.getValue();
									String onSaleStatus = null;
									if(inventory.getProductOnSale()) {
										onSaleStatus = "Yes";
									}
									else {
										onSaleStatus = "No";
									}
									String rebateStatus = null;
									if(inventory.getRebate()) {
										rebateStatus = "Yes";
									}
									else {
										rebateStatus = "No";
									}
									pWriter.println("<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
											"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
												"<p>" + inventory.getProductName() + "</p>\n" +
											"</td>\n" +
											"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
												"<p>$" + String.valueOf(inventory.getPrice()) + "</p>\n" +
											"</td>\n" +
											"<td style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
												"<p>" + onSaleStatus + "</p>\n" +
											"</td>\n" +
											"<td style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
												"<p>" + rebateStatus + "</p>\n" +
											"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='4'>\n" +
													"<p></p>\n" +
												"</td>\n" +
											"</tr>");		
								}
							}
									pWriter.println("<tr>\n" +
											"<td colspan='4' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/DataAnalyticsServlet?op=inventory' method='get'>Inventory Home</a>\n" +
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
									"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
										"<div class='form_container'>\n" +
											"<table id='ProductListTable'>\n" +
												"<tr>\n" +	
													"<th colspan='4'><div align='center'>Products OnSale</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='4' style='text-align:center;'>\n" +
														"<p>" + dbStatus.get("msg") + "</p>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td colspan='4' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/DataAnalyticsServlet?op=inventory' method='get'>Inventory Home</a>\n" +
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
					case "salesreport":{
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						displayLogoutHeader(pWriter,"SalesReports");
						
						HashMap<String, String> dbStatus = new HashMap<String, String>();
						dbStatus = dbObj.checkDBConnection();
						if(dbStatus.get("status").equals("true")) {
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
										"<div class='form_container'>\n" +
											"<table id='SalesTable'>\n" +
												"<tr>\n" +	
													"<th colspan='4'><div align='center'>Product Sales Report</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<th><div align='center'>Product Name</div></th>\n" +
													"<th><div align='center'>Product Price</div></th>\n" +
													"<th><div align='center'>SalesCount</div></th>\n" +
													"<th><div align='center'>TotalSales</div></th>\n" +
												"</tr>");
							
							HashMap<String, Sales> salesMap = dbObj.getSalesMapDB();
							if(salesMap==null || salesMap.isEmpty()) {
								pWriter.println("<tr>\n" +	
										"<td colspan='4' style='text-align:center;'>\n" +
											"<label for='orderStatusLbl'>Sales List is Empty</label>\n" +
										"</td>\n" +
									"</tr>");
							}
							else {
								Sales sales = new Sales();
								for(Map.Entry<String, Sales> w : salesMap.entrySet()) {
									sales = w.getValue();
									pWriter.println("<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
											"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
												"<p>" + sales.getProductName() + "</p>\n" +
											"</td>\n" +
											"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
												"<p>$" + String.valueOf(sales.getPrice()) + "</p>\n" +
											"</td>\n" +
											"<td style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
												"<p>" + String.valueOf(sales.getSalesCount()) + "</p>\n" +
											"</td>\n" +
											"<td style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
												"<p>$" + String.format("%.2f", sales.getTotalSales()) + "</p>\n" +
											"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='4'>\n" +
													"<p></p>\n" +
												"</td>\n" +
											"</tr>");		
								}
							}
									pWriter.println("<tr>\n" +
											"<td colspan='4' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/DataAnalyticsServlet?op=salesreports' method='get'>SalesReport Home</a>\n" +
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
									"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
										"<div class='form_container'>\n" +
											"<table id='SalesTable'>\n" +
												"<tr>\n" +	
													"<th colspan='4'><div align='center'>Product Sales Report</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='4' style='text-align:center;'>\n" +
														"<p>" + dbStatus.get("msg") + "</p>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td colspan='4' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/DataAnalyticsServlet?op=salesreports' method='get'>Inventory Home</a>\n" +
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
					case "saleschart":{
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						displayLogoutHeader(pWriter,"SalesReports");
						pWriter.println("<div id='body' class='width'>\n" +
								"<section id='content'>\n" +
								"<div style='height:50px;'></div>\n" +
								"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
									"<div class='form_container'>\n" +
										"<table id='SalesTable'>\n" +
											"<tr>\n" +	
												"<th><div align='center'>Product Sales Bar Chart</div></th>\n" +
											"</tr>\n" +
											"<tr>\n" +	
												"<td style='text-align:center;'>\n" +
													"<div id='salesBarChart'></div>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
											"<td style='text-align:right;'>\n" +
												"<a href='/csj/Controller/DataAnalyticsServlet?op=salesreports' method='get'>SalesReport Home</a>\n" +
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
					case "dailysales":{
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						displayLogoutHeader(pWriter,"SalesReports");
						
						HashMap<String, String> dbStatus = new HashMap<String, String>();
						dbStatus = dbObj.checkDBConnection();
						if(dbStatus.get("status").equals("true")) {
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
										"<div class='form_container'>\n" +
											"<table id='SalesTable'>\n" +
												"<tr>\n" +	
													"<th colspan='2'><div align='center'>Daily Sales</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<th><div align='center'>Order Date</div></th>\n" +
													"<th><div align='center'>Daily Total</div></th>\n" +
												"</tr>");
													
							Vector<Sales> salesList = dbObj.getDailySalesData();
							if(salesList==null || salesList.isEmpty()) {
								pWriter.println("<tr>\n" +	
										"<td colspan='2' style='text-align:center;'>\n" +
											"<label for='orderStatusLbl'>Sales List is Empty</label>\n" +
										"</td>\n" +
									"</tr>");
							}
							else {
								Sales sales = new Sales();
								for(Sales w : salesList) {
									pWriter.println("<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
											"<td style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
												"<p>" + w.getOrderDate() + "</p>\n" +
											"</td>\n" +
											"<td style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
												"<p>$" + String.format("%.2f", w.getTotalSales()) + "</p>\n" +
											"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='3'>\n" +
													"<p></p>\n" +
												"</td>\n" +
											"</tr>");		
								}
							}
									pWriter.println("<tr>\n" +
											"<td colspan='3' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/DataAnalyticsServlet?op=salesreports' method='get'>SalesReport Home</a>\n" +
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
									"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
										"<div class='form_container'>\n" +
											"<table id='ProductListTable'>\n" +
												"<tr>\n" +	
													"<th colspan='3'><div align='center'>Product Sales Report</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='3' style='text-align:center;'>\n" +
														"<p>" + dbStatus.get("msg") + "</p>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td colspan='3' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/DataAnalyticsServlet?op=salesreports' method='get'>Inventory Home</a>\n" +
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
					default:{
						break;
					}
				}
				
			}
			else {
				response.sendRedirect("/csj/Controller/LoginServlet");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}