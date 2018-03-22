package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import Controller.HtmlEngine;
import JavaBeans.*;
import DataAccess.MySQLDataStoreUtilities;

public class CartServlet extends HttpServlet {
	
	MySQLDataStoreUtilities dbObj;
	
	public CartServlet() {
		dbObj = new MySQLDataStoreUtilities();
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
	
	public void printCarousel(PrintWriter pWriter,HttpServletRequest request) {
		HttpSession userSession = request.getSession(true);
		ShoppingCart cart = (ShoppingCart)userSession.getAttribute("cart");
		//HashMap<String, String> prodList = new HashMap<String, String>();
		Vector<String> prodList = new Vector<String>();
		prodList = cart.getProductList();
		int size = prodList.size();
		String lastID = new String();
		String lastType = new String();
		if(size>0) {
			lastID = prodList.lastElement();
		}
		HashMap<String, String> dbStatus = new HashMap<String, String>();
		dbStatus = dbObj.checkDBConnection();
		if(dbStatus.get("status").equals("true")) {
			if(dbObj.getWatchMapDB().get(lastID)!=null)
				lastType = "smartwatches";
			if(dbObj.getSpeakerMapDB().get(lastID)!=null)
				lastType = "speakers";
			if(dbObj.getHeadphoneMapDB().get(lastID)!=null)
				lastType = "headphones";
			if(dbObj.getPhoneMapDB().get(lastID)!=null)
				lastType="smartphones";
			if(dbObj.getLaptopMapDB().get(lastID)!=null)
				lastType="laptops";
			if(dbObj.getStorageMapDB().get(lastID)!=null)
				lastType="externalstorage";
			if(dbObj.getAccessoryMapDB().get(lastID)!=null)
				lastType="accessories";
			
			
			System.out.println("LastType - " + lastType + "\t" + "LastID - " + lastID);
			HashMap<String, Accessories> lastItemMap = new HashMap<String, Accessories>();
			switch(lastType) {
				case "smartwatches":{
					SmartWatch tmp = dbObj.getWatchMapDB().get(lastID);
					lastItemMap = tmp.getAccessories();
					break;
				}
				case "speakers":{
					Speakers tmp = dbObj.getSpeakerMapDB().get(lastID);
					lastItemMap = tmp.getAccessories();
					break;
				}
				case "headphones":{
					Headphones tmp = dbObj.getHeadphoneMapDB().get(lastID);
					lastItemMap = tmp.getAccessories();
					break;
				}
				case "smartphones":{
					Phones tmp = dbObj.getPhoneMapDB().get(lastID);
					lastItemMap = tmp.getAccessories();
					break;
				}
				case "laptops":{
					Laptops tmp = dbObj.getLaptopMapDB().get(lastID);
					lastItemMap = tmp.getAccessories();
					break;
				}
				case "externalstorage":{
					ExternalStorage tmp = dbObj.getStorageMapDB().get(lastID); 
					lastItemMap = tmp.getAccessories();
					break;
				}
				case "accessories":{
					SmartWatch temp = new SmartWatch();
					for(Map.Entry<String, SmartWatch> k : dbObj.getWatchMapDB().entrySet()) {					
						temp = k.getValue();
						lastItemMap = temp.getAccessories();
						break;
					}
					break;
				}
				default:{
					
				}
			}
			
			
			String[] idList = new String[30];
			String[] priceList = new String[30];
			String[] nameList = new String[30];
			String[] imgList = new String[30];
			Accessories accessory= new Accessories();
			Accessories accessoryTmp= new Accessories();
			int n=0;
			System.out.println("LastItem Accessory List:");
			for(Map.Entry<String, Accessories> w : lastItemMap.entrySet()) {
				accessoryTmp = w.getValue();
				
				idList[n] = accessoryTmp.getId();
				System.out.println(idList[n]);
				n++;
			}

			for(int k=0;k<n;k++) {
				accessory = dbObj.getAccessoryMapDB().get(idList[k]);
				if(accessory!=null)
				{
					imgList[k] = accessory.getImage();
					priceList[k] = String.valueOf(accessory.getPrice());
					nameList[k] = accessory.getName();
				}
			}
			
			
			
			pWriter.println("<table id='carouselTable'>\n" +
					"<tr>\n" +
						"<td style='width:80%'>\n" +
							"<div id='myCarousel1' class='carousel slide' data-ride='carousel'>\n" +
								"<div class='carousel-inner'>\n" +
									"<div class='item active'>\n" +
										"<table id='carouselTable'>\n" +
											"<tr>\n" +
												"<td style='width:50%;'>\n" +
													"<p style='text-align:center;'><img src='/csj/" + imgList[0] + "' style='width:150px;height:150px' alt='" + imgList[0] + "'></p>\n" +
													"<br />\n" +
													"<p style='text-align:center;'><a href='#'>" + nameList[0] + "</a></p>\n" +
													"<br />\n" +
													"<p style='text-align:center;'><a href='#'>Price: $" + priceList[0] + "</a></p>\n" +
													"<br />\n" +
												"</td>\n" +
												"<td style='width:50%;'>\n" +
													"<p style='text-align:center;'><img src='/csj/" + imgList[1] + "' style='width:150px;height:150px' alt='" + imgList[1] + "'></p>\n" +
													"<br />\n" +
													"<p style='text-align:center;'><a href='#'>" + nameList[1] + "</a></p>\n" +
													"<br />\n" +
													"<p style='text-align:center;'><a href='#'>Price: $" + priceList[1] + "</a></p>\n" +
													"<br />\n" +
												"</td>\n" +
											"</tr>\n" +
										"</table>\n" +
									"</div>");
			for(int c=2;c<n-1;c+=2) {
				pWriter.println("<div class='item'>\n" +
						"<table id='carouselTable'>\n" +
						"<tr>\n" +
							"<td style='width:50%;'>\n" +
								"<p style='text-align:center;'><img src='/csj/" + imgList[c] + "' style='width:150px;height:150px' alt='" + imgList[c] + "'></p>\n" +
								"<br />\n" +
								"<p style='text-align:center;'><a href='#'>" + nameList[c] + "</a></p>\n" +
								"<br />\n" +
								"<p style='text-align:center;'><a href='#'>Price: $" + priceList[c] + "</a></p>\n" +
								"<br />\n" +
							"</td>\n" +
							"<td style='width:50%;'>\n" +
								"<p style='text-align:center;'><img src='/csj/" + imgList[c+1] + "' style='width:150px;height:150px' alt='" + imgList[c+1] + "'></p>\n" +
								"<br />\n" +
								"<p style='text-align:center;'><a href='#'>" + nameList[c+1] + "</a></p>\n" +
								"<br />\n" +
								"<p style='text-align:center;'><a href='#'>Price: $" + priceList[c+1] + "</a></p>\n" +
								"<br />\n" +
							"</td>\n" +
						"</tr>\n" +
					"</table>\n" +
					"</div>");
			}
			
			pWriter.println("<div class='item'>\n" +
							"<table id='carouselTable'>\n" +
							"<tr>\n" +
								"<td>\n" +
									"<p style='text-align:center;'><img src='/csj/" + imgList[n-1] + "' style='width:150px;height:150px' alt='" + imgList[n-1] + "'></p>\n" +
									"<br />\n" +
									"<p style='text-align:center;'><a href='#'>" + nameList[n-1] + "</a></p>\n" +
									"<br />\n" +
									"<p style='text-align:center;'><a href='#'>Price: $" + priceList[n-1] + "</a></p>\n" +
									"<br />\n" +
								"</td>\n" +
							"</tr>\n" +
							"</table>\n" +
							"</div>\n" +				
						"</div>\n" +
								"<a class='left carousel-control' href='#myCarousel1' data-slide='prev'>\n" + 
								"<span class='glyphicon glyphicon-chevron-left'></span>\n" + 
								"<span class='sr-only'>Previous</span>\n" + 
								"</a>\n" + 
								"<a class='right carousel-control' href='#myCarousel1' data-slide='next'>\n" + 
								"<span class='glyphicon glyphicon-chevron-right'></span>\n" + 
								"<span class='sr-only'>Next</span>\n" +
								"</a>\n" +
							"</div>\n" +
						"</td>\n" +
					"</tr>\n" +
				"</table>");
		}
		else {
			
			pWriter.println("<table id='carouselTable'>\n" +
					"<tr>\n" +
						"<td><p>" + dbStatus.get("msg") + "</p></td>\n" +
					"</tr>\n" +
					"</table>");
		}
	}
	
	public void displayLoginMsg(PrintWriter pWriter) {
		pWriter.println("<div id='body' class='width'>\n" +
				"<section id='content'>\n" +
				"<div style='height:50px;'></div>\n" +
				"<form class='register' action='' method='POST'>\n" +
				"<div class='form_container'>\n" +
					"<table id='CartPageTable'>\n" +
						"<tr>\n" +	
							"<th colspan='2'><div align='center'>Cart Page</div></th>\n" +
						"</tr>\n" +
						"<tr>\n" +	
							"<td colspan='2' style='text-align:left;'>\n" +
								
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
							"<td colspan='2' style='text-align:center;'>\n" +
								"<p>Please login to access your cart</p>\n" +
								"<p>Please use the link below to go back to Home Page.</p>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +	
							"<td colspan='2' style='text-align:right;'>\n" +
								"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
							"</td>\n" +
						"</tr>\n" +
					"</table>\n" +
				"</div>\n" +
				"</form>\n" +
				"</section>");
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession userSession = request.getSession(true);
			String username = (String)userSession.getAttribute("customerKey");
			ShoppingCart cart = (ShoppingCart)userSession.getAttribute("cart");
			String task = request.getParameter("task");
			if(task==null) {
				if(cart!=null) {
					int cartSize = cart.getCartSize();
					Vector<String> prodList = new Vector<String>();
					prodList = cart.getProductList();
					
					if(prodList!=null && cartSize>0) {
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						displayLogoutHeader(pWriter,request);
						
						pWriter.println("<div id='body' class='width'>\n" +
								"<section id='content'>\n" +
								"<div style='height:50px;'></div>\n" +
								"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='/csj/Controller/CartServlet' method='post'>\n" +
								"<div class='form_container'>\n" +
									"<table id='CartPageTable'>\n" +
										"<tr>\n" +	
											"<th colspan='4'><div align='center'>Cart Page</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td colspan='4' style='text-align:left;'>\n" +
												"<p><h2>You have " + cartSize + " items in your cart</h2></p>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<th><div align='center'>Product Type</div></th>\n" +
											"<th><div align='center'>Product ID</div></th>\n" +
											"<th><div align='center'>Product Price</div></th>\n" +
										"</tr>");
						
						ShoppingCart cartTmp = new ShoppingCart();
						String type = new String();
						String id = new String();
						HashMap<String, String> dbStatus = new HashMap<String, String>();
						dbStatus = dbObj.checkDBConnection();
						if(dbStatus.get("status").equals("true")) {
							//if clause not working buggy so change this to switch
							int size = prodList.size();
							Iterator<String> itr = prodList.iterator();
					        while(itr.hasNext()){
					            //System.out.println(itr.next());
					            id = itr.next();

					            if(dbObj.getWatchMapDB().get(id)!=null)
									type = "smartwatches";
								if(dbObj.getSpeakerMapDB().get(id)!=null)
									type = "speakers";
								if(dbObj.getHeadphoneMapDB().get(id)!=null)
									type = "headphones";
								if(dbObj.getPhoneMapDB().get(id)!=null)
									type="smartphones";
								if(dbObj.getLaptopMapDB().get(id)!=null)
									type="laptops";
								if(dbObj.getStorageMapDB().get(id)!=null)
									type="externalstorage";
								if(dbObj.getAccessoryMapDB().get(id)!=null)
									type="accessories";
								switch(type) {
									case "smartwatches":{
										SmartWatch watch = new SmartWatch();
										watch = dbObj.getWatchMapDB().get(id);
										pWriter.println("<tr>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='productType'>" + type + "</label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='productID'>" + id + "</label>\n" +											
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='price'>" + String.valueOf(watch.getPrice()) + "</label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<a href='/csj/Controller/CartServlet?task=delete&type=" + type + "&id=" + id + "'>Delete</a>\n" +
												"</td>\n" +
											"</tr>");
										break;
									}
									
									case "speakers":{
										Speakers speaker = new Speakers();
										speaker = dbObj.getSpeakerMapDB().get(id);
										pWriter.println("<tr>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='productType'>" + type + "</label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='productID'>" + id + "</label>\n" +											
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='price'>" + String.valueOf(speaker.getPrice()) + "</label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<a href='/csj/Controller/CartServlet?task=delete&type=" + type + "&id=" + id + "'>Delete</a>\n" +
												"</td>\n" +
											"</tr>");
										break;
									}
									case "headphones":{
										Headphones headphone = new Headphones();
										headphone = dbObj.getHeadphoneMapDB().get(id);
										pWriter.println("<tr>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='productType'>" + type + "</label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='productID'>" + id + "</label>\n" +											
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='price'>" + String.valueOf(headphone.getPrice()) + "</label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<a href='/csj/Controller/CartServlet?task=delete&type=" + type + "&id=" + id + "'>Delete</a>\n" +
												"</td>\n" +
											"</tr>");
		
										break;
									}
									case "smartphones":{
										Phones phone = new Phones();
										phone = dbObj.getPhoneMapDB().get(id);
										pWriter.println("<tr>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='productType'>" + type + "</label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='productID'>" + id + "</label>\n" +											
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='price'>" + String.valueOf(phone.getPrice()) + "</label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<a href='/csj/Controller/CartServlet?task=delete&type=" + type + "&id=" + id + "'>Delete</a>\n" +
												"</td>\n" +
											"</tr>");
		
										break;
									}
									case "laptops":{
										Laptops laptop = new Laptops();
										laptop = dbObj.getLaptopMapDB().get(id);
										pWriter.println("<tr>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='productType'>" + type + "</label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='productID'>" + id + "</label>\n" +											
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='price'>" + String.valueOf(laptop.getPrice()) + "</label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<a href='/csj/Controller/CartServlet?task=delete&type=" + type + "&id=" + id + "'>Delete</a>\n" +
												"</td>\n" +
											"</tr>");
		
										break;
									}
									case "externalstorage":{
										ExternalStorage storage = new ExternalStorage();
										storage = dbObj.getStorageMapDB().get(id);
										pWriter.println("<tr>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='productType'>" + type + "</label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='productID'>" + id + "</label>\n" +											
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='price'>" + String.valueOf(storage.getPrice()) + "</label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<a href='/csj/Controller/CartServlet?task=delete&type=" + type + "&id=" + id + "'>Delete</a>\n" +
												"</td>\n" +
											"</tr>");
		
										break;
									}
									case "accessories":{
										Accessories accessory = new Accessories();
										accessory = dbObj.getAccessoryMapDB().get(id);
										pWriter.println("<tr>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='productType'>" + type + "</label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='productID'>" + id + "</label>\n" +											
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<label name='price'>" + String.valueOf(accessory.getPrice()) + "</label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<a href='/csj/Controller/CartServlet?task=delete&type=" + type + "&id=" + id + "'>Delete</a>\n" +
												"</td>\n" +
											"</tr>");
										break;
									}
									default: {
										break;
									}
								}
					        }
						}
						else {
							pWriter.println("<tr>\n" +
									"<td colspan='4' style='text-align:center;'>\n" +
										"<p>" + dbStatus.get("msg") + "</p>\n" +
								"</td>\n" +
								"</tr>");
						}


						pWriter.println("<tr>\n" +
											"<td colspan='4' style='text-align:center;'>\n" +
												"<p></p>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td colspan='4' style='text-align:right;'>\n" +
												"<input type='submit' value='Checkout'>\n" +
											"</td>\n" +
										"</tr>\n" +
									"</table>\n" +
								"<div style='height:50px;'></div>");
						//display carousel
						printCarousel(pWriter,request);
						
						
						
						pWriter.println("</div>\n" +
								"</form>\n" +
								"</section>");
						
						engine.generateHtml("LeftNavigationBar",request);
						engine.generateHtml("Footer",request);
						
					}
					else {
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						displayLogoutHeader(pWriter,request);
						
						pWriter.println("<div id='body' class='width'>\n" +
								"<section id='content'>\n" +
								"<div style='height:50px;'></div>\n" +
								"<form class='register' action='' method='POST'>\n" +
								"<div class='form_container'>\n" +
									"<table id='CartPageTable'>\n" +
										"<tr>\n" +	
											"<th colspan='2'><div align='center'>Cart Page</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td colspan='2' style='text-align:left;'>\n" +
												"<p><h2>Cart is empty. Please add items to proceed further.</h2></p>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td colspan='2' style='text-align:center;'>\n" +
												"<p></p>\n" +
												"<p>Please use the link below to go back to Home Page.</p>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td colspan='2' style='text-align:right;'>\n" +
												"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
				else
				{
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					displayLogoutHeader(pWriter,request);
					displayLoginMsg(pWriter);
					
					
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
				}
			}
			else {
				//Code to delete item from cart
				
				String type = request.getParameter("type");
				String id = request.getParameter("id");
				System.out.println("type" + type);
				
				Vector<String> prodList = cart.getProductList();
				Vector<String> prodType = cart.getProductType();
				int size = cart.getCartSize();
				
				System.out.println("prodlist size before -" + prodList.size());
				if(prodList != null) {
					int index = prodList.indexOf(id);
					prodList.remove(id);
					prodType.remove(index);
					size = size - 1;	
				}
				System.out.println("prodlist size after -" + prodList.size());
				cart.setProductList(prodList);
				cart.setProductType(prodType);
				cart.setCartSize(size);
				cart.setUsername(username);
				userSession.setAttribute("cart", cart);
				//display page
				response.sendRedirect("/csj/Controller/CartServlet");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}	
	}
	
	public void printPaymentDetails(PrintWriter pWriter,String qnty,String totalPrice) {
		pWriter.println("<div id='body' class='width'>\n" +
				"<section id='content'>\n" +
				"<form style='width: 70%;margin: 0 auto;height: 50%;border: 1px solid #ccc;border-radius: 15px;' action='/csj/Controller/CartCarousel' method='POST'>\n" +
					"<div class='form_container'>\n" +
					"<br />\n" +
					"<table id='PaymentTable'>\n" +
						"<tr>\n" +	
							"<th colspan='2'><div align='center'>Payment Details</div></th>\n" +
						"</tr>\n" +
						"<tr>\n" +	
							"<td colspan='2'><p></p></td>\n" +
						"</tr>\n" +
						"<tr>\n" +
							"<td style='text-align:center;'>\n" +
								"<label for='Quantity' style='font-size:small'>Quantity : </label>\n" +
							"</td>\n" +
							"<td style='text-align:left;'>\n" +
								"<input type='text' id='quantity' style='width:20px;background-color:#bfbfbf;' name='quantity' value='" + qnty + "' readonly/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
							"<td style='text-align:center;'>\n" +
								"<label for='ProductPrice' style='font-size:small'>Product Price : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='productprice' style='width:300px;background-color:#bfbfbf;' name='productprice' value='" + totalPrice + "' readonly/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
							"<td style='text-align:center;'>\n" +
								"<label for='customerName' style='font-size:small'>Customer Name : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='customerName' style='width:300px' name='customerName' required/>\n" +
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
							"<td style='font-size:small;text-align:center;vertical-align: text-top;'>\n" +
								"<label for='CardType' style='font-size:small'>Card Type : </label>\n" +
							"</td>\n" +
							"<td style='font-size:small;text-align:left;vertical-align: text-top;'>\n" +
								"<input type='radio' name='cardType' value='VISA'>&nbsp;&nbsp;<img src='/csj/images/VISALogo.jpg' style='width:40px;height:30px' alt='VISALogo'>&nbsp;&nbsp;\n" +  
								"<input type='radio' name='cardType' value='MasterCard'>&nbsp;&nbsp;<img src='/csj/images/MasterCardLogo.jpg' style='width:40px;height:30px' alt='MasterCardLogo'>&nbsp;&nbsp;\n" +
							  	"<input type='radio' name='cardType' value='AmericanExpress'>&nbsp;&nbsp;<img src='/csj/images/AmericanExpress.jpg' style='width:40px;height:30px' alt='AmericanExpress'>&nbsp;&nbsp;\n" +
							  	"<input type='radio' name='cardType' value='Discover'>&nbsp;&nbsp;<img src='/csj/images/Discover.jpg' style='width:40px;height:30px' alt='Discover'>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
							"<td style='text-align:center;'>\n" +
								"<label for='CardNumber' style='font-size:small'>Card Number : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='cardnumber' style='width:300px' name='cardnumber' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='CVV' style='font-size:small'>CVV : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='cvv' style='width:300px' name='cvv' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='CardholderName' style='font-size:small'>Cardholder Name : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='cvv' style='width:300px' name='cardholdername' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='Country' style='font-size:small'>Country : </label>\n" +
							"</td>\n" +
							"<td style='text-align:right;'>\n" +
								"<input type='text' id='country' style='width:300px' name='country' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='State' style='font-size:small'>State : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='state' style='width:300px' name='state' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='Address1' style='font-size:small'>Address 1 : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='address1' style='width:300px' name='address1' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='Address2' style='font-size:small'>Address 2 : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='address2' style='width:300px' name='address2'/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='City' style='font-size:small'>City : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='city' style='width:300px' name='city' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='PostalCode' style='font-size:small'>Postal Code : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='postalcode' style='width:300px' name='postalcode' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='MobileNumber' style='font-size:small'>Mobile Number : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='mobilenumber' style='width:300px' name='mobilenumber' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='EmailAddress' style='font-size:small'>Email Address : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='emailaddress' style='width:300px' name='emailaddress' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
							"<td colspan='2' style='text-align:center;'>\n" +
								"<button name='sbmtButton' type='submit' style='width:80px;'>Submit</button>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td colspan='2' style='text-align:right;'>\n" +
							"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
						"</td>\n" +
					"</tr>\n" +
					"</table>\n" +
					"</div>\n" +
				"</form>\n" +
				"</section>");
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		try {
			HttpSession userSession = request.getSession(true);
			String username = (String)userSession.getAttribute("customerKey");
			ShoppingCart cart = (ShoppingCart) userSession.getAttribute("cart");
			if(cart!=null) {
				String id = new String();
				String type = new String();
				double d = 0.0;
				HashMap<String, String> dbStatus = new HashMap<String, String>();
				dbStatus = dbObj.checkDBConnection();
				if(dbStatus.get("status").equals("true")) {
					Vector<String> prodList = cart.getProductList();					
					Iterator<String> itr = prodList.iterator();
			        while(itr.hasNext()){
			        	id = itr.next();
						if(dbObj.getWatchMapDB().get(id)!=null)
						{
							SmartWatch temp = dbObj.getWatchMapDB().get(id);
							d = d + temp.getPrice();
						}
						if(dbObj.getSpeakerMapDB().get(id)!=null)
						{
							Speakers temp = dbObj.getSpeakerMapDB().get(id);
							d = d + temp.getPrice();
						}
						if(dbObj.getHeadphoneMapDB().get(id)!=null)
						{
							Headphones temp = dbObj.getHeadphoneMapDB().get(id);
							d = d + temp.getPrice();
						}
						if(dbObj.getPhoneMapDB().get(id)!=null)
						{
							Phones temp = dbObj.getPhoneMapDB().get(id);
							d = d + temp.getPrice();
						}
						if(dbObj.getLaptopMapDB().get(id)!=null)
						{
							Laptops temp = dbObj.getLaptopMapDB().get(id);
							d = d + temp.getPrice();
						}
						if(dbObj.getStorageMapDB().get(id)!=null)
						{
							ExternalStorage temp = dbObj.getStorageMapDB().get(id);
							d = d + temp.getPrice();
						}
						if(dbObj.getAccessoryMapDB().get(id)!=null)
						{
							Accessories temp = dbObj.getAccessoryMapDB().get(id);
							d = d + temp.getPrice();
						}
			        }
					
					int size = cart.getCartSize();
					String qnty = String.valueOf(size);				
					String totalPrice = String.format("%.2f", d);
					//display payment details page
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.generateHtml("Header",request);
					
					printPaymentDetails(pWriter,qnty,totalPrice);
					
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
				}
				else {
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.generateHtml("Header",request);
					
					pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
							"<div style='height:50px;'></div>\n" +
							"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;'>\n" +
								"<div class='form_container'>\n" +
									"<br />\n" +
									"<table id='PaymentDetails'>\n" +
										"<tr>\n" +	
											"<th><div align='center'>Payment Details</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td><p>" + dbStatus.get("msg") + "</p></td>\n" +
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
					
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
				}
				
			}
			else {
				response.setContentType("text/html");
				PrintWriter pWriter = response.getWriter();
				HtmlEngine engine = new HtmlEngine(pWriter);
				displayLogoutHeader(pWriter,request);
				displayLoginMsg(pWriter);
				engine.generateHtml("LeftNavigationBar",request);
				engine.generateHtml("Footer",request);
			}

		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
}