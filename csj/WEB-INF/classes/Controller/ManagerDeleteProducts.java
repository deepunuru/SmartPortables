package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import DataAccess.MySQLDataStoreUtilities;
import JavaBeans.*;
import Controller.HtmlEngine;

public class ManagerDeleteProducts extends HttpServlet {	
	MySQLDataStoreUtilities dbObj;
	
	public ManagerDeleteProducts() {
		dbObj = new MySQLDataStoreUtilities();
	}
	
	public void printProduct(String[] imgList,String[] priceList,String[] nameList,String[] idList,PrintWriter pWriter,int k,String type){
		pWriter.println("<img src='/csj/" + imgList[k] + "' style='width:150px;height:150px' alt='" + imgList[k] +"'>\n" +
				"<p><a href='#' method='get'>" + nameList[k] +"</a>\n" +
				"<br />\n" +
				"Price: $" + priceList[k] +"\n" +
				"<br />\n" +
				"<a id='editProductLink' href='/csj/Controller/ManagerHomeServlet?op=updateForm&id=" + idList[k] + "&type=" + type + "' method='get'>Edit Product</a></p>\n" +
				"<br />");
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
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pWriter = response.getWriter();
		HtmlEngine engine = new HtmlEngine(pWriter);
		displayLogoutHeader(pWriter);
		
		pWriter.println("<div id='body' class='width'>\n" +
				"<section id='content'>\n" +
				"<div style='height:50px;'></div>\n" +
				"<form class='register' action='/csj/Controller/ManagerDeleteProducts' method='post'>\n" +
					"<div class='form_container'>\n" +
						"<table id='chooseProductTable'>\n" +
							"<tr>\n" +	
								"<th colspan='2'><div align='center'>Choose Product</div></th>\n" +
							"</tr>\n" +
							"<tr>\n" +	
								"<td colspan='2' style='text-align:center;'>\n" +
								"</td>\n" +
							"</tr>\n" +
							"<tr>\n" +
								"<td style='text-align:right;'>\n" +
									"<label for='ProductType'>Product Type : </label>\n" +
								"</td>\n" +
								"<td style='text-align:center;'>\n" +
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
								"<td colspan='2' align='center'>\n" +
									"<button name='sbmtButton' type='submit' style='width:80px;'>Submit</button>\n" +
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
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("productType");
		try {
			if(type!=null)
			{
				HashMap<String, String> dbStatus = new HashMap<String, String>();
				dbStatus = dbObj.checkDBConnection();
				if(dbStatus.get("status").equals("true")) {
					switch(type) 
					{
						case "smartwatches":{
							//display all smartwatches
							String[] imgList = new String[30];
							String[] priceList = new String[30];
							String[] nameList = new String[30];
							String[] idList = new String[30];
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter);
							
							
							int count=0;
							for(Map.Entry<String, SmartWatch> w : dbObj.getWatchMapDB().entrySet()) {					
								SmartWatch watch = w.getValue();
								imgList[count] = watch.getImage();
								priceList[count] = String.valueOf(watch.getPrice());
								nameList[count] = watch.getName();
								idList[count] = watch.getId();
								count++;
							}
							
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
										"<table id='SmartWatchList'>\n" +
											"<tr>\n" +
											"<td style='font-size:small;text-align:center;vertical-align: text-top;'>");
													for(int k=0;k<count;k++) {
														if(k%2 == 0) {
															printProduct(imgList,priceList,nameList,idList,pWriter,k,"smartwatches");
														}
													}
								pWriter.println("</td>\n" +
												"<td style='font-size:small;text-align:center;vertical-align: text-top;'>");
								for(int k=0;k<count;k++) {
									if(k%2 != 0) {
										printProduct(imgList,priceList,nameList,idList,pWriter,k,"smartwatches");
									}
								}
								pWriter.println("</td>\n" +
											"</tr>\n" +
											"<tr>\n" +	
											"<td colspan='2' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
											"</td>\n" +
										"</tr>\n" +
										"</table>\n" +
									"</section>");
							
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
							break;
						}
						case "speakers":{
							String[] imgList = new String[30];
							String[] priceList = new String[30];
							String[] nameList = new String[30];
							String[] idList = new String[30];
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);

							displayLogoutHeader(pWriter);
							
							int count=0;
							for(Map.Entry<String, Speakers> w : dbObj.getSpeakerMapDB().entrySet()) {	
								Speakers speaker = w.getValue();					
								imgList[count] = speaker.getImage();
								priceList[count] = String.valueOf(speaker.getPrice());
								nameList[count] = speaker.getName();
								idList[count] = speaker.getId();
								count++;
							}
							
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
										"<table id='SpeakerList'>\n" +
											"<tr>\n" +
											"<td style='font-size:small;text-align:center;vertical-align: text-top;'>");
													for(int k=0;k<count;k++) {
														if(k%2 == 0) {
															printProduct(imgList,priceList,nameList,idList,pWriter,k,"speakers");
														}
													}
								pWriter.println("</td>\n" +
												"<td style='font-size:small;text-align:center;vertical-align: text-top;'>");
								for(int k=0;k<count;k++) {
									if(k%2 != 0) {
										printProduct(imgList,priceList,nameList,idList,pWriter,k,"speakers");
									}
								}
								pWriter.println("</td>\n" +
											"</tr>\n" +
											"<tr>\n" +	
											"<td colspan='2' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
											"</td>\n" +
										"</tr>\n" +
										"</table>\n" +
									"</section>");
							
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
							break;
						}
						case "headphones":{
							String[] imgList = new String[30];
							String[] priceList = new String[30];
							String[] nameList = new String[30];
							String[] idList = new String[30];
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter);
							
							int count=0;
							for(Map.Entry<String, Headphones> w : dbObj.getHeadphoneMapDB().entrySet()) {						
								Headphones headphone = w.getValue();
								imgList[count] = headphone.getImage();
								priceList[count] = String.valueOf(headphone.getPrice());
								nameList[count] = headphone.getName();
								idList[count] = headphone.getId();
								count++;
							}				
							
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
										"<table id='HeadphoneList'>\n" +
											"<tr>\n" +
											"<td style='font-size:small;text-align:center;vertical-align: text-top;'>");
													for(int k=0;k<count;k++) {
														if(k%2 == 0) {
															printProduct(imgList,priceList,nameList,idList,pWriter,k,"headphones");
														}
													}
								pWriter.println("</td>\n" +
												"<td style='font-size:small;text-align:center;vertical-align: text-top;'>");
								for(int k=0;k<count;k++) {
									if(k%2 != 0) {
										printProduct(imgList,priceList,nameList,idList,pWriter,k,"headphones");
									}
								}
								pWriter.println("</td>\n" +
											"</tr>\n" +
											"<tr>\n" +	
											"<td colspan='2' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
											"</td>\n" +
										"</tr>\n" +
										"</table>\n" +
									"</section>");
							
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
							break;
						}
						case "smartphones":{
							String[] imgList = new String[30];
							String[] priceList = new String[30];
							String[] nameList = new String[30];
							String[] idList = new String[30];
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter);
							
							int count=0;
							for(Map.Entry<String, Phones> w : dbObj.getPhoneMapDB().entrySet()) {	
								Phones phone = w.getValue();
								imgList[count] = phone.getImage();
								priceList[count] = String.valueOf(phone.getPrice());
								nameList[count] = phone.getName();
								idList[count] = phone.getId();
								count++;
							}				
							
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
										"<table id='PhoneList'>\n" +
											"<tr>\n" +
											"<td style='font-size:small;text-align:center;vertical-align: text-top;'>");
													for(int k=0;k<count;k++) {
														if(k%2 == 0) {
															printProduct(imgList,priceList,nameList,idList,pWriter,k,"smartphones");
														}
													}
								pWriter.println("</td>\n" +
												"<td style='font-size:small;text-align:center;vertical-align: text-top;'>");
								for(int k=0;k<count;k++) {
									if(k%2 != 0) {
										printProduct(imgList,priceList,nameList,idList,pWriter,k,"smartphones");
									}
								}
								pWriter.println("</td>\n" +
											"</tr>\n" +
											"<tr>\n" +	
											"<td colspan='2' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
											"</td>\n" +
										"</tr>\n" +
										"</table>\n" +
									"</section>");
							
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
							break;
						}
						case "laptops":{
							String[] imgList = new String[30];
							String[] priceList = new String[30];
							String[] nameList = new String[30];
							String[] idList = new String[30];
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter);
							
							int count=0;
							for(Map.Entry<String, Laptops> w : dbObj.getLaptopMapDB().entrySet()) {	
								Laptops laptop = w.getValue();
								imgList[count] = laptop.getImage();
								priceList[count] = String.valueOf(laptop.getPrice());
								nameList[count] = laptop.getName();
								idList[count] = laptop.getId();
								count++;
							}				
							
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
										"<table id='LaptopList'>\n" +
											"<tr>\n" +
											"<td style='font-size:small;text-align:center;vertical-align: text-top;'>");
													for(int k=0;k<count;k++) {
														if(k%2 == 0) {
															printProduct(imgList,priceList,nameList,idList,pWriter,k,"laptops");
														}
													}
								pWriter.println("</td>\n" +
												"<td style='font-size:small;text-align:center;vertical-align: text-top;'>");
								for(int k=0;k<count;k++) {
									if(k%2 != 0) {
										printProduct(imgList,priceList,nameList,idList,pWriter,k,"laptops");
									}
								}
								pWriter.println("</td>\n" +
											"</tr>\n" +
											"<tr>\n" +	
											"<td colspan='2' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
											"</td>\n" +
										"</tr>\n" +
										"</table>\n" +
									"</section>");
							
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
							break;
						}
						case "externalstorage":{
							String[] imgList = new String[30];
							String[] priceList = new String[30];
							String[] nameList = new String[30];
							String[] idList = new String[30];
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter);
							
							int count=0;
							for(Map.Entry<String, ExternalStorage> w : dbObj.getStorageMapDB().entrySet()) {	
								ExternalStorage storage = w.getValue();
								imgList[count] = storage.getImage();
								priceList[count] = String.valueOf(storage.getPrice());
								nameList[count] = storage.getName();
								idList[count] = storage.getId();
								count++;
							}				
							
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
										"<table id='StorageList'>\n" +
											"<tr>\n" +
											"<td style='font-size:small;text-align:center;vertical-align: text-top;'>");
													for(int k=0;k<count;k++) {
														if(k%2 == 0) {
															printProduct(imgList,priceList,nameList,idList,pWriter,k,"externalstorage");
														}
													}
								pWriter.println("</td>\n" +
												"<td style='font-size:small;text-align:center;vertical-align: text-top;'>");
								for(int k=0;k<count;k++) {
									if(k%2 != 0) {
										printProduct(imgList,priceList,nameList,idList,pWriter,k,"externalstorage");
									}
								}
								pWriter.println("</td>\n" +
											"</tr>\n" +
											"<tr>\n" +	
											"<td colspan='2' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
											"</td>\n" +
										"</tr>\n" +
										"</table>\n" +
									"</section>");
							
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
							break;
						}
						case "accessories":{
							String[] imgList = new String[30];
							String[] priceList = new String[30];
							String[] nameList = new String[30];
							String[] idList = new String[30];
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							displayLogoutHeader(pWriter);
							
							int count=0;
							for(Map.Entry<String, Accessories> w : dbObj.getAccessoryMapDB().entrySet()) {	
								Accessories accessory = w.getValue();
								imgList[count] = accessory.getImage();
								priceList[count] = String.valueOf(accessory.getPrice());
								nameList[count] = accessory.getName();
								idList[count] = accessory.getId();
								count++;
							}				
							
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
										"<table id='AccessoryList'>\n" +
											"<tr>\n" +
											"<td style='font-size:small;text-align:center;vertical-align: text-top;'>");
													for(int k=0;k<count;k++) {
														if(k%2 == 0) {
															printProduct(imgList,priceList,nameList,idList,pWriter,k,"accessories");
														}
													}
								pWriter.println("</td>\n" +
												"<td style='font-size:small;text-align:center;vertical-align: text-top;'>");
								for(int k=0;k<count;k++) {
									if(k%2 != 0) {
										printProduct(imgList,priceList,nameList,idList,pWriter,k,"accessories");
									}
								}
								pWriter.println("</td>\n" +
											"</tr>\n" +
											"<tr>\n" +	
											"<td colspan='2' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
											"</td>\n" +
										"</tr>\n" +
										"</table>\n" +
									"</section>");
							
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
							break;
						}
						
						default:{
							response.sendRedirect("/csj/Controller/HomeServlet?var=home");
							break;
						}
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
								"<table id='ListTable'>\n" +
									"<tr>\n" +
										"<td colspan='2' style='text-align:center;'>\n" +
											"<p>" + dbStatus.get("msg") + "</p>\n" +										
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
				}
			}
			else {
				//display home page
				response.sendRedirect("/csj/Controller/HomeServlet?var=home");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
}