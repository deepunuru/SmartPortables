package Controller;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import javax.servlet.*;
import javax.servlet.http.*;

import org.bson.Document;
import org.bson.conversions.*;

import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.*;

import DataAccess.MySQLDataStoreUtilities;
import DataAccess.MongoDBDataStoreUtilities;
import Controller.HtmlEngine;
import JavaBeans.*;

public class DataAnalyticsServlet extends HttpServlet {
	MySQLDataStoreUtilities dbObj;
	MongoDBDataStoreUtilities mDBObj;
	
	public DataAnalyticsServlet() {
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
							if(selected.equals("DataAnalytics")) {
								pWriter.println("<li class='selected'><a href='/csj/Controller/DataAnalyticsServlet?op=home' method='get'>Data Analytics</a></li>\n" +
										"<li><a href='/csj/Controller/DataExplorationServlet' method='get'>Data Exploration</a></li>\n" +
										"<li><a href='/csj/Controller/DataAnalyticsServlet?op=inventory' method='get'>Inventory</a></li>\n" +
					    				"<li class='end'><a href='/csj/Controller/DataAnalyticsServlet?op=salesreports' method='get'>Sales Reports</a></li>");
							}
							if(selected.equals("DataExploration")) {
								pWriter.println("<li><a href='/csj/Controller/DataAnalyticsServlet?op=home' method='get'>Data Analytics</a></li>\n" +
										"<li class='selected'><a href='/csj/Controller/DataExplorationServlet' method='get'>Data Exploration</a></li>\n" +
										"<li><a href='/csj/Controller/DataAnalyticsServlet?op=inventory' method='get'>Inventory</a></li>\n" +
					    				"<li class='end'><a href='/csj/Controller/DataAnalyticsServlet?op=salesreports' method='get'>Sales Reports</a></li>");
							}
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
				String op = request.getParameter("op");
				if(op == null) {
					response.sendRedirect("/csj/Controller/HomeServlet?var=home");
				}
				else {
					switch(op) {
						case"home":{
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter,"DataAnalytics");
							SmartWatch watch = new SmartWatch();
							Speakers speaker = new Speakers();
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form style='width: 90%;margin: 0 auto;height: 50%;border: 1px solid #ccc;border-radius: 15px;' action='/csj/Controller/DataAnalyticsServlet?var=home' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='addProductTable'>\n" +
												"<tr>\n" +	
													"<th colspan='4'><div align='center'>Data Analytics on Review Data</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='4' style='text-align:center;'>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td style='text-align:left;'>\n" +
														"<input type='checkbox' name='selectChkBox' value='ProductName'> Select <br>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +
														"<label for='ProductName'>Product Name : </label>\n" +
													"</td>\n" +
													"<td colspan='2' style='text-align:left;'>\n" +
														"<select name='productName' id='productName' style='width:200px;'>\n" +
															"<option value='AllProducts'>All Products</option>");
														HashMap<String, String> dbStatus = new HashMap<String, String>();
														dbStatus = dbObj.checkDBConnection();
														if(dbStatus.get("status").equals("true")) {
															for(Map.Entry<String, SmartWatch> w : dbObj.getWatchMapDB().entrySet()) {					
																pWriter.println("<option value='" + w.getValue().getName() + "'>" + w.getValue().getName() + "</option>");
															}
															for(Map.Entry<String, Speakers> w : dbObj.getSpeakerMapDB().entrySet()) {					
																pWriter.println("<option value='" + w.getValue().getName() + "'>" + w.getValue().getName() + "</option>");
															}
															for(Map.Entry<String, Headphones> w : dbObj.getHeadphoneMapDB().entrySet()) {					
																pWriter.println("<option value='" + w.getValue().getName() + "'>" + w.getValue().getName() + "</option>");
															}
															for(Map.Entry<String, Phones> w : dbObj.getPhoneMapDB().entrySet()) {					
																pWriter.println("<option value='" + w.getValue().getName() + "'>" + w.getValue().getName() + "</option>");
															}
															for(Map.Entry<String, Laptops> w : dbObj.getLaptopMapDB().entrySet()) {					
																pWriter.println("<option value='" + w.getValue().getName() + "'>" + w.getValue().getName() + "</option>");
															}
															for(Map.Entry<String, ExternalStorage> w : dbObj.getStorageMapDB().entrySet()) {					
																pWriter.println("<option value='" + w.getValue().getName() + "'>" + w.getValue().getName() + "</option>");
															}
															for(Map.Entry<String, Accessories> w : dbObj.getAccessoryMapDB().entrySet()) {					
																pWriter.println("<option value='" + w.getValue().getName() + "'>" + w.getValue().getName() + "</option>");
															}
														}
														else {
															pWriter.println("<option value=''>" + dbStatus.get("msg") + "</option>");
														}
														
													pWriter.println("</select>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='4' style='text-align:center;'>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td style='text-align:left;'>\n" +
														"<input type='checkbox' name='selectChkBox' value='ProductPrice'> Select <br>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +
														"<label for='ProductPrice'>Product Price : </label>\n" +
													"</td>\n" +
													"<td style='text-align:left;width:200px;'>\n" +
														"<p><input type='text' id='productPrice' style='width:200px;text-align:left;' name='productPrice' value='0'/></p>\n" +
													"</td>\n" +
													"<td style='text-align:center;'>\n" +
														"<input type='radio' name='priceEquality' value='Equals' checked>Equals<br>\n" + 
														"<input type='radio' name='priceEquality' value='GreaterThan'>GreaterThan<br>\n" + 
														"<input type='radio' name='priceEquality' value='LessThan'>LessThan<br>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='4' style='text-align:center;'>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td style='text-align:left;'>\n" +
														"<input type='checkbox' name='selectChkBox' value='ReviewRating'> Select <br>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +
														"<label for='ReviewRating'>Review Rating : </label>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +														
														"<select name='reviewRating' id='reviewRating' style='width:200px;'>\n" + 
															"<option value='1'>1</option>\n" + 
															"<option value='2'>2</option>\n" + 
															"<option value='3'>3</option>\n" + 
															"<option value='4'>4</option>\n" +
															"<option value='5'>5</option>\n" +
														"</select>\n" +
													"</td>\n" +
													"<td style='text-align:center;'>\n" +
														"<input type='radio' name='ratingEquality' value='Equals' checked>Equals<br>\n" + 
														"<input type='radio' name='ratingEquality' value='GreaterThan'>GreaterThan<br>\n" + 
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='4' style='text-align:center;'>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td style='text-align:left;'>\n" +
														"<input type='checkbox' name='selectChkBox' value='RetailerCity'> Select <br>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +
														"<label for='RetailerCity'>Retailer City : </label>\n" +
													"</td>\n" +
													"<td style='text-align:left;width:200px;'>\n" +														
														"<p><input type='text' id='retailerCity' style='width:200px;' name='retailerCity'/></p>\n" +
													"</td>\n" +
													"<td>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td style='text-align:left;'>\n" +
														"<input type='checkbox' name='selectChkBox' value='GroupBy'> Select <br>\n" +
													"</td>\n" +
													"<td style='text-align:left;'>\n" +
														"<label for='GroupBy'>Group By : </label>\n" +
													"</td>\n" +
													"<td style='text-align:left;width:200px;'>\n" +														
														"<select name='groupBy' id='groupBy' style='width:200px;'>\n" + 
															"<option value='City'>City</option>\n" + 
															"<option value='ReviewCount'>ReviewCount</option>\n" + 
															"<option value='ZipCode'>ZipCode</option>\n" + 
														"</select>\n" +
													"</td>\n" +
													"<td style='text-align:center;'>\n" +
														"<input type='radio' name='sortType' value='Ascending' checked>Ascending<br>\n" + 
														"<input type='radio' name='sortType' value='Descending'>Descending<br>\n" +
														"<p>Limit : <input type='text' id='limit' style='width:80px;' name='limit' value='10'/></p>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='4' style='text-align:center;'>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td colspan='4' align='center'>\n" +
														"<button name='sbmtButton' type='submit' style='width:80px;'>Submit</button>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td colspan='4' style='text-align:right;'>\n" +
														"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
														"<a href='/csj/Controller/DataAnalyticsServlet?op=home' method='get'>Data Analytics</a>\n" +
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
						case "inventory":{
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter,"Inventory");
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form class='register' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='InventoryTable'>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:center;'>\n" +
														"<p><h2>Product Inventory</h2></p>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='2' style='text-align:center;'>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td colspan='2' style='text-align:center;'>\n" +
													"<p><a href='/csj/Controller/InventorySalesServlet?var=inventory&type=list' method='get'>Product Inventory List</a></p>\n" +
													"<br />\n" +
													"<p><a href='/csj/Controller/InventorySalesServlet?var=inventory&type=barchart' method='get'>Product Inventory Bar Chart</a></p>\n" +
													"<br />\n" +
													"<p><a href='/csj/Controller/InventorySalesServlet?var=productsOnSale' method='get'>Products On Sale</a></p>\n" +
													"<br />\n" +
													"<p><a href='/csj/Controller/InventorySalesServlet?var=rebate' method='get'>Products with Manufacturer Rebate</a></p>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
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
						case "salesreports":{
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter,"SalesReports");
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
													"<p><a href='/csj/Controller/InventorySalesServlet?var=salesreport' method='get'>Product Sales Report</a></p>\n" +
													"<br />\n" +
													"<p><a href='/csj/Controller/InventorySalesServlet?var=saleschart' method='get'>Product Sales Bar Chart</a></p>\n" +
													"<br />\n" +
													"<p><a href='/csj/Controller/InventorySalesServlet?var=dailysales' method='get'>Total Daily Sales</a></p>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
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
						default:{
							break;
						}
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
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String var = request.getParameter("var");
		try {
			switch(var) {
				case"home":{
					String selectChkBox[] = request.getParameterValues("selectChkBox");
					String reviewRating = request.getParameter("reviewRating");
					String ratingEquality = request.getParameter("ratingEquality");
					
					String productName = request.getParameter("productName");
					
					String productPrice = request.getParameter("productPrice");
					//System.out.println("productPrice-" + productPrice);
					String priceEquality = request.getParameter("priceEquality");
					
					String city = request.getParameter("retailerCity");
					String limitVal = request.getParameter("limit");
					
					BasicDBObject query = new BasicDBObject();
					//BasicDBObject group = new BasicDBObject();
					//BasicDBObject groupFields = new BasicDBObject();
					//BasicDBObject projectFields = new BasicDBObject();
					//BasicDBObject project = new BasicDBObject();
					//BasicDBObject sort = new BasicDBObject();
					//BasicDBObject orderBy = new BasicDBObject();
					//BasicDBObject limit = new BasicDBObject();
					
					Bson bList = null;
					Bson sortObj = null;
					Bson limitObj = null;
					
					String groupBy = request.getParameter("groupBy");
					String sortType = request.getParameter("sortType");
					
					boolean filterEnabled = false;
					boolean aggregationEnabled = false;
					
					//MongoCursor<Document> cursor = myReviews.find().sort(Filters.eq("reviewRating", -1)).limit(5).iterator();
					//FindIterable<Document> docList = myReviews.find();
					if(selectChkBox!=null) {
						for(String s: selectChkBox) {
							System.out.println("Value - " + s);
							switch(s) {
								case"ProductName":{
									filterEnabled = true;
									if(productName.equals("AllProducts")) {
										//Do nothing
										filterEnabled = false;
										//docList = docList.limit(10);
									}
									else {
										query.put("productName", productName);
										//docList = docList.find(Filters.eq("productName", productName));
									}
									break;
								}
								case"ProductPrice":{
									filterEnabled = true;
									if(priceEquality.equals("Equals")) {
										query.put("productPrice", Double.parseDouble(productPrice));
									}
									else if(priceEquality.equals("GreaterThan")) {
										query.put("productPrice", new BasicDBObject("$gt",Double.parseDouble(productPrice)));
									}
									else {
										query.put("productPrice", new BasicDBObject("$lt",Double.parseDouble(productPrice)));
									}
									break;
								}
								case"ReviewRating":{
									filterEnabled = true;
									if(ratingEquality.equals("Equals")) {
										query.put("reviewRating", Integer.parseInt(reviewRating));
									}
									else {
										query.put("reviewRating", new BasicDBObject("$gt",Integer.parseInt(reviewRating)));
									}
									break;
								}
								case"RetailerCity":{
									filterEnabled = true;
									if(!city.isEmpty()) {
										query.put("city", city);
									}
									else {
										filterEnabled = false;
									}
									break;
								}
								case"GroupBy":{
									aggregationEnabled = true;
									filterEnabled = false;
									//groupFields = new BasicDBObject("_id",0);
									//groupFields.put("count", new BasicDBObject("$sum",1));
									
									if(groupBy.equals("City")) {
										//groupFields.put("_id", "$city");
										bList = Aggregates.group("$city", Accumulators.sum("ReviewValue", 1));
									}
									else if(groupBy.equals("ReviewCount")) {
										//groupFields.put("_id", "$productName");
										bList = Aggregates.group("$productName", Accumulators.sum("ReviewValue", 1));
									}
									else {
										//groupFields.put("_id", "$zipCode");
										bList = Aggregates.group("$zipCode", Accumulators.sum("ReviewValue", 1));
									}
									//group = new BasicDBObject("$group",groupFields);
									
									
									
									
									
											
									//sort = new BasicDBObject();
									//projectFields.put("value", "$_id");
									//projectFields.put("ReviewValue", "$count");
									//project = new BasicDBObject("$project",projectFields);
									if(sortType.equals("Ascending")) {
										//sort.put("ReviewValue", +1);
										sortObj = Aggregates.sort(com.mongodb.client.model.Sorts.orderBy(com.mongodb.client.model.Sorts.ascending("ReviewValue")));
									}
									else {
										//sort.put("ReviewValue", -1);
										sortObj = Aggregates.sort(com.mongodb.client.model.Sorts.orderBy(com.mongodb.client.model.Sorts.descending("ReviewValue")));
									}
									//orderBy = new BasicDBObject("$sort",sort);
									//limit = new BasicDBObject("$limit",Integer.parseInt(limitVal));
									limitObj = Aggregates.limit(Integer.parseInt(limitVal));
									break;
								}
							}
						}
					}
					else {
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						displayLogoutHeader(pWriter,"DataAnalytics");
						pWriter.println("<div id='body' class='width'>\n" +
								"<section id='content'>\n" +
								"<div style='height:50px;'></div>\n" +
								"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
									"<div class='form_container'>\n" +
										"<table id='ProductListTable'>\n" +
											"<tr>\n" +	
												"<th colspan='4'><div align='center'>Product List</div></th>\n" +
											"</tr>\n" +
											"<tr>\n" +	
												"<td colspan='4' style='text-align:center;'>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +	
												"<td colspan='4' style='text-align:center;'>\n" +
													"<label for='orderStatusLbl'>Please select a valid option to proceed further.</label>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
											"<td colspan='4' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
												"<a href='/csj/Controller/DataAnalyticsServlet?op=home' method='get'>Data Analytics</a>\n" +
											"</td>\n" +
									"</tr>\n" +
								"</table>\n" +
								"</div>\n" +
							"</form>\n" +
						"</section>");
						engine.generateHtml("LeftNavigationBar",request);
						engine.generateHtml("Footer",request);
						return;
					}
					
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					dbStatus = mDBObj.checkMongoDB();
					if(dbStatus.get("status").equals("true")) {
						MongoCollection<Document> myReviews = mDBObj.getCollection();
						
						FindIterable<Document> docs=null;
						AggregateIterable<Document> iterable=null;
						try {
							if(aggregationEnabled == true) {
								//docs = myReviews.aggregate(group,project,orderBy,limit);
								iterable = myReviews.aggregate(Arrays.asList(bList,sortObj,limitObj));
							}
							else {
								if(filterEnabled == false) {
									docs = myReviews.find().limit(20);
								}
								else {
									docs = myReviews.find(query).limit(20);
								}
							}
						}
						catch(Exception ex) {	
							ex.printStackTrace();
						}
						
						
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						displayLogoutHeader(pWriter,"DataAnalytics");
						
						pWriter.println("<div id='body' class='width'>\n" +
								"<section id='content'>\n" +
								"<div style='height:50px;'></div>\n" +
								"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
									"<div class='form_container'>\n" +
										"<table id='ProductListTable'>\n" +
											"<tr>\n" +	
												"<th colspan='4'><div align='center'>Product List</div></th>\n" +
											"</tr>\n" +
											"<tr>\n" +	
												"<td colspan='4' style='text-align:center;'>\n" +
												"</td>\n" +
											"</tr>");
						
						
						MongoCursor<Document> cursor;
						
						if(aggregationEnabled == true) {
							cursor = iterable.iterator();
							if(!cursor.hasNext()) {
								pWriter.println("<tr>\n" +	
										"<td colspan='4' style='text-align:center;'>\n" +
											"<label for='orderStatusLbl'>No Results to Display</label>\n" +
										"</td>\n" +
									"</tr>");
							}
							else {
								try {
									while (cursor.hasNext()) {
										Document doc = cursor.next();
											pWriter.println("<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
													"<td colspan='2' style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>");
														if(groupBy.equals("ZipCode")) {
															pWriter.println("<p>GroupID: " + doc.getInteger("_id") + "</p>");
														}
														else {
															pWriter.println("<p>GroupID: " + doc.getString("_id") + "</p>");
														}
														pWriter.println("</td>\n" +
													"<td colspan='2' style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
														"<p>Count: " + doc.getInteger("ReviewValue") + "</p>\n" +
													"</td>\n" +
													"</tr>\n" +
													"<tr>\n" +
														"<td colspan='4'>\n" +
															"<p></p>\n" +
														"</td>\n" +
													"</tr>");		
									}
								} 
								finally {
									cursor.close();
								}
							}
						}
						else {
							cursor = docs.iterator();
							if(!cursor.hasNext()) {
								pWriter.println("<tr>\n" +	
										"<td colspan='4' style='text-align:center;'>\n" +
											"<label for='orderStatusLbl'>No Reviews to Display</label>\n" +
										"</td>\n" +
									"</tr>");
							}
							else {
								try {
									while (cursor.hasNext()) {
										Document doc = cursor.next();
											pWriter.println("<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
													"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
														"<p>Product Name: " + doc.getString("productName") + "</p>\n" +
													"</td>\n" +
													"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
														"<p>Product Type: " + doc.getString("productType") + "</p>\n" +
													"</td>\n" +
													"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
														"<p>Username: " + doc.getString("username") + "</p>\n" +
													"</td>\n" +
													"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
														"<p>ZipCode: " + String.valueOf(doc.getInteger("zipCode")) + "</p>\n" +
													"</td>\n" +
													"</tr>\n" +
													"<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
													"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +									
														"<p>Review Rating: " + String.valueOf(doc.getInteger("reviewRating")) + "</p>\n" +
													"</td>\n" +
													"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
														"<p>Review Date: " + doc.getString("reviewDate") + "</p>\n" +
													"</td>\n" +
													"<td colspan='2' style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
														"<p>Review Text: " + doc.getString("reviewText") + "</p>\n" +
													"</td>\n" +
													"</tr>\n" +
													"<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
													"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +									
														"<p>Product Price: " + String.valueOf(doc.getDouble("productPrice")) + "</p>\n" +
													"</td>\n" +
													"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
														"<p>City: " + doc.getString("city") + "</p>\n" +
													"</td>\n" +
													"<td colspan='2' style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
													"</td>\n" +
													"</tr>\n" +
													"<tr>\n" +
														"<td colspan='4'>\n" +
															"<p></p>\n" +
														"</td>\n" +
													"</tr>");		
									}
								} 
								finally {
									cursor.close();
								}
							}
						}
						mDBObj.disconnectMongoDB();
						pWriter.println("<tr>\n" +
									"<td colspan='4' style='text-align:right;'>\n" +
										"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
										"<a href='/csj/Controller/DataAnalyticsServlet?op=home' method='get'>Data Analytics</a>\n" +
									"</td>\n" +
							"</tr>\n" +
						"</table>\n" +
						"</div>\n" +
					"</form>\n" +
				"</section>");
						
						engine.generateHtml("LeftNavigationBar",request);
						engine.generateHtml("Footer",request);
					}
					else {
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						displayLogoutHeader(pWriter,"DataAnalytics");
						
						pWriter.println("<div id='body' class='width'>\n" +
								"<section id='content'>\n" +
								"<div style='height:50px;'></div>\n" +
								"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
									"<div class='form_container'>\n" +
										"<table id='ProductListTable'>\n" +
											"<tr>\n" +	
												"<th colspan='4'><div align='center'>Product List</div></th>\n" +
											"</tr>\n" +
											"<tr>\n" +	
												"<td colspan='4' style='text-align:center;'>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +	
												"<td colspan='4' style='text-align:center;'>\n" +
													"<p>" + dbStatus.get("msg") + "</p>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
											"<td colspan='4' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
												"<a href='/csj/Controller/DataAnalyticsServlet?op=home' method='get'>Data Analytics</a>\n" +
											"</td>\n" +
									"</tr>\n" +
								"</table>\n" +
								"</div>\n" +
							"</form>\n" +
							"</section>");
			
						engine.generateHtml("LeftNavigationBar",request);
						engine.generateHtml("Footer",request);
					}
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
}