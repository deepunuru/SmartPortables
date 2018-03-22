package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import DataAccess.MySQLDataStoreUtilities;
import DataAccess.MongoDBDataStoreUtilities;
import Controller.HtmlEngine;
import JavaBeans.*;

public class DataExplorationServlet extends HttpServlet {
	
	public DataExplorationServlet() {
		
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
            "<script src='http://d3js.org/d3.v4.min.js' charset='utf-8'></script>\n" + 
            "<script src='https://d3js.org/d3-queue.v2.min.js'></script>\n" +
            "<script src='https://d3js.org/d3-scale-chromatic.v1.min.js'></script>\n" + 
            "<script src='https://d3js.org/topojson.v2.min.js'></script>\n" +
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
				if(op==null) {
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					displayLogoutHeader(pWriter,"DataExploration");
					
					pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
							"<div style='height:50px;'></div>\n" +
							"<div style='width: 100%;margin: 0 auto;height: 50%;border: 1px solid #ccc;border-radius: 15px;'>\n" +
								"<div class='form_container'>\n" +
									"<table id='addProductTable'>\n" +
										"<tr>\n" +	
											"<th><div align='center'>Data Exploration</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td style='text-align:center;'>\n" +
												"<p><a href='/csj/Controller/DataExplorationServlet?op=TotalReviews' method='GET'>Total number of Reviews per State</a></p>\n" +
												"<br />\n" +
												"<p><a href='/csj/Controller/DataExplorationServlet?op=TotalSalesCount' method='POST'>Total Products Bought per State</a></p>\n" +
												"<br />\n" +
												"<p><a href='/csj/Controller/DataExplorationServlet?op=TotalReviews5' method='POST'>Total number of Reviews with Rating 5</a></p>\n" +
												"<br />\n" +
												"<p><a href='/csj/Controller/DataExplorationServlet?op=AveragePrices' method='POST'>Average Product Prices sold in every State</a></p>\n" +
												"<br />\n" +
												"<p><a href='/csj/Controller/DataExplorationServlet?op=TotalSalesPrices' method='POST'>Total Product Prices per State</a></p>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
										"<td style='text-align:right;'>\n" +
											"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
											"<a href='/csj/Controller/DataExplorationServlet' method='get'>Data Exploration</a>\n" +
										"</td>\n" +
									"</tr>\n" +
								"</table>\n" +
								"</div>\n" +
							"</div>\n" +
						"</section>");
				
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
				}
				else {
					switch(op) {
						case "TotalReviews":{
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter,"DataExploration");
							
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form style='width: 100%;margin: 0 auto;height: 50%;border: 1px solid #ccc;border-radius: 15px;'>\n" +
										"<div class='form_container'>\n" +
											"<table id='addProductTable'>\n" +
												"<tr>\n" +	
													"<th><div align='center'>Total number of Reviews per State</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td style='text-align:center;'>\n" +
														"<svg width='960' height='600'></svg>\n" +
														"<p id='revCount'>Review Count:</p>\n" +
														"<script type='text/javascript' src='/csj/choroPleth.js'></script>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
													"<a href='/csj/Controller/DataExplorationServlet' method='get'>Data Exploration</a>\n" +
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
						case "TotalSalesCount":{
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter,"DataExploration");
							
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form style='width: 100%;margin: 0 auto;height: 50%;border: 1px solid #ccc;border-radius: 15px;' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='addProductTable'>\n" +
												"<tr>\n" +	
													"<th><div align='center'>Total Products Bought per State</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td style='text-align:center;'>\n" +
														"<svg width='960' height='600'></svg>\n" +
														"<p id='prodCount'>Total Products:</p>\n" +
														"<script type='text/javascript' src='/csj/choroPlethTotalSales.js'></script>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
													"<a href='/csj/Controller/DataExplorationServlet' method='get'>Data Exploration</a>\n" +
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
						case "TotalReviews5":{
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter,"DataExploration");
							
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form style='width: 100%;margin: 0 auto;height: 50%;border: 1px solid #ccc;border-radius: 15px;' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='addProductTable'>\n" +
												"<tr>\n" +	
													"<th><div align='center'>Total number of Reviews with Rating 5</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td style='text-align:center;'>\n" +
														"<svg width='960' height='600'></svg>\n" +
														"<p id='revCount'>Total Products:</p>\n" +
														"<script type='text/javascript' src='/csj/choroPlethTotRev5.js'></script>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
													"<a href='/csj/Controller/DataExplorationServlet' method='get'>Data Exploration</a>\n" +
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
						case "AveragePrices":{
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter,"DataExploration");
							
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form style='width: 100%;margin: 0 auto;height: 50%;border: 1px solid #ccc;border-radius: 15px;' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='addProductTable'>\n" +
												"<tr>\n" +	
													"<th colspan='4'><div align='center'>Average Price of Products sold in Every State</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='4' style='text-align:center;'>\n" +
														"<svg width='960' height='600'></svg>\n" +
														"<p id='avgPriceCount'>Average Product Price:</p>\n" +
														"<script type='text/javascript' src='/csj/choroPlethAvgPrices.js'></script>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td colspan='4' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
													"<a href='/csj/Controller/DataExplorationServlet' method='get'>Data Exploration</a>\n" +
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
						case "TotalSalesPrices":{
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter,"DataExploration");
							
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form style='width: 100%;margin: 0 auto;height: 50%;border: 1px solid #ccc;border-radius: 15px;' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='addProductTable'>\n" +
												"<tr>\n" +	
													"<th colspan='4'><div align='center'>Total Price of Products sold in Every State</div></th>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='4' style='text-align:center;'>\n" +
														"<svg width='960' height='600'></svg>\n" +
														"<p id='totProdPrices'>Total Product Prices:</p>\n" +
														"<script type='text/javascript' src='/csj/choroPlethTotPrices.js'></script>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
												"<td colspan='4' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
													"<a href='/csj/Controller/DataExplorationServlet' method='get'>Data Exploration</a>\n" +
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
}