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

public class CustomerHomeServlet extends HttpServlet {
	
	public CustomerHomeServlet() {
		
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
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String confirmationNumber = request.getParameter("id");
		if(confirmationNumber!=null) {
			//get confirmation number into a String
			MySQLDataStoreUtilities dbObj = new MySQLDataStoreUtilities();
			HashMap<String, String> dbStatus = new HashMap<String, String>();
			dbStatus = dbObj.checkDBConnection();
			if(dbStatus.get("status").equals("true")) {
				dbObj.deleteOrders(confirmationNumber);
				//display
				response.setContentType("text/html");
				PrintWriter pWriter = response.getWriter();
				HtmlEngine engine = new HtmlEngine(pWriter);
				displayLogoutHeader(pWriter,request);
				
				pWriter.println("<div id='body' class='width'>\n" +
						"<section id='content'>\n" +
						"<div style='height:50px;'></div>\n" +
						"<form class='register' action='' method='POST'>\n" +
						"<div class='form_container'>\n" +
							"<table id='OrderDeleteTable'>\n" +
								"<tr>\n" +
									"<td colspan='2' style='text-align:center;'>\n" +
										"<p>Order - #" + confirmationNumber +" successfully deleted.</p>\n" +
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
							"<table id='OrderDeleteTable'>\n" +
								"<tr>\n" +
									"<td colspan='2' style='text-align:center;'>\n" +
										"<p>" + dbStatus.get("msg") + "</p>\n" +										
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
		
		
	}
}