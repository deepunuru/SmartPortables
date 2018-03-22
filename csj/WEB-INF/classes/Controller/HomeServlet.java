package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import JavaBeans.*;
import Controller.HtmlEngine;
import Controller.SaxParserDataStore;
import Controller.DOMParser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import java.util.regex.Pattern;
import DataAccess.MySQLDataStoreUtilities;

public class HomeServlet extends HttpServlet {

	public static SaxParserDataStore datastoreObj;
	public static MySQLDataStoreUtilities dbObj;
	
	
	public void init() throws ServletException{
		initializeParser();
		dbObj = new MySQLDataStoreUtilities();
		HashMap<String, String> dbStatus = new HashMap<String, String>();
		dbObj.createDB();
		dbStatus = dbObj.checkDBConnection();
		if(dbStatus.get("status").equals("true")) {
			dbObj.populateDatabase(datastoreObj);
			dbObj.insertSampleUser();
			dbObj.insertSampleOrders();
		}
		else {
			System.out.println("Msg-" + dbStatus.get("msg"));
		}
		//parser.addHashMap(absoluteDiskPath);
		//watchHashMap = parser.displayData();
	}
	
	public void initializeParser() {
		try {
			String relativeWebPath = "/WEB-INF/classes/ProductCatalog.xml";
			String absoluteDiskPath = getServletContext().getRealPath(relativeWebPath);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			datastoreObj = new SaxParserDataStore();
			parser.parse(absoluteDiskPath, datastoreObj);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public HomeServlet() {
		
	}
	
	public void printHeaderJS(PrintWriter pWriter,HttpServletRequest request) {
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
		            "<script type='text/javascript' src='/csj/getQuantity.js'></script>\n" +  
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
		            "<script type='text/javascript' src='/csj/getQuantity.js'></script>\n" +  
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
	
	public void printProduct(String[] imgList,String[] priceList,String[] nameList,String[] idList,PrintWriter pWriter,int k,String type){
		pWriter.println("<img src='/csj/" + imgList[k] + "' style='width:150px;height:150px' alt='" + imgList[k] +"'>\n" +
				"<p><a href='/csj/Controller/DisplayProduct?id=" + idList[k] + "&type=" + type + "' method='get'>" + nameList[k] +"</a>\n" +
				"<br />\n" +
				"Price: $" + priceList[k] +"\n" +
				"<br />\n" +
				"<select id='qnty' onchange='getQuantity()'>\n" + 
				"  <option value='1'>1</option>\n" + 
				"  <option value='2'>2</option>\n" + 
				"  <option value='3'>3</option>\n" + 
				"  <option value='4'>4</option>\n" + 
				"  <option value='5'>5</option>\n" + 
				"  <option value='6'>6</option>\n" + 
				"  <option value='7'>7</option>\n" + 
				"  <option value='8'>8</option>\n" + 
				"  <option value='9'>9</option>\n" + 
				"  <option value='10'>10</option>\n" + 
				"</select>\n" +
				"<br />\n" +
				"<a id='buynowLink' href='/csj/Controller/PortableServlet?id=" + idList[k] + "&type=" + type + "&op=buynow&qnty=1' method='get'>Buy Now</a>\n" +
				"<br />\n" +
				"<a href='/csj/Controller/PortableServlet?id=" + idList[k] + "&type=" + type + "&op=addtocart' method='get'>Add To Cart</a>\n" +
				"<br />\n" +
				"<a id='writeReviewLink' href='/csj/Controller/ProductReview?name=" + nameList[k] + "&type=" + type + "&op=writeReview' method='get'>Write Review</a>\n" +
				"<br />\n" +
				"<a id='viewReviewsLink' href='/csj/Controller/ProductReview?name=" + nameList[k] + "&type=" + type + "&op=viewReviews' method='get'>View Review</a></p>\n" +
				"<br />");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			initializeParser();
			
			
			String var = request.getParameter("var");
			if(var == null)
			{
				response.setContentType("text/html");
				PrintWriter pWriter = response.getWriter();
				HtmlEngine engine = new HtmlEngine(pWriter);
				engine.generateHtml("Header",request);
				engine.generateHtml("Content",request);
				engine.generateHtml("LeftNavigationBar",request);
				engine.generateHtml("Footer",request);
			}
			else {
				switch(var) {
				case "home": {
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.generateHtml("Header",request);
					engine.generateHtml("Content",request);
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				
				case "products": {
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.getMatchingHeader(request,"products");
					engine.generateHtml("Content",request);
					/*pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
								"<table id='CustHomeTable'>\n" +
									"<tr>\n" +
									"<td style='font-size:small;text-align:center;'>\n" +
											"<img src='/csj/images/watchicon.png' style='width:150px;height:150px' alt='watchicon'>\n" +
											"<br />\n" +
											"<p><a href='/csj/Controller/HomeServlet?var=smartwatches' method='get'>Smart Watches</a></p>\n" +
										"</td>\n" +
										"<td style='font-size:small;text-align:center;'>\n" +
											"<img src='/csj/images/speakericon.png' style='width:150px;height:150px' alt='speakericon'>\n" +
											"<br />\n" +
											"<p><a href='/csj/Controller/HomeServlet?var=speakers' method='get'>Speakers</a></p>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='font-size:small;text-align:center;'>\n" +
											"<img src='/csj/images/headphonesicon.png' style='width:150px;height:150px' alt='headphonesicon'>\n" +
											"<br />\n" +
											"<p><a href='/csj/Controller/HomeServlet?var=headphones' method='get'>Headphones</a></p>\n" +
										"</td>\n" +
										"<td style='font-size:small;text-align:center;'>\n" +
											"<img src='/csj/images/phoneicon.png' style='width:150px;height:150px' alt='phoneicon'>\n" +
											"<br />\n" +
											"<p><a href='/csj/Controller/HomeServlet?var=smartphones' method='get'>Smart Phones</a></p>\n" +
										"</td>\n" +								
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='font-size:small;text-align:center;'>\n" +
											"<img src='/csj/images/laptopicon.png' style='width:150px;height:150px' alt='laptopicon'>\n" +
											"<br />\n" +
											"<p><a href='/csj/Controller/HomeServlet?var=laptops' method='get'>Laptops</a></p>\n" +
										"</td>\n" +
										"<td style='font-size:small;text-align:center;'>\n" +
											"<img src='/csj/images/storageicon.jpg' style='width:150px;height:150px' alt='storageicon'>\n" +
											"<br />\n" +
											"<p><a href='/csj/Controller/HomeServlet?var=externalstorage' method='get'>External Storage</a></p>\n" +
										"</td>\n" +								
									"</tr>\n" +
								"</table>\n" +
							"</section>");*/
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				
				
				case "contactus": {
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.getMatchingHeader(request,"contactus");
					
					pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
							"<legend></legend>\n" +
							"<p>Thank you for using Smart Portables website for your purchase. Please complete the form below, so we can provide quick and efficient service. If this is an urgent matter please contact Customer support:</p>\n" +
							"<div><b>U.S - 877-222-3322</b></div>\n" +
							"<div><b>Monday-Friday 07:30 AM - 10:30 PM CST</b></div>\n" +
							"<form class='register' action='/csj/Controller/HomeServlet?var=contactus' method='POST'>\n" +
								"<input type='hidden' id='form' name='form' value='contactus'/>\n" +
								"<div class='form_container'>\n" +
								"<br />\n" +
									"<table id='CustHomeTable'>\n" +
										"<tr>\n" +	
											"<th colspan='2'><div align='center'>Contact Us Form</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td align='right' style='font-size:small'>\n" +
												"<label for='name' style='font-size:small'>Name:</label>\n" +
											"</td>\n" +
											"<td align='left' style='font-size:small'>\n" +
												"<input name='name' id='name' type='text' />\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td align='right' style='font-size:small'>\n" +
												"<label for='email' style='font-size:small'>Email:</label>\n" +
											"</td>\n" +
											"<td align='left' style='font-size:small'>\n" +
												"<input name='email' id='email' type='text' />\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td align='right' style='font-size:small'>\n" +
												"<p><label for='message' style='font-size:small'>Message:</label>\n" +
											"</td>\n" +
											"<td align='left' style='font-size:small'>\n" +
												"<textarea cols='37' rows='11' name='message' id='message'></textarea></p>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td colspan='2' align='center'>\n" +
												"<button name='sbmtButton' type='submit' style='width:80px;'>Submit</button>\n" +
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
				
				case "smartwatches": {
					initializeParser();
					String[] imgList = new String[30];
					String[] priceList = new String[30];
					String[] nameList = new String[30];
					String[] idList = new String[30];
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					printHeaderJS(pWriter,request);
					
					
					int count=0;
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
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
									"</table>\n" +
								"</section>");
					}
					else {
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
									"</table>\n" +
								"</div>\n" +
								"</form>\n" +
								"</section>");
					}
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				
				case "speakers": {
					initializeParser();
					String[] imgList = new String[30];
					String[] priceList = new String[30];
					String[] nameList = new String[30];
					String[] idList = new String[30];
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);

					printHeaderJS(pWriter,request);
					
					int count=0;
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
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
									"</table>\n" +
								"</section>");
					}
					else {
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
									"</table>\n" +
								"</div>\n" +
								"</form>\n" +
								"</section>");
					}
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				
				case "headphones": {
					initializeParser();
					String[] imgList = new String[30];
					String[] priceList = new String[30];
					String[] nameList = new String[30];
					String[] idList = new String[30];
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					printHeaderJS(pWriter,request);
					
					int count=0;
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
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
									"</table>\n" +
								"</section>");
					}
					else {
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
									"</table>\n" +
								"</div>\n" +
								"</form>\n" +
								"</section>");
					}
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				
				case "smartphones": {
					initializeParser();
					String[] imgList = new String[30];
					String[] priceList = new String[30];
					String[] nameList = new String[30];
					String[] idList = new String[30];
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					printHeaderJS(pWriter,request);
					
					int count=0;
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
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
									"</table>\n" +
								"</section>");
					}
					else {
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
									"</table>\n" +
								"</div>\n" +
								"</form>\n" +
								"</section>");
					}
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				
				case "laptops": {
					initializeParser();
					String[] imgList = new String[30];
					String[] priceList = new String[30];
					String[] nameList = new String[30];
					String[] idList = new String[30];
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					printHeaderJS(pWriter,request);
					
					int count=0;
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
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
									"</table>\n" +
								"</section>");
					}
					else {
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
									"</table>\n" +
								"</div>\n" +
								"</form>\n" +
								"</section>");
					}
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				
				case "externalstorage": {
					initializeParser();
					String[] imgList = new String[30];
					String[] priceList = new String[30];
					String[] nameList = new String[30];
					String[] idList = new String[30];
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					printHeaderJS(pWriter,request);
					
					int count=0;
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
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
									"</table>\n" +
								"</section>");
					}
					else {
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
									"</table>\n" +
								"</div>\n" +
								"</form>\n" +
								"</section>");
					}
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				
				case "accessories": {
					initializeParser();
					String[] imgList = new String[200];
					String[] priceList = new String[200];
					String[] nameList = new String[200];
					String[] idList = new String[200];
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					printHeaderJS(pWriter,request);
					
					int count=0;
					HashMap<String, String> dbStatus = new HashMap<String, String>();
					dbStatus = dbObj.checkDBConnection();
					if(dbStatus.get("status").equals("true")) {
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
									"</table>\n" +
								"</section>");
					}
					else {
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
									"</table>\n" +
								"</div>\n" +
								"</form>\n" +
								"</section>");
					}
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				
				default: {
					response.sendRedirect("/csj/Controller/HomeServlet?var=home");
					break;
					
				}
					
				}
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		String var = request.getParameter("var");
		if(var.equals("contactus")) {
			response.setContentType("text/html");
			PrintWriter pWriter = response.getWriter();
			HtmlEngine engine = new HtmlEngine(pWriter);
			engine.generateHtml("Header",request);
			pWriter.println("<div id='body' class='width'>\n" +
					"<section id='content'>\n" +
					"<div style='height:50px;'></div>\n" +
					"<form class='register' action='' method='POST'>\n" +
					"<div class='form_container'>\n" +
						"<table id='CustRegTable'>\n" +
							"<tr>\n" +
								"<td colspan='2' style='text-align:center;'>\n" +
									"<p>Review successfully Submitted.</p>\n" +
									"<p>Please use the link below to go back to Home Page.</p>\n" +
								"</td>\n" +
							"</tr>\n" +
							"<tr>\n" +	
								"<td colspan='2' style='text-align:right;'>\n" +
									"<a href='/csj/Controller/HomeServlet?var=home' method='get'>Homepage</a>\n" +
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
}